package com.text.card.ui

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.boat.vpn.demo.util.StatusBarUtil
import com.text.card.R
import com.text.card.base.AppActivity
import com.text.card.core.ColorData
import com.text.card.core.SwitchItem
import com.text.card.core.TemplateManager
import com.text.card.core.TemplateModel
import com.text.card.core.TextCardCore
import com.text.card.databinding.ActivityEditBinding
import com.text.card.helper.DisplayHelper
import com.text.card.helper.KeyboardUtils
import com.text.card.helper.KeyboardUtils.SoftKeyboardListener.OnSoftKeyboardChangeListener
import com.text.card.helper.ViewHelper
import com.text.card.helper.log
import com.text.card.helper.toast
import com.text.card.ui.adapter.Pager2Adapter
import com.text.card.ui.adapter.SwitchItemAdapter
import com.text.card.ui.adapter.TemplateItemAdapter
import com.text.card.viewmodel.EditViewMode

class EditActivity : AppActivity<ActivityEditBinding, EditViewMode>() {

    private val templateAdapter by lazy {
        TemplateItemAdapter().apply {
            data.clear()
            data.addAll(TemplateManager.templateData)
            itemClick = object : TemplateItemAdapter.ItemClick {
                override fun onItemClick(position: Int, item: TemplateModel<*>) {
                    data.map {
                        it.selected = false
                        if (it.getTemplateName() == item.getTemplateName()) {
                            it.selected = true
                        }
                    }
                    TemplateManager.currentTemplate = item
                    notifyDataSetChanged()
                    changeTemplate(item)
                    TextCardCore.saveCardData()
                }
            }
        }
    }

    private val mColorListener = object : ColorFragment.ColorListener {
        override fun onColorSelect(pageIndex: Int, colorData: ColorData) {
            TemplateManager.currentTemplate.updateCardBg(pageIndex == 1, colorData)
            TextCardCore.cardData.setBgColorType(pageIndex)
            TextCardCore.cardData.setBgColorName(colorData.name)
            TextCardCore.saveCardData()
        }
    }

    private val colorFragmentList = mutableListOf<ColorFragment>().apply {
        add(ColorFragment(0).apply {
            colorListener = mColorListener
        })//Light
        add(ColorFragment(1).apply {
            colorListener = mColorListener
        })//Dark
    }

    private val colorPagerAdapter by lazy {
        Pager2Adapter(this, colorFragmentList)
    }

    private val switchAdapter by lazy {
        SwitchItemAdapter().apply {
            data.clear()
            data.addAll(TemplateManager.switchData)
            notifyDataSetChanged()

            itemClick = object : SwitchItemAdapter.ItemClick {
                override fun onItemClick(position: Int, item: SwitchItem) {
                    item.show = !item.show
                    notifyItemChanged(position, position)
                    handleSwitch(item, position)
                }
            }
        }
    }

    private fun handleSwitch(item: SwitchItem, position: Int) {
        when(position) {
            0 -> {
                //icon
                TemplateManager.templateData.map {
                    it.showOrHideIcon(item.show)
                }
                TextCardCore.cardData.switchIcon = item.show
            }
            1 -> {
                //Date
                TemplateManager.templateData.map {
                    it.showOrHideDate(item.show)
                }
                TextCardCore.cardData.switchDate = item.show
            }
            2 -> {
                //Title
                TemplateManager.templateData.map {
                    it.showOrHideTitle(item.show)
                }
                TextCardCore.cardData.switchTitle = item.show
            }
            3 -> {
                //Text
                TemplateManager.templateData.map {
                    it.showOrHideContent(item.show)
                }
                TextCardCore.cardData.switchText = item.show
            }
            4 -> {
                //Author
                TemplateManager.templateData.map {
                    it.showOrHideAuthor(item.show)
                }
                TextCardCore.cardData.switchQuote = item.show
            }
            5 -> {
                //Count
                TemplateManager.templateData.map {
                    it.showOrHideWordCount(item.show)
                }
                TextCardCore.cardData.switchCount = item.show
            }
            6 -> {
                //qrcode
                TemplateManager.templateData.map {
                    it.showOrHideQrCode(item.show)
                }
                TextCardCore.cardData.switchQrCode = item.show
            }
            7 -> {
                //MARK
                TemplateManager.templateData.map {
                    it.showOrHideMark(item.show)
                }
                TextCardCore.cardData.switchWaterMark = item.show
            }
        }

        TextCardCore.saveCardData()
    }

    private lateinit var rootView: View


    override fun viewModelClass() = EditViewMode::class.java

    override fun inflate() = ActivityEditBinding.inflate(layoutInflater)

    override fun init() {
        TemplateManager.initTemplateView()
        rootView = this.findViewById(android.R.id.content)

        mBinding.apply {
            StatusBarUtil.fixStatusBar(toolBar)
            changeTemplate(TemplateManager.currentTemplate)

            scrollView.post {
                scrollView.post {
                    ViewHelper.setMarginTop(
                        contentContainer,
                        StatusBarUtil.getStatusBarHeight(this@EditActivity) + DisplayHelper.dip2px(
                            54 + 10
                        )
                    )
                }
            }

            KeyboardUtils.SoftKeyboardListener.setListener(this@EditActivity, object : OnSoftKeyboardChangeListener {
                override fun hide(height: Int) {
                    btnDone.isVisible = false
                    btnExport.isVisible = true
                    ivClearText.isVisible = true
                    saveEdittextContent()
                }

                override fun show(height: Int) {
                    btnDone.isVisible = true
                    btnExport.isVisible = false
                    ivClearText.isVisible = false
                }
            })

            //Template
            val templateLayoutManager = LinearLayoutManager(this@EditActivity, LinearLayoutManager.HORIZONTAL, false)
            templateContent.layoutManager = templateLayoutManager
            templateContent.adapter = templateAdapter

            //BgColor
            viewPager.adapter = colorPagerAdapter
            viewPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    if (position == 0) {
                        indicateFirst.setBackgroundResource(R.drawable.shape_google_blue_r45)
                        indicateSecond.setBackgroundResource(R.drawable.shape_999999_r45)
                    } else {
                        indicateFirst.setBackgroundResource(R.drawable.shape_999999_r45)
                        indicateSecond.setBackgroundResource(R.drawable.shape_google_blue_r45)
                    }
                }
            })

            //Switch
            switchContent.layoutManager = LinearLayoutManager(this@EditActivity, LinearLayoutManager.HORIZONTAL, false)
            switchContent.adapter = switchAdapter
        }

        initClickListener()

    }

    override fun onDestroy() {
        super.onDestroy()
        TemplateManager.destroyTemplate()
    }

    private fun initClickListener() {
        mBinding.apply {
            btnDone.setOnClickListener {
                KeyboardUtils.hideInput(this@EditActivity)
            }

            btnEditStyle.setOnClickListener {
                menuContainer.isVisible = !menuContainer.isVisible
                if (menuContainer.isVisible) {
                    btnEditStyle.setCardBackgroundColor(ContextCompat.getColor(this@EditActivity, R.color.page_bg))
                    tvEditStyle.visibility = View.INVISIBLE
                    ivBtnEditStyleArrow.animate().rotation(-180f).start()
                } else {
                    btnEditStyle.setCardBackgroundColor(ContextCompat.getColor(this@EditActivity, R.color.color_252525))
                    tvEditStyle.visibility = View.VISIBLE
                    ivBtnEditStyleArrow.animate().rotation(0f).start()
                }

                updateContentMarginBottom()
            }

            val selectTextColor = ContextCompat.getColor(this@EditActivity, R.color.white)
            val unSelectColor = ContextCompat.getColor(this@EditActivity, R.color.color_999999)
            btnTemplate.setOnClickListener {
                ivTemplate.isVisible = true
                tvTemplate.setTextColor(selectTextColor)
                btnTemplate.background = ContextCompat.getDrawable(this@EditActivity, R.drawable.shape_edit_page_btn_bg)
                templateContent.isVisible = true

                ivBgColor.isVisible = false
                tvBgColor.setTextColor(unSelectColor)
                btnBgColor.background = null
                bgColorContent.isVisible = false

                ivSwitch.isVisible = false
                tvSwitch.setTextColor(unSelectColor)
                btnSwitch.background = null
                switchContent.isVisible = false

                updateContentMarginBottom()
            }

            btnBgColor.setOnClickListener {
                changeBgColorData(TemplateManager.currentTemplate)

                ivTemplate.isVisible = false
                tvTemplate.setTextColor(unSelectColor)
                btnTemplate.background = null
                templateContent.isVisible = false

                ivBgColor.isVisible = true
                tvBgColor.setTextColor(selectTextColor)
                btnBgColor.background = ContextCompat.getDrawable(this@EditActivity, R.drawable.shape_edit_page_btn_bg)
                bgColorContent.isVisible = true

                ivSwitch.isVisible = false
                tvSwitch.setTextColor(unSelectColor)
                btnSwitch.background = null
                switchContent.isVisible = false

                updateContentMarginBottom()
            }

            btnSwitch.setOnClickListener {
                ivTemplate.isVisible = false
                tvTemplate.setTextColor(unSelectColor)
                btnTemplate.background = null
                templateContent.isVisible = false

                ivBgColor.isVisible = false
                tvBgColor.setTextColor(unSelectColor)
                btnBgColor.background = null
                bgColorContent.isVisible = false

                ivSwitch.isVisible = true
                tvSwitch.setTextColor(selectTextColor)
                btnSwitch.background = ContextCompat.getDrawable(this@EditActivity, R.drawable.shape_edit_page_btn_bg)
                switchContent.isVisible = true

                updateContentMarginBottom()
            }
        }
    }

    private fun updateContentMarginBottom() {
        mBinding.apply {
            menuContainer.post {
                val marginBottom = if (menuContainer.isVisible) {
                    menuContainer.height + DisplayHelper.dip2px(10)
                } else {
                    0
                }

                ViewHelper.setMarginBottom(contentContainer, marginBottom)
            }

        }
    }

    private fun changeTemplate(templateModel: TemplateModel<*>) {
        mBinding.apply {
            TextCardCore.cardData.templateName = templateModel.getTemplateName()
            contentContainer.removeAllViews()
            contentContainer.addView(templateModel.mBinding?.root)
            initTemplateListener()
            initTemplateData()
        }
    }

    private fun initTemplateListener() {
        TemplateManager.currentTemplate.apply {
            getIconView().setOnClickListener {
                toast("icon")
            }
            getDateView().setOnClickListener {
                toast("date")
            }

            getTitleView().addTextChangedListener {
                TextCardCore.cardData.title = it?.toString()?:""
            }
            getContentView().addTextChangedListener {
                TextCardCore.cardData.text = it?.toString()?:""
                getWordCountView().text = "word: ${getContentView().text.toString().length}"
            }
            getAuthorView().addTextChangedListener {
                TextCardCore.cardData.author = it?.toString()?:""
            }
        }

        //when hide keyboard saveData
    }

    private fun initTemplateData() {
        val cardData = TextCardCore.cardData
        TemplateManager.currentTemplate.apply {
            //icon
            getIconView().setImageResource(cardData.iconResId)
            //date always today

            //title
            getTitleView().setText(cardData.title)
            //text
            getContentView().setText(cardData.text)
            //author
            getAuthorView().setText(cardData.author)
            //word
            getWordCountView().setText("work: ${getContentView().text.toString().length}")
        }

        //showOrHide
        TemplateManager.templateData.map {
            it.showOrHideIcon(cardData.switchIcon)
            it.showOrHideDate(cardData.switchDate)
            it.showOrHideTitle(cardData.switchTitle)
            it.showOrHideContent(cardData.switchText)
            it.showOrHideAuthor(cardData.switchQuote)
            it.showOrHideWordCount(cardData.switchCount)
            it.showOrHideQrCode(cardData.switchQrCode)
        }
    }

    private fun saveEdittextContent() {
        TemplateManager.currentTemplate.apply {
            TextCardCore.cardData.title = getTitleView().text.toString()
            TextCardCore.cardData.text = getContentView().text.toString()
            TextCardCore.cardData.author = getAuthorView().text.toString()
            TextCardCore.saveCardData()
        }
    }

    private fun changeBgColorData(templateModel: TemplateModel<*>) {
        colorFragmentList[0].updateData(templateModel.getTemplateBgColor()[0].colorDataList)
        colorFragmentList[1].updateData(templateModel.getTemplateBgColor()[1].colorDataList)
    }
}
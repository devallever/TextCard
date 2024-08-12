package com.text.card.ui

import android.content.Context
import android.graphics.Color
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.boat.vpn.demo.util.StatusBarUtil
import com.text.card.R
import com.text.card.base.AppActivity
import com.text.card.core.SwitchItem
import com.text.card.core.TemplateManager
import com.text.card.core.TemplateModel
import com.text.card.databinding.ActivityEditBinding
import com.text.card.databinding.TemplateMediaBinding
import com.text.card.helper.DisplayHelper
import com.text.card.helper.KeyboardUtils
import com.text.card.helper.KeyboardUtils.SoftKeyboardListener.OnSoftKeyboardChangeListener
import com.text.card.helper.KeyboardVisibilityListener
import com.text.card.helper.ViewHelper
import com.text.card.ui.adapter.ColorAdapter
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
                    notifyDataSetChanged()
                    changeTemplate(item)
                }
            }
        }
    }

    private val colorFragmentList = mutableListOf<ColorFragment>().apply {
        add(ColorFragment())//Light
        add(ColorFragment())//Dark
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
                }
            }
        }
    }

    private lateinit var rootView: View


    override fun viewModelClass() = EditViewMode::class.java

    override fun inflate() = ActivityEditBinding.inflate(layoutInflater)

    override fun init() {
        rootView = this.findViewById(android.R.id.content)

        mBinding.apply {
            StatusBarUtil.fixStatusBar(toolBar)
            val tempMediaBinding = TemplateMediaBinding.inflate(layoutInflater)
            contentContainer.removeAllViews()
            contentContainer.addView(tempMediaBinding.root)

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
                updateBgColorData(TemplateManager.currentTemplate)

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

    }

    private fun updateBgColorData(templateModel: TemplateModel<*>) {
        colorFragmentList[0].updateData(templateModel.getTemplateBgColor()[0].colorDataList)
        colorFragmentList[1].updateData(templateModel.getTemplateBgColor()[1].colorDataList)
    }
}
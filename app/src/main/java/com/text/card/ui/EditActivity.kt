package com.text.card.ui

import android.Manifest
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Build
import android.os.Environment
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.boat.vpn.demo.util.StatusBarUtil
import com.text.card.R
import com.text.card.base.AppActivity
import com.text.card.core.ColorData
import com.text.card.core.DateFormat
import com.text.card.core.SwitchItem
import com.text.card.core.TemplateManager
import com.text.card.core.TemplateModel
import com.text.card.core.TextCardCore
import com.text.card.databinding.ActivityEditBinding
import com.text.card.databinding.PopExportBinding
import com.text.card.helper.DisplayHelper
import com.text.card.helper.KeyboardUtils
import com.text.card.helper.KeyboardUtils.SoftKeyboardListener.OnSoftKeyboardChangeListener
import com.text.card.helper.PermissionHelper
import com.text.card.helper.ShareHelper
import com.text.card.helper.ViewHelper
import com.text.card.helper.copyToAlbum
import com.text.card.helper.toast
import com.text.card.ui.adapter.Pager2Adapter
import com.text.card.ui.adapter.SwitchItemAdapter
import com.text.card.ui.adapter.TemplateItemAdapter
import com.text.card.ui.dialog.DateTimeFormatDialog
import com.text.card.ui.dialog.IconDialog
import com.text.card.ui.dialog.WordCountFormatDialog
import com.text.card.viewmodel.EditViewMode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream


class EditActivity : AppActivity<ActivityEditBinding, EditViewMode>() {

    private val RC_PERMISSION = 1000
    private val mPermissionsList = ArrayList<String>().apply {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            add(Manifest.permission.READ_MEDIA_IMAGES)
        } else {
            add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }

    private lateinit var mPopBinding: PopExportBinding
    private lateinit var mPopExport: PopupWindow

    private val mIconDialog by lazy {
        IconDialog {
            TemplateManager.currentTemplate.getIconView().setImageResource(it)
            TextCardCore.cardData.iconResId = it
            TextCardCore.saveCardData()
        }
    }

    private val mDateTimeFormatDialog by lazy {
        DateTimeFormatDialog { text, format ->
            TemplateManager.currentTemplate.getDateView().text = text
            TextCardCore.cardData.dateFormatType = format
            TextCardCore.saveCardData()
        }
    }

    private val mWordFormatDialog by lazy {
        WordCountFormatDialog {
            val etContent = TemplateManager.currentTemplate.getContentView()
            TemplateManager.currentTemplate.getWordCountView().text =
                getString(it, etContent.text.toString().length)
            TextCardCore.cardData.wordCountFormatType = it
            TextCardCore.saveCardData()
        }
    }

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
            updateMenuColorText(pageIndex == 1, colorData)
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
        when (position) {
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

            KeyboardUtils.SoftKeyboardListener.setListener(
                this@EditActivity,
                object : OnSoftKeyboardChangeListener {
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
            val templateLayoutManager =
                LinearLayoutManager(this@EditActivity, LinearLayoutManager.HORIZONTAL, false)
            templateContent.layoutManager = templateLayoutManager
            templateContent.adapter = templateAdapter

            //BgColor
            viewPager.adapter = colorPagerAdapter
            viewPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    TextCardCore.cardData.setBgColorType(position)
                    TextCardCore.saveCardData()
                    if (position == 0) {
                        indicateFirst.setBackgroundResource(R.drawable.shape_google_blue_r45)
                        indicateSecond.setBackgroundResource(R.drawable.shape_999999_r45)
                        tvColorStyle.text = "Light"
                    } else {
                        indicateFirst.setBackgroundResource(R.drawable.shape_999999_r45)
                        indicateSecond.setBackgroundResource(R.drawable.shape_google_blue_r45)
                        tvColorStyle.text = "Dark"
                    }
                }
            })

            //Switch
            switchContent.layoutManager =
                LinearLayoutManager(this@EditActivity, LinearLayoutManager.HORIZONTAL, false)
            switchContent.adapter = switchAdapter
        }

        initPopMenu()

        initClickListener()

    }

    private fun initPopMenu() {
        mPopBinding = PopExportBinding.inflate(layoutInflater)

        mPopExport = PopupWindow(
            mPopBinding.root,
            DisplayHelper.dip2px(240),
            ViewGroup.LayoutParams.WRAP_CONTENT,
            true
        )

    }

    override fun onDestroy() {
        super.onDestroy()
        TemplateManager.destroyTemplate()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == RC_PERMISSION) {
            val hasPermission =
                PermissionHelper.hasPermissionOrigin(this@EditActivity, mPermissionsList)
            if (hasPermission) {
                saveView { result, path->
                    if (result) {
                        toast("save success: $path")
                    } else {
                        toast("save fail")
                    }
                }
            }
        }
    }

    private fun initClickListener() {
        mBinding.apply {
            btnDone.setOnClickListener {
                KeyboardUtils.hideInput(this@EditActivity)
            }

            btnEditStyle.setOnClickListener {
                menuContainer.isVisible = !menuContainer.isVisible
                if (menuContainer.isVisible) {
                    btnEditStyle.setCardBackgroundColor(
                        ContextCompat.getColor(
                            this@EditActivity,
                            R.color.page_bg
                        )
                    )
                    tvEditStyle.visibility = View.INVISIBLE
                    ivBtnEditStyleArrow.animate().rotation(-180f).start()
                } else {
                    btnEditStyle.setCardBackgroundColor(
                        ContextCompat.getColor(
                            this@EditActivity,
                            R.color.color_252525
                        )
                    )
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
                btnTemplate.background =
                    ContextCompat.getDrawable(this@EditActivity, R.drawable.shape_edit_page_btn_bg)
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
                btnBgColor.background =
                    ContextCompat.getDrawable(this@EditActivity, R.drawable.shape_edit_page_btn_bg)
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
                btnSwitch.background =
                    ContextCompat.getDrawable(this@EditActivity, R.drawable.shape_edit_page_btn_bg)
                switchContent.isVisible = true

                updateContentMarginBottom()
            }

            mPopBinding.btnSave.setOnClickListener {
                mPopExport.dismiss()
                val hasPermission =
                    PermissionHelper.hasPermissionOrigin(this@EditActivity, mPermissionsList)
                if (hasPermission) {
                    saveView{ result, path->
                        if (result) {
                            toast("save success: $path")
                        } else {
                            toast("save fail")
                        }
                    }
                } else {
                    // 请求权限
                    val array: Array<String> =
                        mPermissionsList.toArray(arrayOfNulls<String>(mPermissionsList.size)) // 使用ArrayList的size作为参数也可以，例如：new Integer[list.size()]
                    ActivityCompat.requestPermissions(
                        this@EditActivity,
                        array,
                        RC_PERMISSION
                    );
                }
            }
            mPopBinding.btnShare.setOnClickListener {
                mPopExport.dismiss()
                //
                val path = "${cacheDir.absolutePath}${File.separator}${System.currentTimeMillis()}.jpg"
                lifecycleScope.launch(Dispatchers.IO) {
                    val result = saveViewAsImageToCache(scrollView, path)
                    if (result) {
                        ShareHelper.shareImage(this@EditActivity, path)
                    } else {
                        toast("Share fail")
                    }
                }
            }
            btnExport.setOnClickListener {
                mPopExport.showAsDropDown(
                    btnExport,
                    0,
                    DisplayHelper.dip2px(22)
                )
            }

            ivClearText.setOnClickListener {
                AlertDialog.Builder(this@EditActivity)
                    .setMessage(R.string.clear_text_tips)
                    .setTitle(R.string.clear_all_text)
                    .setPositiveButton(R.string.clear) {dialog, which ->
                        TemplateManager.currentTemplate.getTitleView().setText("")
                        TemplateManager.currentTemplate.getContentView().setText("")
                        TemplateManager.currentTemplate.getAuthorView().setText("")
                        dialog.dismiss()
                    }
                    .setNegativeButton(R.string.cancle) {dialog, which ->
                        dialog.dismiss()
                    }
                    .show()
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
            contentContainer.post {
                changeBgColorData(TemplateManager.currentTemplate)
            }
        }
    }

    private fun initTemplateListener() {
        TemplateManager.currentTemplate.apply {
            getIconView().setOnClickListener {
                mIconDialog.show(supportFragmentManager)
            }
            getDateView().setOnClickListener {
                mDateTimeFormatDialog.show(supportFragmentManager)
            }
            getWordCountView().setOnClickListener {
                mWordFormatDialog.show(supportFragmentManager)
            }

            getTitleView().addTextChangedListener {
                TextCardCore.cardData.title = it?.toString() ?: ""
            }
            getContentView().addTextChangedListener {
                TextCardCore.cardData.text = it?.toString() ?: ""
                getWordCountView().text = getString(
                    TextCardCore.cardData.wordCountFormatType,
                    getContentView().text.toString().length
                )
            }
            getAuthorView().addTextChangedListener {
                TextCardCore.cardData.author = it?.toString() ?: ""
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
            getDateView().text =
                DateFormat.format(cardData.dateFormatType, System.currentTimeMillis())

            //title
            getTitleView().setText(cardData.title)
            //text
            getContentView().setText(cardData.text)
            //author
            getAuthorView().setText(cardData.author)
            //word
            getWordCountView().text =
                getString(cardData.wordCountFormatType, getContentView().text.toString().length)

            //bgColor

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

    private fun changeBgColorSelectedData(templateModel: TemplateModel<*>) {

    }

    private fun changeBgColorData(templateModel: TemplateModel<*>, isChangeData: Boolean = true) {
        val lightList = templateModel.getTemplateBgColor()[0].colorDataList
        val darkList = templateModel.getTemplateBgColor()[1].colorDataList
        val isDark = TextCardCore.cardData.getBgColorType() == 1
        val colorName = TextCardCore.cardData.getBgColorName()

        lightList.map {
            it.selected = false
        }
        darkList.map {
            it.selected = false
        }

        var setSuccess = false
        if (isDark) {
            darkList.map {
                if (it.name == colorName) {
                    it.selected = true
                    setSuccess = true
                }
            }
        } else {
            lightList.map {
                if (it.name == colorName) {
                    it.selected = true
                    setSuccess = true
                }
            }
        }

        if (!setSuccess) {
            lightList[0].selected = true
        }

        if (isChangeData) {
            colorFragmentList[0].updateData(lightList)
            colorFragmentList[1].updateData(darkList)
        } else {
            colorFragmentList[0].notifySelectedChanged()
            colorFragmentList[1].notifySelectedChanged()
        }


        val colorData = TemplateManager.currentTemplate.getBgColorData()
        colorData.let {
            TemplateManager.currentTemplate.updateCardBg(isDark, it)
        }
        updateMenuColorText(isDark, colorData)

    }

    private fun updateMenuColorText(isDark: Boolean, colorData: ColorData) {
        mBinding.tvColorName.text = colorData.name
        if (isDark) {
            mBinding.tvColorStyle.text = "Dark"
        } else {
            mBinding.tvColorStyle.text = "Light"
        }
    }

    private fun saveView(cb: (success: Boolean, path: String) -> Unit) {
        lifecycleScope.launch(Dispatchers.Main) {
            val fileName = "${System.currentTimeMillis()}.jpg"
            val path =
                "${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)}"
            val result = saveViewAsImage(mBinding.scrollView, fileName, path)
            cb.invoke(result, "${path}${File.separator}${fileName}")
        }
    }

    private suspend fun saveViewAsImage(view: View, fileName: String, path: String) =
        withContext(Dispatchers.IO) {

            // 创建一个和View相同大小的空的Bitmap
            val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
            // 使用上述创建的Bitmap，创建一个Canvas
            val canvas = Canvas(bitmap)
            // 将View绘制在Canvas上
            view.draw(canvas)
            // 将Bitmap写入到SD卡中
            val file = File(this@EditActivity.cacheDir.absolutePath, fileName)
            try {
                val out = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, out)
                out.flush()
                out.close()
                file.copyToAlbum(this@EditActivity, fileName, getString(R.string.app_name))
                file.delete()
                return@withContext true
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return@withContext false
        }

    suspend fun saveViewAsImageToCache(view: View, filename: String?): Boolean {
        // 创建一个和View相同大小的空的Bitmap
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        // 使用上述创建的Bitmap，创建一个Canvas
        val canvas = Canvas(bitmap)
        // 将View绘制在Canvas上
        view.draw(canvas)
        // 将Bitmap写入到SD卡中
        val file = File(filename)
        try {
            val out = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, out)
            out.flush()
            out.close()
            return true
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }
}
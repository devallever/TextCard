package com.text.card.core

import androidx.viewbinding.ViewBinding

abstract class TemplateModel<VB: ViewBinding> {


    var mBinding: VB
    var name: String = ""
    var cover: Int = -1
    var selected: Boolean = false
    var bgColor: MutableList<TemplateBgColor>

    init {
        mBinding = inflateView()
        name = getTemplateName()
        cover = getTemplateCover()
        bgColor = getTemplateBgColor()
    }

    abstract fun inflateView(): VB

    abstract fun getTemplateName(): String

    abstract fun getTemplateCover(): Int

    abstract fun getTemplateBgColor(): MutableList<TemplateBgColor>

    abstract fun showOrHideIcon(show: Boolean)
    abstract fun showOrHideDate(show: Boolean)
    abstract fun showOrHideTitle(show: Boolean)
    abstract fun showOrHideContent(show: Boolean)
    abstract fun showOrHideAuthor(show: Boolean)
    abstract fun showOrHideWordCount(show: Boolean)
    abstract fun showOrHideQrCode(show: Boolean)

//    abstract fun getIconView(): ImageView
//    abstract fun getDataView(): TextView
//    abstract fun getTitleView(): EditText
//    abstract fun getContentView(): EditText
//    abstract fun getAuthorView(): TextView
//    abstract fun getWordCountView(): TextView
//    abstract fun getQrCodeView(): ImageView
//    abstract fun getQrCodeTitleView(): TextView
//    abstract fun getQrCodeDescView(): TextView




}

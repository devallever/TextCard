package com.text.card.template

import android.view.LayoutInflater
import androidx.core.view.isVisible
import com.text.card.App
import com.text.card.R
import com.text.card.core.ColorData
import com.text.card.core.TemplateBgColor
import com.text.card.core.TemplateModel
import com.text.card.databinding.TemplateMediaBinding

class TemplateTicket : TemplateModel<TemplateMediaBinding>() {
    override fun inflateView() = TemplateMediaBinding.inflate(LayoutInflater.from(App.context))
    override fun getTemplateName(): String {
        return "Ticket"
    }

    override fun getTemplateCover(): Int {
        return R.drawable.icon_setting
    }


    override fun getTemplateBgColor(): MutableList<TemplateBgColor> {
        //dark & light
        return mutableListOf(
            TemplateBgColor(
                //light color
                mutableListOf(
                    ColorData("Default", mutableListOf("#ff0000", "#ff00ff")),
                    ColorData("Default2", mutableListOf("#00ff00", "#0000ff"))
                ),
                TemplateBgColor.COLOR_LIGHT,
            ),
            //dark color
            TemplateBgColor(
                mutableListOf(
                    ColorData("Default", mutableListOf("#ff0000", "#ff00ff")),
                    ColorData("Default2", mutableListOf("#00ff00", "#0000ff"))
                ),
                TemplateBgColor.COLOR_DARK,
            )
        )
    }

    override fun showOrHideIcon(show: Boolean) {
        mBinding.ivIcon.isVisible = show
    }

    override fun showOrHideDate(show: Boolean) {
        mBinding.tvDate.isVisible = show
    }

    override fun showOrHideTitle(show: Boolean) {
        mBinding.etTitle.isVisible = show
    }

    override fun showOrHideContent(show: Boolean) {
        mBinding.etContent.isVisible = show
    }

    override fun showOrHideAuthor(show: Boolean) {
        mBinding.etAuthor.isVisible = show
    }

    override fun showOrHideWordCount(show: Boolean) {
        mBinding.tvCount.isVisible = show
    }

    override fun showOrHideQrCode(show: Boolean) {
        mBinding.tvQrCodeDesc.isVisible = show
    }


}
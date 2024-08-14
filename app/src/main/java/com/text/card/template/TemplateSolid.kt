package com.text.card.template

import android.view.LayoutInflater
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.text.card.App
import com.text.card.R
import com.text.card.core.ColorData
import com.text.card.core.TemplateBgColor
import com.text.card.core.TemplateModel
import com.text.card.databinding.TemplateSolidBinding
import com.text.card.helper.log
import com.text.card.ui.widget.GradientBackgroundDrawable

class TemplateSolid : TemplateModel<TemplateSolidBinding>() {
    private val colorList = mutableListOf(
        TemplateBgColor(
            //light color
            mutableListOf(
                ColorData.CYAN,
                ColorData.LIGHT_CYAN,
                ColorData.BLUE,
                ColorData.LIGHT_BLUE,
                ColorData.TEAL,
                ColorData.GREEN,
                ColorData.LIGHT_GREEN,
                ColorData.EMERALD,
                ColorData.ROSE,
                ColorData.LIGHT_ROSE,
                ColorData.LIGHT_PURPLE,
                ColorData.ORANGE,
                ColorData.LIGHT_ORANGE,
                ColorData.YELLOW,
                ColorData.WHITE
            ),
            TemplateBgColor.COLOR_LIGHT,
        ),
        //dark color
        TemplateBgColor(
            mutableListOf(
                ColorData.DARK_GRAY_RED,
                ColorData.DARK_GRAY_GREEN,
                ColorData.DARK_GRAY_VIOLET,
                ColorData.DARK_GRAY_INDIGO,
                ColorData.DARK_GRAY_BLUE,
                ColorData.DARK_GRAY_CYAN,
                ColorData.DARK_DARK_CYAN,
                ColorData.DARK_DARK_INDIGO,
                ColorData.DARK_DARK_GREEN,
                ColorData.DARK_DARK_BLUE,
                ColorData.DARK_DARK_VIOLET,
                ColorData.DARK_LIGHT_WARM_GRAY,
                ColorData.DARK_LIGHT_COLD_GRAY,
                ColorData.DARK_BLACK
            ),
            TemplateBgColor.COLOR_DARK,
        )
    )

    override fun inflateView() = TemplateSolidBinding.inflate(LayoutInflater.from(App.context))
    override fun getTemplateName(): String {
        return "Solid"
    }

    override fun getTemplateCover(): Int {
        return R.drawable.icon_setting
    }


    override fun getTemplateBgColor(): MutableList<TemplateBgColor> {
        //dark & light
        log("Solid create getTemplateBgColor")
        return colorList
    }

    override fun showOrHideIcon(show: Boolean) {
        mBinding?.ivIcon?.isVisible = show
    }

    override fun showOrHideDate(show: Boolean) {
        mBinding?.tvDate?.isVisible = show
    }

    override fun showOrHideTitle(show: Boolean) {
        mBinding?.etTitle?.isVisible = show
    }

    override fun showOrHideContent(show: Boolean) {
        mBinding?.etContent?.isVisible = show
    }

    override fun showOrHideAuthor(show: Boolean) {
        mBinding?.etAuthor?.isVisible = show
    }

    override fun showOrHideWordCount(show: Boolean) {
        mBinding?.tvWordCount?.isVisible = show
    }

    override fun showOrHideQrCode(show: Boolean) {
        mBinding?.qrCodeLine?.isVisible = show
        mBinding?.qrCode?.isVisible = show
    }

    override fun showOrHideMark(show: Boolean) {
        mBinding?.waterMark?.isVisible = show
    }

    override fun updateCardBg(isDark: Boolean, data: ColorData) {


        mBinding?.apply {

            //background
            templateBg.background = GradientBackgroundDrawable().apply {
                setColorList(
                    data.colorValue,
                    templateBg.width.toFloat(),
                    templateBg.height.toFloat()
                )
            }

            //cardView
            cardView.setBackgroundResource(if (isDark) R.drawable.shape_solid_bg_dark else R.drawable.shape_solid_bg)

            //icon
//            ivIcon.setBackgroundResource(if (isDark) R.drawable.shape100f17_r45 else R.drawable.shape_999999_r45)
            ivIcon.setColorFilter(
                ContextCompat.getColor(
                    App.context,
                    if (isDark) R.color.color_A0ffffff else R.color.color_A0000000
                )
            )

            //Date
            tvDate.setTextColor(App.getColor(if (isDark) R.color.color_40ffffff else R.color.color_40000000))

            //title
            etTitle.setTextColor(App.getColor(if (isDark) R.color.color_A0ffffff else R.color.color_A0000000))
            etTitle.setHintTextColor(App.getColor(if (isDark) R.color.color_40ffffff else R.color.color_40000000))
            //content
            etContent.setTextColor(App.getColor(if (isDark) R.color.color_A0ffffff else R.color.color_A0000000))
            etContent.setHintTextColor(App.getColor(if (isDark) R.color.color_40ffffff else R.color.color_40000000))

            //author
            etAuthor.setTextColor(App.getColor(if (isDark) R.color.color_40ffffff else R.color.color_40000000))
            etAuthor.setHintTextColor(App.getColor(if (isDark) R.color.color_40ffffff else R.color.color_80000000))
            //word
            tvWordCount.setTextColor(App.getColor(if (isDark) R.color.color_40ffffff else R.color.color_40000000))

            //qrcode
            ivQrCode.setColorFilter(App.getColor(if (isDark) R.color.color_A0ffffff else R.color.color_40000000))
            tvQrCodeTitle.setTextColor(App.getColor(if (isDark) R.color.color_40ffffff else R.color.color_40000000))
            tvQrCodeDesc.setTextColor(App.getColor(if (isDark) R.color.color_40ffffff else R.color.color_20000000))

            //water mark
            tvWater.setTextColor(App.getColor(if (isDark) R.color.color_40ffffff else R.color.color_40000000))

        }
    }

    override fun getIconView() = mBinding?.ivIcon!!

    override fun getDateView() = mBinding?.tvDate!!

    override fun getTitleView() = mBinding?.etTitle!!

    override fun getContentView() = mBinding?.etContent!!

    override fun getAuthorView() = mBinding?.etAuthor!!

    override fun getWordCountView() = mBinding?.tvWordCount!!

}
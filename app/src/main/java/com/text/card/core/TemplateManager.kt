package com.text.card.core

import com.text.card.R
import com.text.card.template.TemplateBento
import com.text.card.template.TemplateGeek
import com.text.card.template.TemplateMedia
import com.text.card.template.TemplateSolid
import com.text.card.template.TemplateTicket

object TemplateManager {

    val templateData = mutableListOf<TemplateModel<*>>()
    lateinit var currentTemplate: TemplateModel<*>
    val switchData = mutableListOf<SwitchItem>().apply {
        add(SwitchItem("Icon", R.drawable.icon_switch_icon, TextCardCore.cardData.switchIcon))
        add(SwitchItem("Date", R.drawable.icon_switch_date, TextCardCore.cardData.switchDate))
        add(SwitchItem("Title", R.drawable.icon_swich_title, TextCardCore.cardData.switchTitle))
        add(SwitchItem("Text", R.drawable.icon_switch_text, TextCardCore.cardData.switchText))
        add(SwitchItem("Quote", R.drawable.icon_switch_quote, TextCardCore.cardData.switchQuote))
        add(SwitchItem("Count", R.drawable.icon_switch_count, TextCardCore.cardData.switchCount))
        add(SwitchItem("QRCode", R.drawable.icon_swich_qr, TextCardCore.cardData.switchQrCode))
        add(SwitchItem("Mark", R.drawable.icon_switch_mark, TextCardCore.cardData.switchWaterMark))
    }

    fun initData() {
        templateData.clear()
        templateData.add(TemplateMedia())
        templateData.add(TemplateGeek())
        templateData.add(TemplateSolid())
        templateData.add(TemplateBento())
        templateData.add(TemplateTicket())
        templateData.map {
            val selected = TextCardCore.cardData.templateName == it.getTemplateName()
            it.selected = selected
            if (selected) {
                currentTemplate = it
            }
        }

        if (currentTemplate == null) {
            templateData[0].selected = true
            currentTemplate = templateData[0]
            TextCardCore.cardData.templateName = templateData[0].getTemplateName()
        }

    }

    fun initTemplateView() {
        templateData.map {
            it.initView()
        }
    }

    fun destroyTemplate() {
        templateData.map {
            it.destroyView()
        }
    }
}
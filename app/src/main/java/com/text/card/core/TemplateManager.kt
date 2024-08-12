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
        add(SwitchItem("Icon", R.drawable.icon_switch_icon))
        add(SwitchItem("Date", R.drawable.icon_switch_date))
        add(SwitchItem("Title", R.drawable.icon_swich_title))
        add(SwitchItem("Text", R.drawable.icon_switch_text))
        add(SwitchItem("Quote", R.drawable.icon_switch_quote))
        add(SwitchItem("Count", R.drawable.icon_switch_count))
        add(SwitchItem("QRCode", R.drawable.icon_swich_qr))
        add(SwitchItem("Mark", R.drawable.icon_switch_mark))
    }

    fun initData() {
        templateData.clear()
        templateData.add(TemplateMedia().apply {
            selected = true
            currentTemplate = this
        })
        templateData.add(TemplateGeek())
        templateData.add(TemplateSolid())
        templateData.add(TemplateBento())
        templateData.add(TemplateTicket())
    }
}
package com.text.card.core

import com.text.card.R
import com.text.card.helper.GsonHelper
import com.text.card.helper.StoreManager
import com.text.card.helper.log
import com.text.card.helper.toJson

object TextCardCore {
    var cardData = getCardDataObj()

    val iconTypeData = mutableListOf(
        IconTypeData(
            "Abstract", mutableListOf(
                IconData(R.drawable.icon_setting, true),
                IconData(R.drawable.icon_save)
            )
        ),
        IconTypeData(
            "Object & Tool", mutableListOf(
                IconData(R.drawable.icon_switch_text),
                IconData(R.drawable.icon_erase)
            )
        ),
        IconTypeData(
            "Device", mutableListOf(
                IconData(R.drawable.icon_swich_qr),
                IconData(R.drawable.icon_switch_mark)
            )
        ),
        IconTypeData(
            "Human", mutableListOf(
                IconData(R.drawable.icon_more),
                IconData(R.drawable.icon_switch_count)
            )
        ),
        IconTypeData(
            "Nature", mutableListOf(
                IconData(R.drawable.icon_swich_title),
                IconData(R.drawable.icon_switch_text)
            )
        ),
        IconTypeData(
            "Travel", mutableListOf(
                IconData(R.drawable.icon_arrow_down, true),
                IconData(R.drawable.icon_switch_quote, true)
            )
        )
    ).apply {
        map {
            it.iconList.map {
                if (it.icon == cardData.iconResId) {
                    it.selected = true
                } else {
                    it.selected = false
                }
            }
        }
    }

    val dateTimeFormatData = mutableListOf<DateTimeItem>().apply {
        val time = System.currentTimeMillis()
        DateFormat.FORMAT_LIST.map {
            add(DateTimeItem(time, it, it == cardData.dateFormatType))
        }
    }

    private const val KEY_CARD_DATA = "KEY_CARD_DATA"

    fun saveCardData() {
        val cardJson = cardData.toJson()
        log("saveCardData: $cardJson")
        StoreManager.putString(KEY_CARD_DATA, cardJson)
    }

    fun getCardDataObj(): CardData {
        val data = StoreManager.getString(KEY_CARD_DATA)
        log("getCardData: ${data}")
        if (data.isEmpty()) {
            return CardData()
        }
        val cardData = GsonHelper.fromJson(data, CardData::class.java) ?: CardData()
        return cardData
    }

}
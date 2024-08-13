package com.text.card.core

import com.text.card.helper.GsonHelper
import com.text.card.helper.StoreManager
import com.text.card.helper.log
import com.text.card.helper.toJson

object TextCardCore {
    var cardData = getCardDataObj()

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
        val cardData = GsonHelper.fromJson(data, CardData::class.java)?: CardData()
        return cardData
    }

}
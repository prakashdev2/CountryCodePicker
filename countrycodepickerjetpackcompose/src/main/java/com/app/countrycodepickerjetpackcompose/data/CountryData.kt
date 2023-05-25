package com.app.countrycodepickerjetpackcompose.data

import com.app.countrycodepickerjetpackcompose.R
import java.util.Locale

data class CountryData(
    private var cCodes: String,
    val countryPhoneCode: String = "+90",
    val cNames:String = "tr",
    val flagResID: Int = R.drawable.tr
) {
    val countryCode = cCodes.lowercase(Locale.getDefault())
}
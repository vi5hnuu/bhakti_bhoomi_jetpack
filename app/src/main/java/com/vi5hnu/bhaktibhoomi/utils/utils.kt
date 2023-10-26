package com.vi5hnu.bhaktibhoomi.utils

import java.lang.IllegalArgumentException

object Utils{
    private val languages= mapOf<String,String>(
        "en" to "English",
        "hi" to "Hindi"
    )

    fun getFullForm(langAbbr:String):String{
        return languages[langAbbr] ?: throw IllegalArgumentException("no such language abbrivation exists.")
    }
}
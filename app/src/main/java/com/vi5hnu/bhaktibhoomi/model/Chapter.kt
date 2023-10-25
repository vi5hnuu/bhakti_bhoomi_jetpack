package com.vi5hnu.bhaktibhoomi.model

data class Chapter(
    val id:String,
    val chapterNumber:Int,
    val versesCount:Int,
    val name:String,
    val translation:String,
    val transliteration:String,
    val meaning:Map<String,String>,
    val summary:Map<String,String>,
)
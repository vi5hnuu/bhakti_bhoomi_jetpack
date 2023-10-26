package com.vi5hnu.bhaktibhoomi.model

data class ShlokPage(
    val content:List<Shlok>,
    //all other page properties are ignored
)
data class Shlok(
    val id:String,
    val chapter:Int,
    val verse:Int,
    val shlok:String,
    val transliteration:String,
    val translationsBy:Map<String,Map<String,String>>,
    val chapter_id:String
)
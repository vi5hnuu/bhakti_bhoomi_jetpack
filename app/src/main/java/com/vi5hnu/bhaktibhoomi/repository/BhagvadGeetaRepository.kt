package com.vi5hnu.bhaktibhoomi.repository

import com.vi5hnu.bhaktibhoomi.model.Chapter
import com.vi5hnu.bhaktibhoomi.model.Shlok
import com.vi5hnu.bhaktibhoomi.model.ShlokPage
import com.vi5hnu.bhaktibhoomi.network.BhagvadGeetaAPI
import javax.inject.Inject


class BhagvadGeetaRepository @Inject constructor(private val bhagvadGeetaAPI: BhagvadGeetaAPI) {
    suspend fun getAllChapters():List<Chapter>{
        return bhagvadGeetaAPI.getChapters()
    }
    suspend fun getChapter(id:String):Chapter{//not in use
        return bhagvadGeetaAPI.getChapter(id);
    }
    suspend fun getShlok(cid:String,sid:String):Shlok{//not in use
        return bhagvadGeetaAPI.getShlok(cid,sid);
    }
    suspend fun getAllShloks(cid:String,pageSize:Int,pageNo:Int=1):ShlokPage{
        return bhagvadGeetaAPI.getShloks(cid,pageSize,pageNo)
    }

}
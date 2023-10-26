package com.vi5hnu.bhaktibhoomi.network

import com.vi5hnu.bhaktibhoomi.model.Chapter
import com.vi5hnu.bhaktibhoomi.model.Shlok
import com.vi5hnu.bhaktibhoomi.model.ShlokPage
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface BhagvadGeetaAPI {
    @GET("chapter")
    suspend fun getChapters():List<Chapter>
    @GET("chapter/{cid}")
    suspend fun getChapter(@Path("cid") id:String):Chapter

    @GET("chapter/{cid}/shlok/{sid}")
    suspend fun getShlok(@Path("cid") cid:String,@Path("sid") sid:String):Shlok
    @GET("chapter/{cid}/shlok")
    suspend fun getShloks(@Path("cid") cid:String,@Query("pageSize") pageSize:Int, @Query("pageNo") pageNo:Int):ShlokPage
}
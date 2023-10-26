package com.vi5hnu.bhaktibhoomi.screens

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vi5hnu.bhaktibhoomi.model.Chapter
import com.vi5hnu.bhaktibhoomi.model.Shlok
import com.vi5hnu.bhaktibhoomi.model.ShlokPage
import com.vi5hnu.bhaktibhoomi.repository.BhagvadGeetaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BhagvadGeetaViewModel @Inject constructor(private val bhagvadGeetaRepository: BhagvadGeetaRepository):ViewModel() {
    val isLoading= mutableStateOf(false)
    val error:MutableState<String?> = mutableStateOf(null)
    val chapters:MutableState<List<Chapter>> = mutableStateOf(emptyList())
    val shloks:MutableState<ShlokPage?> = mutableStateOf(null)

    init {
        viewModelScope.launch { _getAllChapters() }
    }
    private suspend fun _getAllChapters(){
        isLoading.value=true;
        error.value=null;
        try {
           chapters.value=bhagvadGeetaRepository.getAllChapters().sortedBy { it.chapterNumber }
        }catch (e:Exception){
            error.value=e.message;
        }finally {
            isLoading.value=false;
        }
    }

    fun loadChapters(){
        viewModelScope.launch { _getAllChapters() }
    }

    private suspend fun _getAllShloks(chapterId:String,pageSize:Int,){
        isLoading.value=true;
        error.value=null;
        try {
           shloks.value = bhagvadGeetaRepository.getAllShloks(cid = chapterId,pageSize=pageSize)
        }catch (e:Exception){
            error.value=e.message;
        }finally {
            isLoading.value=false;
        }
    }
    fun loadShlokas(chapterId: String,pageSize: Int){
        viewModelScope.launch { _getAllShloks(chapterId=chapterId,pageSize=pageSize) }
    }
    fun getShlok(chapterId:String,shlokId:String):Shlok?{
        error.value=null;
        try {
          return shloks.value?.content?.find { shlok -> shlok.chapter_id==chapterId && shlok.id == shlokId } ?: throw Exception("failed to find shlok...")
        }catch (e:Exception){
            error.value=e.message;
        }
        return null;
    }

    fun getChapter(chapterId:String):Chapter?{
        error.value=null;
        try {
            return chapters.value.find { chapter -> chapter.id==chapterId } ?: throw Exception("failed to find chapter...")
        }catch (e:Exception){
            error.value=e.message;
        }
        return null;
    }
}
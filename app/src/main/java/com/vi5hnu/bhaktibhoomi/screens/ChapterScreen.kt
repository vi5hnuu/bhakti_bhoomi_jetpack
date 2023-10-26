package com.vi5hnu.bhaktibhoomi.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.vi5hnu.bhaktibhoomi.model.Chapter
import com.vi5hnu.bhaktibhoomi.model.Shlok
import com.vi5hnu.bhaktibhoomi.utils.Utils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChapterScreen(viewModel: BhagvadGeetaViewModel,navController:NavController,id:String) {
    val chapter = viewModel.getChapter(id);

    //load shlokas for this chapter
    if(chapter!=null) viewModel.loadShlokas(chapter.id,chapter.versesCount);

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                ),
                title = {
                    Text(
                        text="${chapter?.name} (${chapter?.translation})",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState()),
            )
        }
    ) { innerPadding -> Surface(modifier= Modifier
        .padding(innerPadding)
        .fillMaxSize()) {
            Box(modifier = Modifier.padding(12.dp) ){
                if(chapter==null) Text("something went wrong")//will rarely happen
                else Column(modifier=Modifier.fillMaxSize()) {
                    Text(
                        text = "${chapter.meaning["hi"]}\n(${chapter.meaning["en"]})",
                        modifier=Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 24.sp,
                        color = MaterialTheme.colorScheme.primary)
                    Divider(modifier=Modifier.padding(horizontal = 7.dp, vertical = 12.dp), color = MaterialTheme.colorScheme.primary)
                    LazyColumn(modifier=Modifier.fillMaxSize()){
                        item {
                            SummaryCard(chapter)
                            Spacer(modifier = Modifier.height(7.dp))
                        }
                        item {
                            Card(
                                modifier = Modifier
                                    .clip(
                                        RoundedCornerShape(
                                            CornerSize(15.dp),
                                        )
                                    )
                                    .height(625.dp)
                                    .fillMaxWidth(),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.primary.copy(0.07f)
                                )
                            ) {

                                Box(modifier=Modifier.fillMaxSize()){
                                    if(viewModel.isLoading.value)  CircularProgressIndicator(
                                        color = MaterialTheme.colorScheme.primary,
                                        strokeCap = StrokeCap.Square,
                                        modifier = Modifier.align(Alignment.Center)
                                    )else if(viewModel.error.value!=null || viewModel.shloks.value?.content==null) Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center,
                                        modifier= Modifier
                                            .fillMaxWidth()
                                            .align(Alignment.Center)) {
                                        Text(text = "Something went wrong")
                                        TextButton(onClick = { viewModel.loadChapters() }) {
                                            Text(text = "Retry")
                                        }
                                    }else Column(modifier = Modifier
                                        .padding(7.dp)
                                        .fillMaxWidth()
                                    ) {
                                        Text(
                                            text = "Shloks",
                                            fontWeight = FontWeight.Bold,
                                            textDecoration = TextDecoration.Underline,
                                            textAlign = TextAlign.Center,
                                            fontSize = 18.sp,
                                            color = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(horizontal = 7.dp, vertical = 7.dp)
                                        )
                                        LazyColumn(modifier=Modifier.padding(7.dp)){
                                            val shlokas=viewModel.shloks.value!!.content;
                                            items(shlokas){
                                                    shlok -> ShlokCard(shlok=shlok, modifier = Modifier.padding(bottom = if (shlok.verse != shlokas.size) 7.dp else 0.dp)){
                                                    chapterId,shlokId->navController.navigate("${Screens.ShlokScreen.name}/${chapterId}/shlok/${shlokId}")
                                                }
                                            }
                                        }

                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
}

@Composable
private fun ShlokCard(
    shlok: Shlok,
    modifier: Modifier=Modifier,
    onClick:(chapterId:String,shlokId:String)->Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary.copy(0.04f),
            contentColor = MaterialTheme.colorScheme.secondary
        ),
        shape = RoundedCornerShape(50),
        modifier = modifier
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = shlok.shlok,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 7.dp, bottom = 7.dp, start = 15.dp).weight(1f),
                textAlign = TextAlign.Center
            )
            IconButton(onClick = { onClick(shlok.chapter_id,shlok.id) }, modifier = Modifier.padding(end=12.dp)) {
                Icon(imageVector = Icons.Filled.ArrowForward, contentDescription = "Arrow forward")
            }

        }
    }
}

@Composable
private fun SummaryCard(
    chapter: Chapter,
) {
    val expandedSummary:MutableState<String?> = remember { mutableStateOf(null) }
    Card(
        modifier = Modifier.clip(
            RoundedCornerShape(
                CornerSize(15.dp),
            )
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary.copy(0.07f)
        )
    ) {
        Column(modifier = Modifier.padding(7.dp)) {
            Text(
                text = "Summary",
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline,
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 7.dp, vertical = 7.dp)
            )
            Column {
                chapter.summary.entries.forEach { entry ->
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 5.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 7.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = Utils.getFullForm(entry.key), color = MaterialTheme.colorScheme.primary)
                            IconButton(onClick = {
                                expandedSummary.value =
                                    if (expandedSummary.value == entry.key) null else entry.key
                            }) {
                                Icon(
                                    imageVector = if (expandedSummary.value == entry.key) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                                    contentDescription = "expension arrow",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                        AnimatedVisibility(visible = expandedSummary.value == entry.key) {
                            Text(
                                text = entry.value,
                                textAlign = TextAlign.Justify,
                                fontSize = 18.sp,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(
                                    start = 12.dp,
                                    end = 12.dp,
                                    bottom = 12.dp
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

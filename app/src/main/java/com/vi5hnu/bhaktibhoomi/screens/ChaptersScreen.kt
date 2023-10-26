package com.vi5hnu.bhaktibhoomi.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.vi5hnu.bhaktibhoomi.components.ChapterCard


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChaptersScreen(viewModel: BhagvadGeetaViewModel,navController: NavController) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                ),
                title = {
                    Text(
                        "Bhagvad Geeta",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState()),
            )
        }
    ) {
        innerPadding -> Surface(modifier= Modifier
        .padding(innerPadding)
        .fillMaxSize()) {
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(7.dp)){
                if(!viewModel.isLoading.value && viewModel.error.value==null) LazyColumn(modifier=Modifier.fillMaxSize()){
                    val chapters=viewModel.chapters.value;
                    itemsIndexed(chapters){
                            index,chapter -> ChapterCard(chapter=chapter){chapterId->navController.navigate("${Screens.ChapterScreen.name}/${chapterId}")}
                        if(index!=chapters.lastIndex) Spacer(modifier = Modifier.height(7.dp))
                    }
                }
                if(viewModel.isLoading.value) CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary,
                    strokeCap = StrokeCap.Square,
                    modifier = Modifier.align(Alignment.Center)
                )
                if(viewModel.error.value!=null) Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Something went wrong")
                    TextButton(onClick = { viewModel.loadChapters() }) {
                        Text(text = "Retry")
                    }
                }
            }
        }
    }
}

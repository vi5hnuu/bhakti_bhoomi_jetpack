package com.vi5hnu.bhaktibhoomi.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class ExpendedTranslation(var authorKey:String,var key:String)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShlokScreen(viewModel: BhagvadGeetaViewModel,cid:String,sid:String) {
    val shlok=viewModel.getShlok(chapterId = cid, shlokId = sid)
    val expandedTranslation:MutableState<ExpendedTranslation?> = remember {
        mutableStateOf(null)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                ),
                title = {
                    Text(
                        text="Shlok",
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
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    if(shlok==null) Text(text = "Something went wrong") //will never happend (rare)
                    else {
                        ElevatedCard(
                            modifier=Modifier.size(55.dp),
                            shape = RoundedCornerShape(50),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary, contentColor = MaterialTheme.colorScheme.onPrimary)
                        ) {
                            Box(modifier = Modifier.fillMaxSize()){
                                Text(
                                    text = shlok.verse.toString(),
                                    fontSize = 18.sp,
                                    modifier=Modifier.align(Alignment.Center))
                            }
                        }
                        Spacer(modifier = Modifier.height(7.dp))
                        Text(
                            text = shlok.shlok,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 18.sp
                        )
                        Divider(modifier=Modifier.padding(horizontal = 12.dp, vertical = 12.dp), color = MaterialTheme.colorScheme.primary)
                        Column {
                            Text(
                                text = "Translations",
                                modifier= Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 12.dp),
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                fontSize = 19.sp,
                                color = MaterialTheme.colorScheme.primary,
                                fontFamily = FontFamily.Monospace,
                                textDecoration = TextDecoration.Underline)
                        }
                        LazyColumn{
                            val authors=shlok.translationsBy.keys.toList();
                            itemsIndexed(authors){
                                index,authorKey -> Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary.copy(0.05f))) {
                                    Column(modifier=Modifier.padding(7.dp)) {
                                    Text(text = shlok.translationsBy[authorKey]!!["author"]!!,modifier=Modifier.fillMaxWidth(), textAlign = TextAlign.Center, fontWeight = FontWeight.Bold,color = MaterialTheme.colorScheme.primary)
                                        Divider(modifier=Modifier.padding(vertical = 7.dp), color = MaterialTheme.colorScheme.primary)
                                    Column {
                                        shlok.translationsBy[authorKey]!!.keys.forEach { key ->
                                            if(key!="author"){
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
                                                        Text(text = key.uppercase(), color = MaterialTheme.colorScheme.secondary)
                                                        IconButton(
                                                            onClick = { expandedTranslation.value = if (expandedTranslation.value?.authorKey == authorKey && expandedTranslation.value?.key==key) null else ExpendedTranslation(authorKey,key) }) {
                                                            Icon(
                                                                imageVector = if (expandedTranslation.value?.authorKey == authorKey && expandedTranslation.value?.key==key) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                                                                contentDescription = "expension arrow",
                                                                tint = MaterialTheme.colorScheme.secondary
                                                            )
                                                        }
                                                    }
                                                    AnimatedVisibility(visible = (expandedTranslation.value?.authorKey == authorKey && expandedTranslation.value?.key==key)) {
                                                        Text(
                                                            text = shlok.translationsBy[authorKey]!![key]!!,
                                                            textAlign = TextAlign.Justify,
                                                            fontSize = 18.sp,
                                                            color = MaterialTheme.colorScheme.secondary,
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
                                if(index!=authors.lastIndex) Spacer(modifier = Modifier.height(7.dp))
                            }
                        }
                    }

                }   
            }
        }
    }
}

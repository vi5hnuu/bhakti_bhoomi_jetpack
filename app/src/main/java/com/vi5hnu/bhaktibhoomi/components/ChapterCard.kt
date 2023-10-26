package com.vi5hnu.bhaktibhoomi.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vi5hnu.bhaktibhoomi.model.Chapter


@Composable
fun ChapterCard(chapter: Chapter, onClick:(chapterId:String)->Unit) {
    ElevatedCard(
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 0.5.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(chapter.id) },
        colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.secondary),
        shape = RoundedCornerShape(50)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ElevatedCard(
                modifier = Modifier
                    .size(65.dp)
                    .padding(5.dp),
                shape = RoundedCornerShape(50),
                colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = "${chapter.chapterNumber}",
                        modifier = Modifier.align(Alignment.Center),
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 25.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = chapter.name, fontSize = 24.sp)
                Text(text = "(${chapter.translation})", fontWeight = FontWeight.ExtraLight)
            }

        }
    }
}
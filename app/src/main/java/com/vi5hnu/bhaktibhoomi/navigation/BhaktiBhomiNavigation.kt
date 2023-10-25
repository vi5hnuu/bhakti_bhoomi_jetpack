package com.vi5hnu.bhaktibhoomi.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.vi5hnu.bhaktibhoomi.screens.ChapterScreen
import com.vi5hnu.bhaktibhoomi.screens.ChaptersScreen
import com.vi5hnu.bhaktibhoomi.screens.Screens
import com.vi5hnu.bhaktibhoomi.screens.ShlokScreen

@Composable
fun BhaktiBhoomiNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController,
        startDestination = Screens.ChaptersScreen.name ){
        composable(Screens.ChaptersScreen.name){
            ChaptersScreen()
        }
        composable("${Screens.ChapterScreen.name}/{id}", arguments = listOf(navArgument(name = "id"){
            type= NavType.StringType
        }) ){
            ChapterScreen(id=it.arguments?.getString("id") ?: "unknown")
        }
        composable("${Screens.ShlokScreen.name}/{cid}/shlok/{sid}", arguments = listOf(navArgument(name = "cid"){type=
            NavType.StringType}, navArgument(name = "sid"){type= NavType.StringType})){
            ShlokScreen()
        }
    }
}
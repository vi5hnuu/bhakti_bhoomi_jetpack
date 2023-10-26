package com.vi5hnu.bhaktibhoomi.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.vi5hnu.bhaktibhoomi.screens.BhagvadGeetaViewModel
import com.vi5hnu.bhaktibhoomi.screens.ChapterScreen
import com.vi5hnu.bhaktibhoomi.screens.ChaptersScreen
import com.vi5hnu.bhaktibhoomi.screens.Screens
import com.vi5hnu.bhaktibhoomi.screens.ShlokScreen

@Composable
fun BhaktiBhoomiNavigation() {
    val navController = rememberNavController()
    val route="BhagvadGeeta";
    NavHost(navController = navController,
        startDestination = route ){
        navigation(startDestination = Screens.ChaptersScreen.name,route=route){
            composable(Screens.ChaptersScreen.name){
                val viewModel=it.sharedViewModel<BhagvadGeetaViewModel>(navController = navController)
                ChaptersScreen(viewModel=viewModel,navController=navController)
            }
            composable("${Screens.ChapterScreen.name}/{id}", arguments = listOf(navArgument(name = "id"){
                type= NavType.StringType
            }) ){
                val viewModel=it.sharedViewModel<BhagvadGeetaViewModel>(navController = navController)
                ChapterScreen(viewModel=viewModel,navController=navController,id=it.arguments?.getString("id") ?: "unknown")
            }
            composable("${Screens.ShlokScreen.name}/{cid}/shlok/{sid}", arguments = listOf(navArgument(name = "cid"){type=
                NavType.StringType}, navArgument(name = "sid"){type= NavType.StringType})){
                val viewModel=it.sharedViewModel<BhagvadGeetaViewModel>(navController = navController)
                ShlokScreen(viewModel=viewModel,cid=it.arguments?.getString("cid") ?: "unknown" ,sid=it.arguments?.getString("sid") ?: "unknown")
            }
        }
    }
}

@Composable
inline fun <reified T:ViewModel> NavBackStackEntry.sharedViewModel(navController: NavController):T{
    val navGraphRoute = destination.parent?.route ?: return hiltViewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return hiltViewModel(parentEntry);
}
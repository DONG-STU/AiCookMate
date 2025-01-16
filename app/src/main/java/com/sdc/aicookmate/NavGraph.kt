package com.sdc.aicookmate

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
//        composable("home") {
//            HomeScreen(navController)
//        }
//        composable("cart") {
//            CartScreen(navController)
//        }
//        composable("favorite") {
//            FavoriteScreen(navController)
//        }
        composable("mypage") {
            MyPageScreen(navController)
        }
//        composable("refrigerator") {
//            RefrigeratorScreen(navController)
//        }
    }
}
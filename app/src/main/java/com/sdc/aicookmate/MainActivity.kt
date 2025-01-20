package com.sdc.aicookmate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sdc.aicookmate.ui.theme.AiCookMateTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Box(Modifier.safeDrawingPadding()) {
                AiCookMateTheme {
                    NavSys()
                }
            }
        }
    }
}

@Composable
fun NavSys() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "main",
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        composable("main") {
            MainScreen(navController)
        }
        composable("myPage"){
            MyPageScreen(navController)
        }
        composable("shopping"){
            FoodShoppingScreen(navController)
        }
        composable("recipeExplain"){
            RecipeExplainScreen(navController)
        }
        composable("recipeScreen2"){
            RecipeScreen2(navController)
        }
        composable("Recipe"){
            RecipeScreen(navController)
        }
        composable("selectRecipeScreen") {
            SelectRecipeScreen(navController)
        }
        composable("refigeratorScreen") {
            Refrigerator(navController)
        }
        composable("GptScreen") {
            GptScreen(navController)
        }
        composable("ScanRefrigeratorPhoto") {
            ScanRefrigeratorPhoto(navController)
        }
        composable("ScanReceiptImage") {
            ScanReceiptImage(navController)
        }
        composable("InfluencerScreen") {
            InfluencerScreen(navController)
        }
        composable("InfluencerScreen2") {
            InfluencerScreen2(navController)
        }

    }
}
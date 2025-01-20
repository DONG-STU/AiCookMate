package com.sdc.aicookmate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sdc.aicookmate.ui.theme.AiCookMateTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Box(modifier = androidx.compose.ui.Modifier.safeDrawingPadding()) {
                AiCookMateTheme {
                    NavSys()
                }
            }
        }
    }
}

@androidx.compose.runtime.Composable
fun NavSys() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "main",
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        composable("main") {
            MainScreen(navController)
        }
        composable("myPage") {
            MyPageScreen(navController)
        }
        composable("shopping") {
            FoodShoppingScreen(navController)
        }
        composable("recipeExplain") {
            RecipeExplainScreen(navController)
        }
        composable("recipeScreen2") {
            RecipeScreen2(navController)
        }
        composable("Recipe") {
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

    }
}

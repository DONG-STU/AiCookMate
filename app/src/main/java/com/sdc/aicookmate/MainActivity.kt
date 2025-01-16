package com.sdc.aicookmate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
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
        composable("myPage") {
            MyPageScreen(navController)
        }
        composable("shopping") {
            FoodShoppingScreen(navController)
        }
//        composable("refrigerator") {
//            Refrigerator(navController)
//        }
    }
}
@Composable
fun BottomBar(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(Color(0xFFF2F2F2))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_home),
                contentDescription = "메인 홈 아이콘",
                modifier = Modifier
                    .size(40.dp)
                    .clickable { navController.navigate("main") }
            )
            Image(
                painter = painterResource(id = R.drawable.ic_cart),
                contentDescription = "카트 아이콘",
                modifier = Modifier
                    .size(40.dp)
                    .clickable { navController.navigate("shopping") }
            )
            Image(
                painter = painterResource(id = R.drawable.ic_kitchen),
                contentDescription = "냉장고 아이콘",
                modifier = Modifier
                    .size(40.dp)
                    .clickable { navController.navigate("refrigerator") }
            )
            Image(
                painter = painterResource(id = R.drawable.ic_bookheart),
                contentDescription = "찜 레시피 아이콘",
                modifier = Modifier
                    .size(40.dp)
                    .clickable { /*  */ }
            )
            Image(
                painter = painterResource(id = R.drawable.ic_person),
                contentDescription = "마이페이지 아이콘",
                modifier = Modifier
                    .size(40.dp)
                    .clickable { navController.navigate("myPage") }
            )
        }
    }
}

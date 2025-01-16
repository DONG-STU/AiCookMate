package com.sdc.aicookmate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.sdc.aicookmate.ui.theme.AiCookMateTheme




@Composable
fun MyPageScreen(navController: NavController) {
    Scaffold(
        bottomBar = { BottomBar(navController) }
    ) { paddingValues ->
        Column(
            modifier = androidx.compose.ui.Modifier
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        )
        {
            Row {
                Button(onClick = { /*TODO*/ }) {
                    Text("스크랩 레시피")
                }
                Button(onClick = { /*TODO*/ }) {
                    Text("스크랩 레시피")
                }
                Button(onClick = { /*TODO*/ }) {
                    Text("후기 관리")
                }
                RecentRecipeSection()
                MenuList()
            }
        }
    }
}


    @Composable
    fun RecentRecipeSection() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                "최근에 본 레시피",
                modifier = Modifier.padding(vertical = 16.dp),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )


            RecentRecipeCard(
                imageUrl = "recipe_image_url",
                title = "맛있는 비빔밥",
                views = "조회수",
                viewCount = "32회"
            )

            Spacer(modifier = Modifier.height(8.dp))

            RecentRecipeCard(
                imageUrl = "recipe_image_url",
                title = "맛있는 비빔밥",
                views = "조회수",
                viewCount = "32회"
            )

            MenuList()

        }
    }

@Composable
fun RecentRecipeCard(
    imageUrl: String,
    title: String,
    views: String,
    viewCount: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(8.dp),
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.recipe1),
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(4.dp)),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .padding(start = 12.dp)
                    .weight(1f)
            ) {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                Row {
                    Text(
                        text = views,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = viewCount,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

@Composable
fun MenuList() {
    val menuItems = listOf(
        "📢 공지사항",
        "🎉 이벤트",
        "고객센터",
        "📝 자주 묻는 질문",
        "1:1 문의하기",
        "❤️ 찜 목록",
        "주문/배송 내역",
        "교환/반품/취소",
        "간편결제"
    )

    Column(
        modifier = Modifier.padding(top = 16.dp)
    ) {
        menuItems.forEach { item ->
            ClickableMenuItem(text = item)
        }
    }
}

@Composable
fun ClickableMenuItem(text: String) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* 클릭 이벤트 처리 */ }
            .padding(vertical = 12.dp)
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            modifier = Modifier.align(Alignment.CenterStart)
        )

    }
}
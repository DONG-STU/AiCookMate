package com.sdc.aicookmate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
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
            modifier = Modifier
                .padding(paddingValues)
                .background(Color(0xFFF8F8F8))
        ) {
            ActionButtons(
                buttons = listOf("스크랩 레시피", "구독 목록", "후기 관리"),
                onButtonClick = { /* TODO */ }
            )

            RecentRecipeSection(
                recipes = listOf(
                    Recipe("맛있는 비빔밥", "조회수", "32회"),
                    Recipe("고소한 김치찌개", "조회수", "45회"),
                    Recipe("맛있는 비빔밥", "조회수", "45회"),
                    Recipe("고소한 김치찌개", "조회수", "45회"),
                    Recipe("맛있는 비빔밥", "조회수", "45회")

                )
            )

            MenuList(
                menuItems = listOf(
                    "📢 공지사항",
                    "🎉 이벤트",
                    "고객센터",
                    "📝 자주 묻는 질문",
                    "1:1 문의하기"
                )
            )
        }
    }
}

@Composable
fun ActionButtons(buttons: List<String>, onButtonClick: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp) // 버튼 간 간격 조정
    ) {
        buttons.forEach { buttonText ->
            Button(
                onClick = { onButtonClick(buttonText) },
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.contentcolorgreen)),
                shape = RoundedCornerShape(10),
                modifier = Modifier
                    .weight(1f) // 버튼 크기를 동일하게 설정
                    .height(60.dp),
                contentPadding = PaddingValues(0.dp) // 텍스트가 정확히 가운데 정렬되도록 패딩 제거
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center // 텍스트를 완전히 가운데 정렬
                ) {
                    Text(
                        text = buttonText,
                        fontSize = 14.sp,
                        maxLines = 1 // 텍스트가 여러 줄로 늘어나는 것을 방지
                    )
                }
            }
        }
    }
}



data class Recipe(val title: String, val views: String, val viewCount: String)


@Composable
fun RecentRecipeSection(recipes: List<Recipe>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            "최근에 본 레시피",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 10.dp)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(300.dp)
            .verticalScroll(rememberScrollState())
    ) {

        recipes.forEach { recipe ->
            RecentRecipeCard(
                title = recipe.title,
                views = recipe.views,
                viewCount = recipe.viewCount
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun MenuList(menuItems: List<String>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .background(Color.White)
            .clip(RoundedCornerShape(8.dp))
    ) {
        menuItems.forEach { item ->
            ClickableMenuItem(text = item)
        }
    }}

@Composable
fun RecentRecipeCard(
//    imageUrl: String,
    title: String,
    views: String,
    viewCount: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .height(140.dp)
            .border(
                BorderStroke(
                    width = 3.dp,
                    color = colorResource(R.color.contentcolorgreen)
                ),
                shape = RoundedCornerShape(15.dp)
            ),
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults
            .cardColors(containerColor = Color.White)
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
fun ClickableMenuItem(text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* 클릭 이벤트 처리 */ }
            .padding(vertical = 12.dp, horizontal = 16.dp)
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            modifier = Modifier.align(Alignment.CenterStart)
        )
    }
}


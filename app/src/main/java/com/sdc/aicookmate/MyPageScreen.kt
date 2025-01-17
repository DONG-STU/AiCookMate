package com.sdc.aicookmate

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun MyPageScreen(navController: NavController) {
    Scaffold(
        bottomBar = { BottomBar(navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .background(Color(0xFFFCF6E0))
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
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

            NoticeList(
                menuItems = listOf(
                    "📢 공지사항",
                    "🎉 이벤트",
                    "고객센터",
                    "📝 자주 묻는 질문",
                    "1:1 문의하기"
                )
            )
            Text(
                text = "We Contact", fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp),
                color = Color.Black
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 9.dp, horizontal = 16.dp)
            ) {

                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0x00ffffff)),
                    modifier = Modifier
                        .size(45.dp)
                        .weight(1f)
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_instagram),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0x00ffffff)),
                    modifier = Modifier
                        .size(45.dp)
                        .weight(1f)
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_discord),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0x00ffffff)),
                    modifier = Modifier
                        .size(45.dp)
                        .weight(1f)
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_facebook),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
                }

            }
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
    val savedRecipes = remember { mutableStateMapOf<String, Boolean>() }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            "최근에 본 레시피",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 10.dp),
            color = Color.Black
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(370.dp)
            .verticalScroll(rememberScrollState())
            .background(Color(0xFFFFFFFF))
    ) {

        recipes.forEach { recipe ->

            val isSaved = savedRecipes[recipe.title] ?: false

            RecentRecipeCard(
                title = recipe.title,
                views = recipe.views,
                viewCount = recipe.viewCount,
                isSaved = isSaved,
                onSaveClick = { newState -> savedRecipes[recipe.title] }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun NoticeList(menuItems: List<String>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 9.dp)
            .padding(horizontal = 16.dp)
            .background(Color.White)
            .clip(RoundedCornerShape(8.dp))
    ) {
        menuItems.forEach { item ->
            ClickableMenuItem(text = item)
        }
    }
}

@Composable
fun RecentRecipeCard(
//    imageUrl: String,
    title: String,
    views: String,
    viewCount: String,
    isSaved: Boolean,
    onSaveClick: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp, horizontal = 15.dp)
            .height(110.dp)
            .border(
                BorderStroke(
                    width = 2.dp,
                    color = colorResource(R.color.contentcolorgreen)
                ),
                shape = RoundedCornerShape(15.dp)
            ),
        shape = RoundedCornerShape(15.dp),
//        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults
            .cardColors(containerColor = Color.White)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.recipe1),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(15.dp))
                    .weight(0.4f),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.weight(0.02f))

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(0.58f)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.35f)
                ) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Text(text = title, modifier = Modifier.align(Alignment.BottomStart),
                            color = Color.Black)

                        IconButton(modifier = Modifier.align(Alignment.TopEnd),
                            onClick = { onSaveClick(!isSaved) } // 저장 상태 토글
                        ) {
                            Icon(
                                painter = painterResource(
                                    if (isSaved) R.drawable.ic_aftersavebutton // 저장됨 아이콘
                                    else R.drawable.ic_beforesavebutton // 저장 전 아이콘
                                ),
                                contentDescription = null,
                                tint = Color.Unspecified, // 기본 색상 유지
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.6f)
                ) {
                    Text(text = "dngkgkkdslhsdfkjshdfiulsdhgiusiudyfiusrehfosudhflskdnflskd",
                        color = Color.Black)
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.25f)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(0.5f)
                        ) {
                            Text(text = "tlqkf",
                                color = Color.Black)
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(0.5f)
                        ) {
                            Text(text = "tlqkf",
                                color = Color.Black
                            )
                        }
                    }
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
            modifier = Modifier.align(Alignment.CenterStart),
            color = Color.Black
        )
    }
}


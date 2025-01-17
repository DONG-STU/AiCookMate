package com.sdc.aicookmate

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@Preview
@Composable
fun GptScreenPreview() {
    //  GptScreen()
}

@Composable
fun GptScreen(navController: NavController) {
    val scrollState = rememberLazyListState()
    var remainingCount by remember { mutableStateOf(3) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Image(
                painter = painterResource(id = R.drawable.ic_arrowback),
                contentDescription = "뒤로가기",
                modifier = Modifier
                    .size(40.dp)
                    .clickable { /* */ }
            )

            Text(
                text = "이런 메뉴 어떠세요?",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .weight(1f)
                    .border(
                        width = 1.dp,
                        color = Color(0xFF90AA8D),
                        shape = RoundedCornerShape(12.dp)
                    )
            ) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    // 첫 번째 박스
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.2f)
                    ) {
                        Text("hi",modifier=Modifier.padding(8.dp))
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(Color(0xFF90AA8D))
                    )

                    // 2,3번째 박스를 합친 LazyColumn
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.8f)  // 0.6f + 0.2f
                    ) {
                        LazyColumn(
                            state = scrollState,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            // Middle Items (20개)
                            items(20) { index ->
                                Text(
                                    text = "Middle Item $index",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp)
                                )
                            }

                            // 구분선
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(1.dp)
                                        .background(Color(0xFF90AA8D))
                                )
                            }

                            // Bottom Items (10개)
                            items(10) { index ->
                                Text(
                                    text = "Bottom Item $index",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp)
                                )
                            }
                        }
                    }
                }
            }

            RecipeRecommendationButton(
                remainingCount = remainingCount,
                onCountDecrease = { remainingCount-- }
            )
        }
    }
}

@Composable
fun RecipeRecommendationButton(
    remainingCount: Int,
    onCountDecrease: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                if (remainingCount > 0) {
                    onCountDecrease()
                }
            },
            enabled = remainingCount > 0,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(136, 193, 136),
                contentColor = Color.Black,
                disabledContainerColor = Color(136, 193, 136).copy(alpha = 0.5f)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(horizontal = 12.dp, vertical = 4.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row {
                    Text(
                        "레시피 다시추천 받기",
                        style = TextStyle(
                            fontSize = 16.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Image(
                        painterResource(id = R.drawable.ic_swap),
                        contentDescription = "새로고침",
                        modifier = Modifier.size(24.dp),
                        colorFilter = ColorFilter.tint(Color.White)
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    "남은 횟수: $remainingCount",
                    style = TextStyle(
                        fontSize = 12.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Normal
                    )
                )
            }
        }
    }
}
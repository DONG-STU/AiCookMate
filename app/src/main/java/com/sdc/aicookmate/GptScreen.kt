package com.sdc.aicookmate

import android.util.Log
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun GptScreen(navController: NavController) {
    val scrollState = rememberLazyListState()
    var remainingCount by remember { mutableStateOf(3) }
    var recipeName by remember { mutableStateOf("") }
    var recipeIngredients by remember { mutableStateOf("") }
    var recipeMethod by remember { mutableStateOf("") }

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
                    .clickable { navController.navigateUp() }
            )

            Text(
                text = "이런 메뉴 어떠세요?",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            )

            // Recipe Box
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
                Column(modifier = Modifier.fillMaxSize()) {
                    // Recipe Name
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.2f)
                            .padding(8.dp)
                    ) {
                        Text(recipeName)
                    }

                    // Divider
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(Color(0xFF90AA8D))
                    )

                    // Recipe Ingredients and Method
                    LazyColumn(
                        state = scrollState,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        item {
                            Text("재료: $recipeIngredients", modifier = Modifier.padding(8.dp))
                        }

                        item {
                            Text("방법: $recipeMethod", modifier = Modifier.padding(8.dp))
                        }
                    }
                }
            }

            RecipeRecommendationButton(
                remainingCount = remainingCount,
                onCountDecrease = {
                    remainingCount--
                    if (remainingCount >= 0) {
                        fetchRecipe(
                            prompt = "내가 가진 재료는 '$ingreidentsSelected'이고 '$titleSelected'을 요리하려고 해. 레시피를 만들어줘.",
                            onSuccess = { response ->
                                recipeName = response.choices[0].message.content.substringBefore("\n")
                                recipeIngredients = response.choices[0].message.content
                                    .substringAfter("\n재료: ")
                                    .substringBefore("\n방법:")
                                recipeMethod = response.choices[0].message.content.substringAfter("\n방법: ")
                            },
                            onError = {
                                recipeName = "에러 발생!"
                                recipeIngredients = ""
                                recipeMethod = ""
                            }
                        )
                    }
                }
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

// GPT API 호출 함수
fun fetchRecipe(
    prompt: String,
    onSuccess: (GptResponse) -> Unit,
    onError: () -> Unit
) {
    val apiService = ApiClient.apiService

    // GptRequest 객체 생성
    val request = GptRequest(
        model = "gpt-3.5-turbo",
        messages = listOf(
            Message(role = "user", content = prompt)
        )
    )

    Log.d("GPT API", "Request started with prompt: $prompt") // 요청 시작 로그

    // API 호출
    apiService.getGptResponse(
        authorization = "Bearer ${BuildConfig.OPENAI_API_KEY}",
        request = request
    ).enqueue(object : retrofit2.Callback<GptResponse> {
        override fun onResponse(call: retrofit2.Call<GptResponse>, response: retrofit2.Response<GptResponse>) {
            if (response.isSuccessful) {
                Log.d("GPT API", "Response received: ${response.body()}") // 성공 로그
                response.body()?.let(onSuccess) ?: onError()
            } else {
                Log.e("GPT API", "Error: ${response.code()} - ${response.message()}") // 실패 로그
                onError()
            }
        }

        override fun onFailure(call: retrofit2.Call<GptResponse>, t: Throwable) {
            Log.e("GPT API", "Failure: ${t.message}") // 네트워크 오류 로그
            onError()
        }
    })
}



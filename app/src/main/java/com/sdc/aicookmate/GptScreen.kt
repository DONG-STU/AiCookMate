package com.sdc.aicookmate

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
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
import com.google.gson.Gson

@Composable
fun GptScreen(navController: NavController) {
    val scrollState = rememberLazyListState()

    var remainingCount by remember { mutableStateOf(3) }
    var recipeName by remember { mutableStateOf("") }
    var recipeIngredients by remember { mutableStateOf("") }
    var recipeMethod by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    val defaultErrorMessage = "레시피를 가져오는 중 오류가 발생했습니다."

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            // 뒤로가기
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

            // 레시피 박스
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
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                } else {
                    Column(modifier = Modifier.fillMaxSize()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(0.2f)
                                .padding(8.dp)
                        ) {
                            Text(recipeName)
                        }

                        // 구분선
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(Color(0xFF90AA8D))
                        )

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
            }

            // 버튼
            Button(
                onClick = {
                    if (!isLoading && remainingCount > 0) {
                        remainingCount--
                        isLoading = true
                        fetchRecipe(
                            prompt = "내가 가진 재료는 돼지목살, 김치, 간장, 설탕, 라면, 두부, 팽이버섯이고 다이어트요리를 요리하려고 해. 레시피를 만들어줘.",
                            onSuccess = { response ->
                                isLoading = false
                                // 간단 파싱
                                val content = response.choices.getOrNull(0)?.message?.content ?: ""
                                recipeName = content.substringBefore("\n")
                                recipeIngredients = content
                                    .substringAfter("\n재료: ", "")
                                    .substringBefore("\n방법:")
                                recipeMethod = content.substringAfter("\n방법: ", "")
                            },
                            onError = {
                                isLoading = false
                                recipeName = defaultErrorMessage
                                recipeIngredients = ""
                                recipeMethod = ""
                            }
                        )
                    }
                },
                enabled = (remainingCount > 0 && !isLoading),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(136, 193, 136),
                    contentColor = Color.Black
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .padding(horizontal = 12.dp, vertical = 4.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = "레시피 다시추천 받기 (남은 횟수: $remainingCount)")
            }
        }
    }
}

// 실제 API 호출 로직
fun fetchRecipe(
    prompt: String,
    onSuccess: (GptResponse) -> Unit,
    onError: () -> Unit
) {
    val apiService = ApiClient.apiService

    // 조직용 모델: "gpt-4o", "gpt-4o-mini", etc.
    // 원하는 모델로 교체하세요. (예: "gpt-4o-2024-08-06")
    val request = GptRequest(
        model = "gpt-3.5-turbo-0125",
        messages = listOf(
            Message(role = "user", content = prompt)
        )
    )

    Log.d("GPT API", "Request JSON: ${Gson().toJson(request)}")

    apiService.getGptResponse(
        authorization = "Bearer ${BuildConfig.OPENAI_API_KEY}",
        request = request
    ).enqueue(object : retrofit2.Callback<GptResponse> {
        override fun onResponse(call: retrofit2.Call<GptResponse>, response: retrofit2.Response<GptResponse>) {
            if (response.isSuccessful) {
                Log.d("GPT API", "Response: ${response.body()}")
                response.body()?.let(onSuccess) ?: onError()
            } else {
                Log.e("GPT API", "Error: ${response.code()} - ${response.message()}")
                onError()
            }
        }

        override fun onFailure(call: retrofit2.Call<GptResponse>, t: Throwable) {
            Log.e("GPT API", "Failure: ${t.message}")
            onError()
        }
    })
}

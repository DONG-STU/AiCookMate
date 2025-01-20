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
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
            // 뒤로가기 버튼 (혹은 NavController.popBackStack() 등)
            Image(
                painter = painterResource(id = R.drawable.ic_arrowback),
                contentDescription = "뒤로가기",
                modifier = Modifier
                    .size(40.dp)
                    .clickable {
                        navController.popBackStack()
                    }
            )

            Text(
                text = "이런 메뉴 어떠세요?",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(start = 8.dp)
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
                Column(modifier = Modifier.fillMaxSize()) {
                    // 레시피 이름
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
                    // 재료와 방법
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

            // 레시피 추천 버튼
            Button(
                onClick = {
                    remainingCount--
                    if (remainingCount >= 0) {
                        fetchRecipe(
                            prompt = "내가 가진 재료는 '양파, 돼지고기, 간장, 고추장, 메이플시럽'이고 '찜/탕'을 요리하려고 해. 레시피를 만들어줘.",
                            onSuccess = { response ->
                                val content = response.choices[0].message.content
                                recipeName = content.substringBefore("\n")
                                recipeIngredients = content
                                    .substringAfter("\n재료: ", "")
                                    .substringBefore("\n방법:")
                                recipeMethod = content.substringAfter("\n방법: ", "")
                            },
                            onError = {
                                recipeName = "에러 발생!"
                                recipeIngredients = ""
                                recipeMethod = ""
                            }
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF90AA8D),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = "레시피 추천 (${remainingCount}번 남음)", fontSize = 16.sp)
            }
        }
    }
}

fun fetchRecipe(
    prompt: String,
    onSuccess: (GptResponse) -> Unit,
    onError: () -> Unit
) {
    val apiService = ApiClient.apiService

    val request = GptRequest(
        model = "gpt-3.5-turbo",
        messages = listOf(
            Message(role = "user", content = prompt)
        )
    )

    Log.d("GPT API", "Request started with prompt: $prompt")

    apiService.getGptResponse(
        authorization = "Bearer ${BuildConfig.OPENAI_API_KEY}",
        request = request
    ).enqueue(object : Callback<GptResponse> {
        override fun onResponse(call: Call<GptResponse>, response: Response<GptResponse>) {
            if (response.isSuccessful) {
                Log.d("GPT API", "Response received: ${response.body()}")
                response.body()?.let(onSuccess) ?: onError()
            } else {
                Log.e("GPT API", "Error: ${response.code()} - ${response.message()}")
                onError()
            }
        }

        override fun onFailure(call: Call<GptResponse>, t: Throwable) {
            Log.e("GPT API", "Failure: ${t.message}")
            onError()
        }
    })
}

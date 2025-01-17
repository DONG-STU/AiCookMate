package com.sdc.aicookmate

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

// OpenAI 요청 바디 정의
data class GptRequest(
    val model: String,
    val messages: List<Message>,
    val max_tokens: Int = 300
)

// GPT 메시지 형식
data class Message(
    val role: String, // "user", "system", or "assistant"
    val content: String
)

// OpenAI 응답 데이터 구조
data class GptResponse(
    val choices: List<Choice>
)

data class Choice(
    val message: Message
)

// API 인터페이스
interface ApiService {
    @POST("v1/chat/completions")
    fun getGptResponse(
        @Header("Authorization") authorization: String,
        @Body request: GptRequest
    ): Call<GptResponse>
}

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

// 메시지 / 요청 바디
data class GptRequest(
    val model: String,               // e.g. "gpt-4o", "gpt-4o-mini", ...
    val messages: List<Message>,
    val max_tokens: Int = 300
)

data class Message(
    val role: String,    // "user", "developer", "assistant"...
    val content: String
)

data class GptResponse(
    val choices: List<Choice>
)

data class Choice(
    val message: Message
)

// ChatCompletion Endpoint
interface ApiService {
    @POST("v1/chat/completions")
    fun getGptResponse(
        @Header("Authorization") authorization: String,
        @Body request: GptRequest
    ): Call<GptResponse>
}

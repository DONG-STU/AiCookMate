package com.sdc.aicookmate

import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.rememberAsyncImagePainter
import com.sdc.aicookmate.ui.theme.Pink80
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.Response
import java.io.File
import java.io.IOException
import java.util.UUID
import java.util.concurrent.TimeUnit


@Preview(showBackground = true)
@Composable
fun PreviewRefrigeratorImageScanScreen() {
    // Fake NavController for preview purposes
    val fakeNavController = rememberNavController()

    RefrigeratorImageScanScreen(navController = fakeNavController)
}

/**
 * 1) Flask 서버에서 반환하는 객체 정보
 */
data class DetectionItem(
    val name: String,
    val confidence: Float
)

/**
 * 2) Flask API 인터페이스
 */
interface FlaskApi {
    @Multipart
    @POST("/detect")
    fun uploadImage(
        @Part filePart: MultipartBody.Part
    ): Call<List<DetectionItem>>
}

/**
 * 3) Retrofit 클라이언트 싱글턴
 *    baseUrl : Flask 서버 IP:PORT
 */
object FlaskClient {
    private const val BASE_URL = "http://192.168.45.42:9400 "
    // ↑ 예시 IP. 실제 서버IP:PORT로 변경.

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .connectTimeout(120, TimeUnit.SECONDS)
        .readTimeout(120, TimeUnit.SECONDS)
        .writeTimeout(120, TimeUnit.SECONDS)
        .build()

    val api: FlaskApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FlaskApi::class.java)
    }
}

/**
 * 4) suspend 함수: 실제 업로드 (동기 .execute())
 */
suspend fun uploadRefrigeratorImage(
    uri: Uri,
    context: Context
): Result<List<DetectionItem>> {
    return try {
        // 4-1) Uri -> 임시 File
        val inputStream = context.contentResolver.openInputStream(uri)
            ?: return Result.failure(IOException("Fail open InputStream."))

        val tempFile = File(context.cacheDir, "upload_${UUID.randomUUID()}.jpg")
        inputStream.use { input ->
            tempFile.outputStream().use { output ->
                input.copyTo(output)
            }
        }

        // 4-2) Multipart
        val reqFile = tempFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val bodyPart = MultipartBody.Part.createFormData("file", tempFile.name, reqFile)

        // 4-3) 동기 호출
        val response = withContext(Dispatchers.IO) {
            FlaskClient.api.uploadImage(bodyPart).execute()
        }

        if (response.isSuccessful) {
            val list = response.body().orEmpty()
            Result.success(list)
        } else {
            Result.failure(IOException("Server Error: ${response.code()}"))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }
}

/**
 * 5) 카메라 이미지 Uri 생성
 */
fun createImageUri(context: Context): Uri {
    val resolver = context.contentResolver
    val contentValues = ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME, "photo_${System.currentTimeMillis()}.jpg")
        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
    }
    return resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
        ?: throw IllegalStateException("Failed to create new MediaStore record.")
}

/**
 * 6) 메인 Composable
 *    - 갤러리 or 카메라로 이미지 받아서 Flask 서버 업로드
 */
@Composable
fun RefrigeratorImageScanScreen(navController: NavController) {
    var inputText by remember { mutableStateOf("") }
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val selectedIngredients = remember { mutableStateListOf<String>() }

    // 이미지 URI
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    // 모델 검출 결과
    var detectionList by remember { mutableStateOf<List<DetectionItem>>(emptyList()) }

    // 로딩 상태
    var isLoading by remember { mutableStateOf(false) }

    // 상단 텍스트
    var resultText by remember { mutableStateOf("") }

    // (A) CoroutineScope (→ 버튼 onClick에서 launch)
    val scope = rememberCoroutineScope()

    // 갤러리에서 이미지 선택
    val pickLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uriSelected ->
        if (uriSelected != null) {
            imageUri = uriSelected
        }
    }

    // 카메라 권한
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (!granted) {
            Toast.makeText(context, "카메라 권한이 필요합니다.", Toast.LENGTH_SHORT).show()
        }
    }

    // 카메라 촬영
    val cameraLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        if (!success) {
            Toast.makeText(context, "사진 촬영 실패", Toast.LENGTH_SHORT).show()
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        Spacer(modifier = Modifier.height(10.dp))

            // 검색 필드
        EnhancedSearchDropdown(
            items = ingredients,
            selectedItems = ingreidentsSelected, // 여기서 매개변수 이름을 맞춤
            placeholderText = "재료를 검색하세요"
        ) { selectedItem ->
            println("선택된 항목: $selectedItem")
        }

        Spacer(modifier = Modifier.height(10.dp))

//        // 즐겨찾기 박스
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .weight(1f) // 즐겨찾기 박스 높이 고정
//                .padding(horizontal = 20.dp)
//                .border(1.dp, Color.Black, RoundedCornerShape(10.dp))
//                .background(Color(0xFFFFFCCB))
//        ) {
//            // 선택된 이미지 표시
//            imageUri?.let { uri ->
//                Image(
//                    painter = rememberAsyncImagePainter(uri),
//                    contentDescription = "selected image",
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .border(2.dp, Color.Gray),
//                    contentScale = ContentScale.Fit
//                )
//            }
//        }

//        Spacer(modifier = Modifier.height(10.dp))

        Column(
            modifier = Modifier
                .background(color = Color.White)
                .fillMaxSize()
                .padding(10.dp)
                .weight(1f)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color(0x80F5F5F5))
                    .weight(3f)
            ) {
                Text(
                    "추가된 재료",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(3.dp)
                        .fillMaxWidth()
                )


                Column(
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .verticalScroll(scrollState)
                        .fillMaxWidth()
                ) {
                    for (ingredient in selectedIngredients) {
                        ListOfIngredientsUI(ingredient)
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Button(
                        onClick = {
                            val newIngredients =
                                selectedIngredients.filter { it !in ingreidentsSelected }
                            ingreidentsSelected.addAll(newIngredients.distinct())
                            navController.navigateUp()
                            Toast.makeText(context, "재료를 추가했어요!", Toast.LENGTH_SHORT).show()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.titleColor) // XML 색상 적용
                        ),
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier
                            .size(100.dp)
                            .align(Alignment.BottomEnd) // 버튼을 오른쪽 아래로 배치
                            .padding(10.dp)
                    ) {
                        Text(
                            "확인",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier . padding(vertical = 20.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
                .weight(0.7f)

        ) {
            Row {
                Button(
                    onClick = {
                        val permission = android.Manifest.permission.CAMERA
                        val granted =
                            context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
                        if (!granted) {
                            permissionLauncher.launch(permission)
                        } else {
                            val uri = createImageUri(context)
                            imageUri = uri
                            cameraLauncher.launch(uri)
                        }
                    },
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.titleColor) // XML 색상 적용
                    ),
                    modifier = Modifier

                        .size(70.dp)
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.ic_plus),
                        contentDescription = "카메라 버튼 이미지",
                        modifier = Modifier.size(40.dp)
                    )
                }
                Column(modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp)) {
                    Text(
                        "카메라 촬영",
                        fontSize = 24.sp,
                        modifier = Modifier,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("냉장고를 촬영하고 사진을 넣어보세요",fontSize = 12.sp)
                }
            }

            Spacer(modifier = Modifier.height(25.dp))

            Row {
                Button(
                    onClick = {
                        pickLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                    },
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.titleColor) // XML 색상 적용
                    ),

                    modifier = Modifier

                        .size(70.dp)
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.ic_plus),
                        contentDescription = "사진 버튼 이미지",
                        modifier = Modifier.size(40.dp)
                    )
                }
                Column(modifier = Modifier.padding(horizontal = 10.dp,vertical = 3.dp)) {
                    Text("사진 넣기",
                        fontSize = 24.sp,
                        modifier = Modifier,
                        fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("갤러리에 있는 사진을 등록해보세요",fontSize = 12.sp)
                }
            }

            Spacer(modifier = Modifier.height(25.dp))
            Row {
                Button(
                    onClick = {
                        if (imageUri == null) {
                            Toast.makeText(context, "이미지를 먼저 선택하세요", Toast.LENGTH_SHORT).show()
                            return@Button
                        }
                        // 코루틴 스코프에서 suspend 함수 호출
                        scope.launch {
                            isLoading = true
                            resultText = ""
                            detectionList = emptyList()

                            val res = uploadRefrigeratorImage(imageUri!!, context)
                            isLoading = false
                            if (res.isSuccess) {
                                val list = res.getOrNull().orEmpty()
                                detectionList = list
                                val sb = StringBuilder()
                                sb.append("감지된 객체: ${list.size}개\n")
                                for (item in list) {
                                    sb.append("${item.name} : ${"%.2f".format(item.confidence)}\n")
                                }
                                resultText = sb.toString()
                            } else {
                                resultText = "업로드 실패: ${res.exceptionOrNull()?.message}"
                            }
                            when {
                                "onion" in resultText -> selectedIngredients.add("양파")
                                "egg" in resultText -> selectedIngredients.add("계란")
                                "garlic" in resultText -> selectedIngredients.add("마늘")
                                "green onion" in resultText -> selectedIngredients.add("대파")
                                "pepper" in resultText -> selectedIngredients.add("고추")
                                "potato" in resultText -> selectedIngredients.add("감자")
                            }
                        }
                    },
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.titleColor) // XML 색상 적용
                    ),
                    modifier = Modifier

                        .size(70.dp)
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.ic_plus),
                        contentDescription = "사진 버튼 이미지",
                        modifier = Modifier.size(40.dp)
                    )

                }
                Column(modifier = Modifier.padding(horizontal = 5.dp, vertical = 3.dp)) {
                    Text(
                        "재료 탐지",
                        fontSize = 24.sp,
                        modifier = Modifier,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("카메라를 재료에 대보면 무슨 재료인지 알려드려요",fontSize = 12.sp)
                }
            }
        }
        // 로딩 표시
        if (isLoading) {
            CircularProgressIndicator()
        }

    }
}

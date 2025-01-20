package com.sdc.aicookmate

import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import com.sdc.aicookmate.ui.theme.AiCookMateTheme

//@Preview
@Composable
fun ScanRefrigeratorPhoto(navController: NavController) {
    var inputText by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    val pickMedia =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                imageUri = uri
            }
        }

    // Camera Launcher 설정
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Toast.makeText(context, "카메라 권한 허용됨", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "카메라 권한이 필요합니다.", Toast.LENGTH_SHORT).show()
        }
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            if (success) {
                Toast.makeText(context, "사진 저장 완료", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "사진 촬영 실패", Toast.LENGTH_SHORT).show()
            }
        }
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFFCF6E0))
    ) {
        // 상단 여백
        Spacer(modifier = Modifier.height(10.dp))

        // 검색 필드
        OutlinedTextField(
            value = inputText,
            onValueChange = { inputText = it },
            leadingIcon = { Icon(Icons.Default.Search, "검색") },
            maxLines = 1,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .height(60.dp) // 검색 필드 높이 고정
                .border(3.dp, Color.LightGray, RoundedCornerShape(10.dp)),
            shape = RoundedCornerShape(10.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))

        // 즐겨찾기 박스
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp) // 즐겨찾기 박스 높이 고정
                .padding(horizontal = 20.dp)
                .border(1.dp, Color.Black, RoundedCornerShape(10.dp))
                .background(Color(0xFFFFFCCB))
        ) {
            Text("즐겨찾기", fontSize = 15.sp, modifier = Modifier.padding(10.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black)
                    .height(1.dp)
            )
            Row() {
                PostIt("양파")
                PostIt("콩나물")
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        // 냉장고 이미지

        Image(
            rememberAsyncImagePainter(imageUri),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .weight(1f)
                .border(3.dp, Color.LightGray, RectangleShape)
        )

        Box(
            modifier = Modifier
                .weight(0.4f)
                .fillMaxWidth()
                .background(Color.White)
        ) {
            Text("다음 재료를 추가할게요!", modifier = Modifier.align(Alignment.TopStart))
            Row (modifier = Modifier.padding(top = 10.dp)){
                PostIt("양파")
                PostIt("대파")
                PostIt("계란")
                PostIt("콩나물")
            }
            Button(onClick = {
                navController.navigateUp()
                Toast.makeText(context, "재료를 추가했어요!", Toast.LENGTH_SHORT).show()
            },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xffFF5D5D)),
                modifier = Modifier.align(Alignment.BottomEnd)) {
                Text("확인")
            }
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp) // 버튼 Row 높이 고정
                .padding(horizontal = 10.dp)
        ) {
            Button(
                onClick = {
                    pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("사진 넣기")
            }
            Button(
                onClick = {
                    if (ContextCompat.checkSelfPermission(
                            context,
                            android.Manifest.permission.CAMERA
                        ) ==
                        PackageManager.PERMISSION_GRANTED
                    ) {
                        // 권한이 허용된 경우 카메라 실행
                        val uri = createImageUri(context)
                        cameraLauncher.launch(uri)
                        imageUri = uri
                    } else {
                        // 권한 요청
                        permissionLauncher.launch(android.Manifest.permission.CAMERA)
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("카메라 촬영")
            }
            Button(
                onClick = {
                    navController.navigateUp()
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("뒤로 가기")
            }
        }
    }
}

// 이미지 Uri 생성 함수
 fun createImageUri(context: Context): Uri {
    val resolver = context.contentResolver
    val contentValues = ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME, "photo_${System.currentTimeMillis()}.jpg")
        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
    }
    return resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
        ?: throw IllegalStateException("Failed to create new MediaStore record.")
}

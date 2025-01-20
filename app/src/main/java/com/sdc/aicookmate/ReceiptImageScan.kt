package com.sdc.aicookmate

import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.korean.KoreanTextRecognizerOptions
import java.io.IOException

//@Preview
@Composable
fun ScanReceiptImage(navController: NavController) {
    var inputText by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    val pickMedia =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                imageUri = uri
            }
        }
    var receiptText by remember { mutableStateOf("") }

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

    LaunchedEffect(imageUri) {
        if (imageUri != null) {
            val recognizer =
                TextRecognition.getClient(KoreanTextRecognizerOptions.Builder().build())
            try {
                val image = InputImage.fromFilePath(context, imageUri!!)
                recognizer.process(image)
                    .addOnSuccessListener { result ->
                        receiptText = result.text
                    }
                    .addOnFailureListener { e ->
                    }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFFCF6E0))
    ) {
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
                .height(180.dp) // 즐겨찾기 박스 높이 고정
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

            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 10.dp)
                .fillMaxSize()
                .background(Color.White)
        ) {
            Column(modifier = Modifier.fillMaxSize()
                .padding(10.dp)) {
                Text(
                    "영수증",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(3.dp)
                        .fillMaxWidth()
                )
                Text("[매장명] : AI CookMate")
                Text("[매출일] : 20XX.XX.XX")

                Spacer(modifier = Modifier.height(10.dp))

                Box(modifier = Modifier.fillMaxWidth()
                    .background(Color.Black)
                    .padding(horizontal = 3.dp)
                    .height(1.dp))
                Spacer(modifier = Modifier.height(10.dp))
                Box(modifier = Modifier.fillMaxWidth()
                    .background(Color.Black)
                    .padding(horizontal = 3.dp)
                    .height(1.dp))
                ListOfIngredientsUI(receiptText)
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

@Composable
fun ListOfIngredientsUI(text:String){
    Column (modifier = Modifier.padding(vertical = 10.dp)){
        Box(modifier = Modifier.fillMaxWidth()
            .background(Color.LightGray)
            .height(1.dp))
        Row (horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 5.dp)){
            Text(text)
            Image(painter = painterResource(R.drawable.postit_close_btn),
                contentDescription = "닫기 버튼")
        }
        Box(modifier = Modifier.fillMaxWidth()
            .background(Color.LightGray)
            .height(1.dp))
    }
}
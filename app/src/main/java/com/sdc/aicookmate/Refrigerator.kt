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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Preview(showBackground = true)
@Composable
fun ShowScreen() {
    Refrigerator()
}

@Composable
fun Refrigerator() {
    var inputText by remember { mutableStateOf("") }

    Column(
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
            Row {
                PostIt("시바아")
                PostIt("이게되네")
                PostIt("ㄹㅇㅋㅋ")
            }
        }
        Spacer(modifier = Modifier.height(10.dp))

        // 냉장고 이미지
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f) // 남은 공간을 냉장고 이미지가 차지
                .padding(horizontal = 10.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.refrigerator),
                contentDescription = "냉장고",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize()
            )
            Column(modifier = Modifier.padding(5.dp)) {
                Row(modifier = Modifier.padding(5.dp)) {
                    PostIt("양파")
                    PostIt("대파")
                    PostIt("시금치")
                    PostIt("콩나물시금치")

                }
                Row(modifier = Modifier.padding(5.dp)) {
                    PostIt("아무튼졸라긴거적음")
                }
            }
        }

        // 버튼 Row
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp) // 버튼 Row 높이 고정
                .padding(horizontal = 10.dp)
        ) {
            Button(
                onClick = {},
                shape = RoundedCornerShape(10.dp), // 모서리를 둥글게 설정
                modifier = Modifier
                    .weight(1f) // 버튼 균등 배치
                    .border(3.dp, Color.LightGray, RoundedCornerShape(10.dp)) // 둥근 외곽선
                    .height(60.dp), // 버튼 높이 고정
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.contentcolorgreen))
            ) {
                Image(
                    painter = painterResource(R.drawable.scan_camera),
                    contentDescription = "냉장고 스캔",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("냉장고 스캔", fontSize = 14.sp)
            }

            Spacer(modifier = Modifier.width(20.dp))
            Button(
                onClick = {},
                shape = RoundedCornerShape(10.dp), // 모서리를 둥글게 설정
                modifier = Modifier
                    .weight(1f) // 버튼 균등 배치
                    .border(3.dp, Color.LightGray, RoundedCornerShape(10.dp)) // 둥근 외곽선
                    .height(60.dp), // 버튼 높이 고정
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.contentcolorgreen))
            ) {
                Image(
                    painter = painterResource(R.drawable.scan_reciept),
                    contentDescription = "영수증 스캔",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("영수증 스캔", fontSize = 14.sp)
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
    }
}


//}

@Composable
fun PostIt(text: String) {
    var imgSize by remember { mutableIntStateOf(0) }
    when {
        text.length < 4 -> imgSize = 60
        text.length < 7 -> imgSize = 90
        text.length < 10 -> imgSize = 120
    }
    Box(
        modifier = Modifier
            .size(imgSize.dp)
            .padding(5.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.post_it_pink),
            contentDescription = "포스트잇", modifier = Modifier.size(imgSize.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 3.dp)
        ) {
            Text(text, fontSize = 10.sp)
            Image(
                painter = painterResource(R.drawable.postit_close_btn),
                contentDescription = "닫기", modifier = Modifier.size(10.dp)
            )
        }
    }
}
package com.sdc.aicookmate

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
//    Scaffold(
//        bottomBar = { BottomBar(navController) }
//    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
//                .padding(paddingValues)
//                .verticalScroll(rememberScrollState())
                .background(color = Color(0xFFFCF6E0))
        ) {
            Spacer(modifier = Modifier.weight(0.5f))
            OutlinedTextField(
                value = inputText,
                onValueChange = { inputText = it },
                leadingIcon = { Icon(Icons.Default.Search, "검색") },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                ),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp)
                    .border(3.dp, Color.LightGray, RoundedCornerShape(10.dp))
                    .weight(1.3f),
                shape = RoundedCornerShape(10.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(3f)
                    .padding(20.dp)
                    .border(1.dp, Color.Black, RoundedCornerShape(10.dp))
                    .background(Color.White)
            ) {
                Text("즐겨찾기", fontSize = 15.sp, modifier = Modifier.padding(10.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Black)
                        .height(1.dp)
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(14f)
                    .padding(horizontal = 10.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.refrigerator),
                    contentDescription = "냉장고",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(horizontal = 10.dp)
                    .weight(3f)
            ) {
                Button(
                    onClick = {},
                    shape = RectangleShape,
                    modifier = Modifier.weight(5f)
                        .wrapContentHeight(),
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.contentcolorgreen))
                ) {
                    Image(painter = painterResource(R.drawable.scan_camera),
                        contentDescription = "냉장고 스캔",
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier.size(40.dp))
                    Text("냉장고 스캔", fontSize = 17.sp)
                }
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = {},
                    shape = RectangleShape,
                    modifier = Modifier.weight(5f)
                        .wrapContentHeight(),
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.contentcolorgreen))
                ) {
                    Image(painter = painterResource(R.drawable.scan_reciept),
                        contentDescription = "영수증 스캔",
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier.size(40.dp))
                    Text("영수증 스캔", fontSize = 17.sp)
                }
            }
        }
    }
//}
package com.sdc.aicookmate

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

//@Preview
//@Composable
//fun ShowHowAddIngredientsScreen() {
//    ShowHowRecommend()
//}

@Composable
fun ShowHowAddIngredients(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .weight(1f)
                .background(color = colorResource(R.color.titleColor))
        ) {
            Text(
                "어느 방법으로 추가할까요?",
                textAlign = TextAlign.Center,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.fillMaxWidth()
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = {navController.navigate("ScanRefrigeratorPhoto")},
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.titleColor)),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .weight(3f)
                .padding(20.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    painter = painterResource(R.drawable.refrigerator_scan_btn),
                    contentDescription = "냉장고 스캔",
                    Modifier.fillMaxSize()
                        .weight(3f)
                )
                Text(
                    "냉장고 스캔",
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxSize()
                        .weight(1f)
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = {navController.navigate("ScanReceiptImage")},
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.titleColor)),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .weight(3f)
                .padding(20.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    painter = painterResource(R.drawable.receipt_scan_btn),
                    contentDescription = "영수증 스캔",
                    Modifier.fillMaxSize()
                        .weight(3f)
                )
                Text(
                    "영수증 스캔",
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxSize()
                        .weight(1f)
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun ShowHowRecommend(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .weight(1f)
                .background(color = colorResource(R.color.titleColor))
        ) {
            Text(
                "어느 방법으로 추천할까요?",
                textAlign = TextAlign.Center,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.fillMaxWidth()
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = {navController.navigate("selectRecipeScreen")},
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.titleColor)),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .weight(3f)
                .padding(20.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    "AI 추천",
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = {navController.navigate("RecipeRecommendScreen")},
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.titleColor)),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .weight(3f)
                .padding(20.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    "기존 레시피로 추천",
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}
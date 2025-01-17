package com.sdc.aicookmate

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.sdc.aicookmate.ui.theme.AiCookMateTheme
import java.time.format.TextStyle



@Composable
fun RecipeExplainScreen(navController: NavController) {
    Scaffold(
        bottomBar = { BottomBar(navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .background(Color(0xFFFCF6E0))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFFFFBF0))
                    .padding(16.dp)
            ) {

                Text(
                    text = "생일 축하합니다~♬ 참 쉬운 미역국 끓이기!",
                    style = androidx.compose.ui.text.TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                )

                Text(
                    text = "미역국 끓이는 건 어렵지 않습니다!\n집에서 쉽게 만들 수 있는 미역국 가족, 친구 생일에 직접 끓여 보세요~",
                    modifier = Modifier.padding(vertical = 8.dp),
                    style = androidx.compose.ui.text.TextStyle(
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                )


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    Text("2인분")
                    Text("30분 이내")
                    Text("초급")
                }


                Image(
                    painter = painterResource(id = R.drawable.ic_banner),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp),
                    contentScale = ContentScale.FillWidth
                )


                Text(
                    text = "[재료]",
                    modifier = Modifier.padding(vertical = 16.dp),
                    style = androidx.compose.ui.text.TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                )

                val ingredients = listOf(
                    "소고기(양지) 1/2컵(100g)",
                    "자른미역 1/3컵(10g)",
                    "참기름 2큰술(14g)",
                    "국간장 5큰술(30g)",
                    "물 약4컵(1.3L)",
                    "다진마늘 2/3큰술(10g)",
                    "멸치액젓 1과1/2큰술(15g)"
                )

                ingredients.forEach { ingredient ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(ingredient)
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = Color.Black
                        )
                    }


                    Text(
                        text = "[조리방법]",
                        modifier = Modifier.padding(vertical = 16.dp),
                        style = androidx.compose.ui.text.TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )

                    val steps = listOf(
                        "1. 미역은 물에 불려 준비한다.",
                        "2. 냄비에 참기름을 두르고 소고기를 넣어 중약불에서 볶아준다.",
                        "3. 소고기가 익으면 불린 미역을 넣어 함께 볶아준다.",
                        "4. 볶아진 미역에 국간장을 넣어 볶고 물을 넣어 끓여준다.",
                        "5. 물이 끓으면 다진 마늘을 넣어 끓여준다.",
                        "6. 마지막에 액젓을 이용하여 간을 맞춰 완성한다."
                    )

                    steps.forEach { step ->
                        Text(
                            text = step,
                            modifier = Modifier.padding(vertical = 4.dp),
                            style = androidx.compose.ui.text.TextStyle(fontSize = 14.sp)
                        )

                        FoodItems(navController)
                    }
                }
            }
        }
    }
}
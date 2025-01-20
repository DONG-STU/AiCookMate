package com.sdc.aicookmate

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun SelectRecipeScreen(navController: NavController){
    GPTFoodCategories(navController)
}

var titleSelected = ""

@Composable
fun GPTFoodCategories(navController: NavController) {
    val categories = listOf(
        "찜조림" to R.drawable.ic_zzim,
        "국/탕/찌개" to R.drawable.ic_gook,
        "볶음/구이" to R.drawable.ic_bokuem,
        "밥/죽" to R.drawable.ic_rice,
        "면/만두" to R.drawable.ic_noodle,
        "간편요리" to R.drawable.ic_quick,
        "야식" to R.drawable.ic_chicken,
        "다이어트" to R.drawable.ic_diet,
        "인플루언서" to R.drawable.ic_influencer
    )

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(0.dp)

    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween

        ) {
            GPTFoodCategoryItem(
                navController,
                title = categories[0].first,
                iconRes = categories[0].second,
            )
            GPTFoodCategoryItem(
                navController,
                title = categories[1].first,
                iconRes = categories[1].second,
            )
            GPTFoodCategoryItem(
                navController,
                title = categories[2].first,
                iconRes = categories[2].second,
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            GPTFoodCategoryItem(
                navController,
                title = categories[3].first,
                iconRes = categories[3].second,
            )
            GPTFoodCategoryItem(
                navController,
                title = categories[4].first,
                iconRes = categories[4].second,
            )
            GPTFoodCategoryItem(
                navController,
                title = categories[5].first,
                iconRes = categories[5].second,
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            GPTFoodCategoryItem(
                navController,
                title = categories[6].first,
                iconRes = categories[6].second,
            )
            GPTFoodCategoryItem(
                navController,
                title = categories[7].first,
                iconRes = categories[7].second,
            )
            GPTFoodCategoryItem(
                navController,
                title = categories[8].first,
                iconRes = categories[8].second,
            )
        }
    }
}

@Composable
fun GPTFoodCategoryItem(navController: NavController, title: String, iconRes: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(100.dp)
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(percent = 20))
                .background(Color.White)
                .clickable {
                    titleSelected = title
                    navController.navigate("GptScreen")
                }
                .border(
                    width = 0.5.dp,
                    color = Color.LightGray,
                    shape = RoundedCornerShape(percent = 20)
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .size(100.dp)

                ) {
                    Image(
                        painter = painterResource(id = iconRes),
                        contentDescription = null,
                        modifier = Modifier
                            .size(80.dp)
                            .align(Alignment.TopCenter),
                        contentScale = ContentScale.Crop
                    )

                    Text(
                        text = title,
                        modifier = Modifier.align(Alignment.BottomCenter),
                        style = TextStyle(
                            fontSize = 16.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }
    }
}

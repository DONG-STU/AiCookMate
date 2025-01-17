package com.sdc.aicookmate


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.test.isSelected
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.sdc.aicookmate.ui.theme.AiCookMateTheme


@Composable
fun RecipeScreen(navController: NavController) {
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
                    .padding(16.dp)

            ) {


                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    leadingIcon = { Icon(Icons.Default.Search, "검색") },
                    placeholder = { Text("재료나 요리명을 검색하세요") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White, RoundedCornerShape(30.dp)),
                    shape = RoundedCornerShape(30.dp)
                )

                Spacer(modifier = Modifier.height(3.dp))

                FoodCategories()

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    RecentButton(
                        text = "최신순",
                        isSelected = true,
                        onClick = { /**/ }
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    RecentButton(
                        text = "인기순",
                        isSelected = false,
                        onClick = { /**/ }
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                FoodItems()

            }
        }
    }
}


@Composable
fun FoodCategories() {
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
            .fillMaxWidth()
            .padding(horizontal = 5.dp),
        verticalArrangement = Arrangement.spacedBy(0.dp)

    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween

        ) {
            FoodCategoryItem(
                title = categories[0].first,
                iconRes = categories[0].second
            )
            FoodCategoryItem(
                title = categories[1].first,
                iconRes = categories[1].second
            )
            FoodCategoryItem(
                title = categories[2].first,
                iconRes = categories[2].second
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            FoodCategoryItem(
                title = categories[3].first,
                iconRes = categories[3].second
            )
            FoodCategoryItem(
                title = categories[4].first,
                iconRes = categories[4].second
            )
            FoodCategoryItem(
                title = categories[5].first,
                iconRes = categories[5].second
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            FoodCategoryItem(
                title = categories[6].first,
                iconRes = categories[6].second
            )
            FoodCategoryItem(
                title = categories[7].first,
                iconRes = categories[7].second
            )
            FoodCategoryItem(
                title = categories[8].first,
                iconRes = categories[8].second
            )
        }
    }
}

@Composable
fun FoodCategoryItem(title: String, iconRes: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(80.dp)
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(percent = 20))
                .background(Color.White)
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
                        .size(50.dp)
                        .clip(CircleShape)

                ) {
                    Image(
                        painter = painterResource(id = iconRes),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = title,
                    style = TextStyle(
                        fontSize = 11.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}


@Composable
fun FoodItems() {
    val foodItems = listOf(
        FoodItem(
            "맛있는 미역국",
            "30분",
            R.drawable.ic_sallad1
        ),
        FoodItem(
            "매콤달콤 불고기",
            "45분",
            R.drawable.ic_sallad2
        ),
        FoodItem(
            "일품한 김치찌개",
            "40분",
            R.drawable.ic_sallad3
        )
    )

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(1.dp)
    ) {
        items(foodItems.size) { index ->
            FoodItemCard(foodItems[index])
        }
    }
}

@Composable
fun FoodItemCard(foodItem: FoodItem) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
    ) {

        Image(
            painter = painterResource(id = foodItem.imageRes),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .height(150.dp),
            contentScale = ContentScale.Crop
        )
    }


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(5.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {

                androidx.compose.foundation.text.BasicText(
                    text = foodItem.title,
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                )

                Spacer(modifier = Modifier.height(1.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_time),
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        colorFilter = ColorFilter.tint(Color.Gray)
                    )
                    Box(
                        modifier = Modifier
                            .padding(bottom = 3.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            BasicText(
                                text = foodItem.duration,
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    color = Color.Gray
                                )
                            )
                            DifficultyStars(DifficultyLevel.BEGINNER)
                        }
                    }
                }
            }
        }
    }
}

data class FoodItem(
    val title: String,
    val duration: String,
    val imageRes: Int
)


enum class DifficultyLevel(val text: String) {
    BEGINNER("☆초급"),
    INTERMEDIATE("☆중급"),
    ADVANCED("☆고급")
}

@Composable
fun DifficultyStars(level: DifficultyLevel) {
    Text(
        text = level.text,
        style = TextStyle(
            fontSize = 16.sp,
            color = Color.Gray,
            fontWeight = FontWeight.Normal
        )
    )
}

@Composable
fun RecentButton(
    text: String,
    isSelected: Boolean = false,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) Color.Black else Color.White,
            contentColor = if (isSelected) Color.White else Color.Black
        ),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(
            width = 1.dp,
            color = if (isSelected) Color.Black else Color.Gray.copy(alpha = 0.3f)
        ),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        modifier = Modifier.height(36.dp)
    ) {
        Text(
            text = text,
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        )
    }
}
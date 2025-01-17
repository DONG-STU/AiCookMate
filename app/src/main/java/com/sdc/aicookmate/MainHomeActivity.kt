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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.sdc.aicookmate.ui.theme.AiCookMateTheme

@Composable
fun MainScreen(navController: NavController) {
    Scaffold(
        bottomBar = { BottomBar(navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .background(Color(0xFFFCF6E0))
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth(),
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    leadingIcon = { Icon(Icons.Default.Search, "검색") },
                    placeholder = { Text("레시피 검색") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White, RoundedCornerShape(30.dp)),
                    shape = RoundedCornerShape(30.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))
                RefrigeratorButton()
                Spacer(modifier = Modifier.height(24.dp))

                ChuchunList()
                Spacer(modifier = Modifier.height(12.dp))
                ChuChunCard()
                Spacer(modifier = Modifier.height(32.dp))

                CategorySelector()
                Spacer(modifier = Modifier.height(32.dp))

                BestRecipe()
                Spacer(modifier = Modifier.height(12.dp))
                BestListCard()


                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun RefrigeratorButton() {

    Button(
        onClick = {},
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(136, 193, 136),
            contentColor = Color.Black
        ), modifier = Modifier
            .fillMaxWidth()
            .height(110.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_kitchen),
                contentDescription = "냉장고 아이콘",
                modifier = Modifier
                    .width(80.dp)
                    .height(90.dp),
                contentScale = ContentScale.FillBounds
            )
            Spacer(modifier = Modifier.width(100.dp))

            Text(
                text = "냉장고 관리하기",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.width(10.dp))

            Image(
                painter = painterResource(id = R.drawable.ic_arrow),
                contentDescription = "냉장고 아이콘",
                modifier = Modifier
                    .size(16.dp)
            )
        }
    }
}

@Composable
fun ChuchunList() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(1.dp)
    )
    {

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {


            Text(
                "오늘의 추천 레시피",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Box() {
                Text(
                    "더보기>",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier
                        .padding(bottom = 4.dp)

                )
            }
            Spacer(modifier = Modifier.width(10.dp))
        }
    }
}


@Composable
fun ChuChunCard() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        RecipeCard(imageRes = R.drawable.recipe1, description = "")
        RecipeCard(imageRes = R.drawable.recipe2, description = "")
        RecipeCard(imageRes = R.drawable.recipe3, description = "")
    }
}


@Composable
fun RecipeCard(imageRes: Int, description: String) {
    Column(
        modifier = Modifier
            .width(150.dp)
            .height(130.dp)

    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .clip(RoundedCornerShape(13.dp))
        )
        Text(
            text = description,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(8.dp),
            color = Color.Black
        )
    }
}

@Composable
fun CategorySelector() {
    var selectedCategory by remember { mutableStateOf("한식") }
    Column {
        Text(
            "카테고리",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier
                .padding(bottom = 8.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            CategoryItem(
                iconRes = R.drawable.ic_korean,
                label = "한식",
                isSelected = selectedCategory == "한식",
                onClick = { selectedCategory = "한식" }
            )
            CategoryItem(
                iconRes = R.drawable.ic_japanese,
                label = "일식",
                isSelected = selectedCategory == "일식",
                onClick = { selectedCategory = "일식" }
            )
            CategoryItem(
                iconRes = R.drawable.ic_chinese,
                label = "중식",
                isSelected = selectedCategory == "중식",
                onClick = { selectedCategory = "중식" }
            )
            CategoryItem(
                iconRes = R.drawable.ic_western,
                label = "양식",
                isSelected = selectedCategory == "양식",
                onClick = { selectedCategory = "양식" }
            )
            CategoryItem(
                iconRes = R.drawable.ic_boonsik,
                label = "분식",
                isSelected = selectedCategory == "분식",
                onClick = { selectedCategory = "분식" }
            )
            CategoryItem(
                iconRes = R.drawable.ic_desert,
                label = "디저트",
                isSelected = selectedCategory == "디저트",
                onClick = { selectedCategory = "디저트" }
            )
            CategoryItem(
                iconRes = R.drawable.ic_sallad,
                label = "샐러드",
                isSelected = selectedCategory == "샐러드",
                onClick = { selectedCategory = "샐러드" }
            )
            CategoryItem(
                iconRes = R.drawable.ic_baking,
                label = "베이킹",
                isSelected = selectedCategory == "베이킹",
                onClick = { selectedCategory = "베이킹" }
            )
            CategoryItem(
                iconRes = R.drawable.ic_snack,
                label = "스낵",
                isSelected = selectedCategory == "스낵",
                onClick = { selectedCategory = "스낵" }
            )
        }
    }
}

@Composable
fun CategoryItem(
    iconRes: Int,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable { onClick() }
            .background(
                color = if (isSelected) Color(0xFFE3F2FD) else Color.White,
                shape = RoundedCornerShape(12.dp)
            )
            .border(
                width = if (isSelected) 2.dp else 0.dp,
                color = if (isSelected) Color.Blue else Color.Transparent,
                shape = RoundedCornerShape(12.dp)
            )
            .size(80.dp)
            .padding(8.dp)
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = label,
            modifier = Modifier.size(40.dp)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}

@Composable
fun BestRecipe() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(1.dp)

    )
    {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom

        ) {

            Text(
                "실시간 베스트 레시피",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Text(
                "더보기>",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier
                    .padding(bottom = 4.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
        }
    }
}

@Composable
fun BestListCard() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        BestCard(imageRes = R.drawable.recipe1, description = "")
        BestCard(imageRes = R.drawable.recipe2, description = "")
        BestCard(imageRes = R.drawable.recipe3, description = "")
    }
}


@Composable
fun BestCard(imageRes: Int, description: String) {
    Column(
        modifier = Modifier
            .width(150.dp)
            .height(130.dp)

    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .clip(RoundedCornerShape(13.dp))
        )
        Text(
            text = description,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(8.dp),
            color = Color.Black
        )
    }
}

@Composable
fun BottomBar(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(Color(0xFFF2F2F2))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_home),
                contentDescription = "메인 홈 아이콘",
                modifier = Modifier
                    .size(40.dp)
                    .clickable { navController.navigate("main") }
            )
            Image(
                painter = painterResource(id = R.drawable.ic_cart),
                contentDescription = "카트 아이콘",
                modifier = Modifier
                    .size(40.dp)
                    .clickable { navController.navigate("shopping") }
            )
            Image(
                painter = painterResource(id = R.drawable.ic_kitchen),
                contentDescription = "냉장고 아이콘",
                modifier = Modifier
                    .size(40.dp)
                    .clickable { }
            )
            Image(
                painter = painterResource(id = R.drawable.ic_bookheart),
                contentDescription = "찜 레시피 아이콘",
                modifier = Modifier
                    .size(40.dp)
                    .clickable { navController.navigate("Recipe") }
            )
            Image(
                painter = painterResource(id = R.drawable.ic_person),
                contentDescription = "마이페이지 아이콘",
                modifier = Modifier
                    .size(40.dp)
                    .clickable { navController.navigate("myPage") }
            )
        }
    }
}


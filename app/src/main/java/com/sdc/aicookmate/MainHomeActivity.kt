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
import androidx.compose.foundation.layout.heightIn
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
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.sdc.aicookmate.ui.theme.AiCookMateTheme
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign

@Composable
fun MainScreen(navController: NavController) {
    val viewModel: RecipeViewModel = viewModel()

    Scaffold(
        bottomBar = { BottomBar(navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .background(Color.White)
        ) {
            Column( //검색창 컬럼
                modifier = Modifier
                    .height(90.dp)
                    .fillMaxWidth()
                    .background(color = colorResource(R.color.titleColor))
            ) { //검색창 컬럼
                FirebaseDropdown(
                    viewModel = viewModel,
                    placeholderText = "레시피 검색"
                ) { selectedRecipe ->
                    println("선택된 레시피: ${selectedRecipe.title}")
                    navController.navigate("recipeDetail/${selectedRecipe.title}")
                }
            } //검색창컬럼

            Box( //레시피리스트 박스
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { navController.navigate("refigeratorScreen") } // 클릭 시 이동
            ) { //레시피리스트 박스
                Image(
                    painter = painterResource(id = R.drawable.recipelist),
                    contentDescription = "홈 레시피 리스트",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .height(350.dp)
                        .align(Alignment.Center),
                    contentScale = ContentScale.FillBounds
                )
            } //레시피리스트 박스

            Column( //카테고리 컬럼
                modifier = Modifier
                    .fillMaxWidth()
            ) {//카테고리 컬럼
                Text(//텍스트
                    text = "레시피 종류",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 9.dp)
//                        .background(color = colorResource(R.color.titleColor))
//                        .width(130.dp)
//                        .height(30.dp),
//                        textAlign = TextAlign.Center

                )//텍스트
                CategorySelector(navController)
            }//카테고리 컬럼

            Spacer(modifier = Modifier.height(20.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 9.dp, horizontal = 16.dp)
            ) {
                Text(
                    text = "실시간 인기 레시피",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                )

                Text(
                    "더보기>",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                )

            }



            BestListCard()


        }//최상위 컬럼
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
fun CategorySelector(navController: NavController) {
    var selectedCategory by remember { mutableStateOf("한식") }
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            CategoryItem(
                iconRes = R.drawable.ic_zzim,
                label = "찜/조림",
                isSelected = selectedCategory == "찜/조림",
                onClick = {
                    selectedCategory = "찜/조림"
                    navController.navigate("zzimScreen") // 찜/조림 화면으로 이동
                }
            )
            CategoryItem(
                iconRes = R.drawable.ic_gook,
                label = "국/탕/찌개",
                isSelected = selectedCategory == "국/탕/찌개",
                onClick = {
                    selectedCategory = "국/탕/찌개"
                    navController.navigate("gookScreen") // 국/탕/찌개 화면으로 이동
                }
            )
            CategoryItem(
                iconRes = R.drawable.ic_bokuem,
                label = "볶음/구이",
                isSelected = selectedCategory == "볶음/구이",
                onClick = {
                    selectedCategory = "볶음/구이"
                    navController.navigate("bokuemScreen") // 볶음/구이 화면으로 이동
                }
            )
            CategoryItem(
                iconRes = R.drawable.ic_rice,
                label = "밥/죽",
                isSelected = selectedCategory == "밥/죽",
                onClick = {
                    selectedCategory = "밥/죽"
                    navController.navigate("riceScreen") // 밥/죽 화면으로 이동
                }
            )
            CategoryItem(
                iconRes = R.drawable.ic_noodle,
                label = "면/만두",
                isSelected = selectedCategory == "면/만두",
                onClick = {
                    selectedCategory = "면/만두"
                    navController.navigate("noodleScreen") // 면/만두 화면으로 이동
                }
            )
            CategoryItem(
                iconRes = R.drawable.ic_quick,
                label = "간편요리",
                isSelected = selectedCategory == "간편요리",
                onClick = {
                    selectedCategory = "간편요리"
                    navController.navigate("quickScreen") // 간편요리 화면으로 이동
                }
            )
            CategoryItem(
                iconRes = R.drawable.ic_chicken,
                label = "야식",
                isSelected = selectedCategory == "야식",
                onClick = {
                    selectedCategory = "야식"
                    navController.navigate("chickenScreen") // 야식 화면으로 이동
                }
            )
            CategoryItem(
                iconRes = R.drawable.ic_diet,
                label = "다이어트",
                isSelected = selectedCategory == "다이어트",
                onClick = {
                    selectedCategory = "다이어트"
                    navController.navigate("dietScreen") // 다이어트 화면으로 이동
                }
            )
            CategoryItem(
                iconRes = R.drawable.ic_influencer,
                label = "인플루언서",
                isSelected = selectedCategory == "인플루언서",
                onClick = {
                    selectedCategory = "인플루언서"
                    navController.navigate("influencerScreen") // 인플루언서 화면으로 이동
                }
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
                color = if (isSelected) Color.Blue else Color.Black,
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


    )
    {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom

        ) {

            Text(
                "실시간 인기 레시피",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                modifier = Modifier.padding(vertical = 9.dp)
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
                    .clickable { navController.navigate("refigeratorScreen") }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FirebaseDropdown(
    viewModel: RecipeViewModel,
    placeholderText: String,
    onItemSelected: (RecipeData) -> Unit
) {
    var inputText by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    val searchResults by viewModel.searchResults.collectAsState(initial = emptyList())

    LaunchedEffect(inputText) {
        if (inputText.isNotEmpty()) {
            viewModel.fetchRecipes2(inputText)
        } else {
            expanded = false // 입력이 비어 있으면 드롭다운 숨기기
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = inputText,
            onValueChange = { newValue ->
                inputText = newValue // 텍스트 필드 값 갱신
                expanded = true // 드롭다운 표시
            },
            placeholder = { Text(placeholderText) },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "검색") },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(30.dp)),
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = Color.White,
                focusedBorderColor = Color.Gray,
                unfocusedBorderColor = Color.LightGray
            )
        )

        if (expanded && searchResults.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .border(1.dp, Color.LightGray, shape = RoundedCornerShape(8.dp))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 200.dp) // 최대 높이 제한
                        .verticalScroll(rememberScrollState())
                ) {
                    searchResults.forEach { recipe ->
                        DropdownMenuItem(
                            text = { Text(text = recipe.title) },
                            onClick = {
                                inputText = recipe.title
                                expanded = false
                                onItemSelected(recipe)
                            }
                        )
                    }
                }
            }
        }
    }
}

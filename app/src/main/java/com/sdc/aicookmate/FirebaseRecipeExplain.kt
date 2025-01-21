package com.sdc.aicookmate

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.firestore.FirebaseFirestore






@Composable
fun RecipeDetailScreen(title: String) {
    val recipeDetail = remember { mutableStateOf<RecipeDetailData?>(null) }

    // Firestore 데이터 로드
    LaunchedEffect(title) {
        Log.d("RecipeDetailScreen", "Fetching recipe for title: $title")
        FirebaseFirestore.getInstance()
            .collection("aicookmaterecipe")
            .whereEqualTo("title", title) // title 필드 기준으로 조회
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    recipeDetail.value = querySnapshot.documents.firstOrNull()?.toObject(RecipeDetailData::class.java)
                    Log.d("Firestore", "Recipe found: ${recipeDetail.value}")
                } else {
                    Log.e("Firestore", "No document found for title: $title")
                }
            }
            .addOnFailureListener { exception ->
                Log.e("Firestore", "Error fetching data: ${exception.message}")
            }
    }

    // UI
    recipeDetail.value?.let { recipe ->
        RecipeDetailContent(recipe)
    } ?: Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Loading...")
    }
}



data class RecipeDetailData(
    val title: String = "",
    val thumbnail: String = "",
    val servings: String = "",
    val time_required: String = "",
    val difficulty: String = "",
    val description: String = "",
    val popularity: Int = 0,
    val registration_date: String = "",
    val category: String = "",
    val influencer: String = "",
    val ingredients: List<Ingredient> = emptyList(),
    val steps: List<Step> = emptyList(),
    val url: String = ""
)

data class Ingredient(
    val name: String = "",
    val quantity: String = ""
)

data class Step(
    val description: String = "",
    val image: String = ""
)

@Composable
fun RecipeDetailContent(recipe: RecipeDetailData) {
    val scrollState = rememberScrollState() // 스크롤 상태 추가

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState) // 스크롤 가능하도록 설정
    ) {
        // Title
        Text(text = recipe.title, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))

        // Thumbnail
        Image(
            painter = rememberAsyncImagePainter(recipe.thumbnail),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Recipe Details
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween, // 텍스트 간 간격 조절
            verticalAlignment = Alignment.CenterVertically // 텍스트와 이미지 정렬
        ) {
            // Servings
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.ic_people), // 아이콘 리소스 추가
                    contentDescription = "Servings Icon",
                    modifier = Modifier.size(16.dp) // 아이콘 크기 설정
                )
                Spacer(modifier = Modifier.width(1.dp)) // 텍스트와 이미지 간 간격
                Text(text = "${recipe.servings}")
            }

            // Time Required
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.ic_time), // 아이콘 리소스 추가
                    contentDescription = "Time Icon",
                    modifier = Modifier.size(16.dp) // 아이콘 크기 설정
                )
                Spacer(modifier = Modifier.width(1.dp)) // 텍스트와 이미지 간 간격
                Text(text = "${recipe.time_required}")
            }

            // Difficulty
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.ic_star), // 아이콘 리소스 추가
                    contentDescription = "Difficulty Icon",
                    modifier = Modifier.size(16.dp) // 아이콘 크기 설정
                )
                Spacer(modifier = Modifier.width(1.dp)) // 텍스트와 이미지 간 간격
                Text(text = "${recipe.difficulty}")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Description
        Text(text ="Tips:"+ recipe.description)
        Spacer(modifier = Modifier.height(16.dp))

        // Ingredients
        Text(text = "재료:")
        recipe.ingredients.forEach {
            Text(text = "- ${it.name}: ${it.quantity}")
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Steps
        Text(text = "조리방법:")
        recipe.steps.forEachIndexed { index, step ->
            Text(text = "${index + 1}. ${step.description}")
            Spacer(modifier = Modifier.height(8.dp))
            Image(
                painter = rememberAsyncImagePainter(step.image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp)
                    .padding(bottom = 8.dp)
            )
        }
    }
}

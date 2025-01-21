package com.sdc.aicookmate

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.IgnoreExtraProperties
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// 전역 변수로 선택된 재료 관리
var ingredientsSelected = mutableStateListOf<String>()

@Composable
fun RecipeRecommendScreen(navController: NavController) {
    val viewModel: RecipeRecommendViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
    val recipes = viewModel.recipes.collectAsState(initial = emptyList()).value
    RecipeList(recipes = recipes, navController = navController)
}

@IgnoreExtraProperties
data class RecipeRecommendData(
    val title: String = "",
    val thumbnail: String = "",
    val servings: String = "",
    val time_required: String = "",
    val difficulty: String = "",
    val ingredients: List<String> = emptyList() // Firestore 데이터 형식: 문자열 리스트
)

class RecipeRecommendViewModel : ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()
    private val _recipes = MutableStateFlow<List<RecipeRecommendData>>(emptyList())
    val recipes: StateFlow<List<RecipeRecommendData>> = _recipes

    init {
        fetchFilteredRecipes()
    }

    fun fetchFilteredRecipes() {
        viewModelScope.launch {
            firestore.collection("bokuem")
                .orderBy(FieldPath.documentId())
                .get()
                .addOnSuccessListener { result ->
                    val filteredRecipes = result.documents.mapNotNull { document ->
                        try {
                            document.toObject(RecipeRecommendData::class.java)
                        } catch (e: Exception) {
                            println("Mapping error: ${e.message}")
                            null
                        }
                    }.filter { recipe ->
                        recipe.ingredients.count { it in ingredientsSelected } >= 1
                    }.take(5) // 최대 5개 레시피
                    _recipes.value = filteredRecipes

                    if (filteredRecipes.isEmpty()) {
                        println("No matching recipes found.")
                    }
                }
                .addOnFailureListener { e ->
                    println("Firestore fetch failed: ${e.message}")
                    _recipes.value = emptyList()
                }
        }
    }
}

@Composable
fun RecipeList(recipes: List<RecipeRecommendData>, navController: NavController) {
    val scrollState = rememberScrollState()

    if (recipes.isEmpty()) {
        // 데이터가 없을 때 메시지 표시
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "No recipes found.", modifier = Modifier.padding(16.dp))
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp)
                .verticalScroll(scrollState)
        ) {
            recipes.forEach { recipe ->
                RecipeItem(item = recipe) { encodedTitle ->
                    navController.navigate("recipeDetail/$encodedTitle")
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun RecipeItem(item: RecipeRecommendData, onClick: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = rememberAsyncImagePainter(item.thumbnail),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clickable {
                        onClick(Uri.encode(item.title))
                    }
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = item.title, modifier = Modifier.padding(bottom = 12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(60.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Servings: ${item.servings}")
                Text(text = "Time: ${item.time_required}")
                Text(text = "Difficulty: ${item.difficulty}")
            }
        }
    }
}

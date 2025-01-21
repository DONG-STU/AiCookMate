package com.sdc.aicookmate

import android.app.Application
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


data class RecipeData(
    val title: String = "",
    val thumbnail: String = ""
)

class RecipeViewModel : ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()

    // StateFlow로 데이터를 관리
    private val _recipes = MutableStateFlow<List<RecipeData>>(emptyList())
    val recipes: StateFlow<List<RecipeData>> = _recipes

    init {
        fetchRecipes()
    }

    private fun fetchRecipes() {
        viewModelScope.launch {
            val randomOffset = (0..20).random() // 20개 중 랜덤 오프셋 설정

            firestore.collection("aicookmaterecipe")
                .orderBy(FieldPath.documentId()) // 정렬 기준 설정
                .startAfter(randomOffset.toString()) // 랜덤 오프셋에서 시작
                .limit(5) // 5개 문서 가져오기
                .get()
                .addOnSuccessListener { result ->
                    val recipeList = result.documents.mapNotNull { document ->
                        document.toObject(RecipeData::class.java)
                    }
                    _recipes.value = recipeList
                }
                .addOnFailureListener {
                    _recipes.value = emptyList()
                }
        }
    }
}


    @Composable
fun RecipeItem(item: RecipeData, onClick: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column {
            Image(
                painter = rememberAsyncImagePainter(item.thumbnail),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clickable { onClick(item.title) } // title 값을 전달
            )
            Text(
                text = item.title,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}



@Composable
fun RecipeList(recipes: List<RecipeData>, navController: NavController) {
    val scrollState = rememberScrollState()
    Column(

        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
    ) {
        recipes.forEach { item ->
            RecipeItem(item) { recipeId ->
                navController.navigate("FirebaseExplainRecipe/$recipeId")
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}



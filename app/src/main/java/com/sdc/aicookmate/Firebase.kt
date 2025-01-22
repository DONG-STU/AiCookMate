package com.sdc.aicookmate


import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil3.compose.rememberAsyncImagePainter
import com.google.firebase.firestore.FieldPath
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay

data class RecipeData(
    val title: String = "",
    val thumbnail: String = "",
    val servings: String = "",
    val time_required: String = "",
    val difficulty: String = "",
)

class RecipeViewModel : ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()

    // StateFlow로 데이터를 관리
    private val _recipes = MutableStateFlow<List<RecipeData>>(emptyList())
    val recipes: StateFlow<List<RecipeData>> = _recipes

    private val _searchResults = MutableStateFlow<List<RecipeData>>(emptyList())
    val searchResults: StateFlow<List<RecipeData>> = _searchResults

    private var searchJob: Job? = null


    init {
        fetchRecipes()
    }

    private fun fetchRecipes() {
        viewModelScope.launch {
            val randomOffset = (100..500).random() // 500개 중 랜덤 오프셋 설정

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
    // 드롭다운용 검색 쿼리
    fun fetchRecipes2(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(300) // Debounce 처리
            firestore.collection("aicookmaterecipe")
                .orderBy("title") // 제목 기준 정렬
                .startAt(query)
                .endAt(query + "\uf8ff") // Firestore의 범위 검색
                .limit(50) // 최대 50개의 결과 반환
                .get()
                .addOnSuccessListener { result ->
                    val recipeList = result.documents.mapNotNull { document ->
                        document.toObject(RecipeData::class.java)
                    }.distinctBy { it.title } // 중복 제거
                    _searchResults.value = recipeList
                }
                .addOnFailureListener {
                    _searchResults.value = emptyList()
                }
        }
    }

}


@Composable
fun RecipeList(recipes: List<RecipeData>, navController: NavController) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp)
            .verticalScroll(scrollState)
    ) {
        recipes.forEach { item ->
            RecipeItem(item = item) { encodedTitle ->
                // NavController를 사용하여 다음 화면으로 이동
                navController.navigate("recipeDetail/$encodedTitle")
            }
            Spacer(modifier = Modifier.height(8.dp))
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
                InfoRow(iconRes = R.drawable.ic_person, text = item.servings)
                InfoRow(iconRes = R.drawable.ic_time, text = item.time_required)
                InfoRow(iconRes = R.drawable.ic_star, text = item.difficulty)
            }
        }
    }
}

@Composable
fun InfoRow(iconRes: Int, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text)
    }
}


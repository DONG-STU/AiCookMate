package FoodCategoriesScreen

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


@Composable
fun dietScreen(navController: NavController) {

    DietScreen(navController)
}

@Composable
fun DietScreen(navController: NavController) {
    val viewModel: DietViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
    val recipes = viewModel.recipes.collectAsState(initial = emptyList()).value
    DietRecipeList(recipes = recipes, navController = navController)
}

data class DietRecipeData(
    val title: String = "",
    val thumbnail: String = "",
    val servings: String = "",
    val time_required: String = "",
    val difficulty: String = "",
)

class DietViewModel : ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()

    // StateFlow로 데이터를 관리
    private val _recipes = MutableStateFlow<List<DietRecipeData>>(emptyList())
    val recipes: StateFlow<List<DietRecipeData>> = _recipes

    init {
        fetchDietRecipes()
    }

    private fun fetchDietRecipes() {
        viewModelScope.launch {
            val randomOffset = (0..200).random() // 200개 중 랜덤 오프셋 설정

            firestore.collection("diet")
                .orderBy(FieldPath.documentId()) // 정렬 기준 설정
                .startAfter(randomOffset.toString()) // 랜덤 오프셋에서 시작
                .limit(5) // 5개 문서 가져오기
                .get()
                .addOnSuccessListener { result ->
                    val recipeList = result.documents.mapNotNull { document ->
                        document.toObject(DietRecipeData::class.java)
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
fun DietRecipeList(recipes: List<DietRecipeData>, navController: NavController) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp)
            .verticalScroll(scrollState)
    ) {
        recipes.forEach { item ->
            DietRecipeItem(item = item) { encodedTitle ->
                // NavController를 사용하여 다음 화면으로 이동
                navController.navigate("recipeDetail/$encodedTitle")
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun DietRecipeItem(item: DietRecipeData, onClick: (String) -> Unit) {
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
//                DietInfoRow(iconRes = R.drawable.ic_person, text = item.servings)
//                DietInfoRow(iconRes = R.drawable.ic_time, text = item.time_required)
//                DietInfoRow(iconRes = R.drawable.ic_star, text = item.difficulty)
            }
        }
    }
}

@Composable
fun DietInfoRow(iconRes: Int, text: String) {
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

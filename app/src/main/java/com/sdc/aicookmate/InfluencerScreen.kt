package com.sdc.aicookmate

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.sdc.aicookmate.model.Influencer

@Composable
fun InfluencerScreen(navController: NavController) { //
    Scaffold(
        bottomBar = { BottomBar(navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues) //
                .background(Color(0xFFFCF6E0))
                .fillMaxSize()

        ) {
            var inputText by remember { mutableStateOf("") }


            Column(
                modifier = Modifier
                    .padding(vertical = 16.dp, horizontal = 8.dp)
            ) {
                OutlinedTextField(
                    value = inputText,
                    onValueChange = { inputText = it },
                    leadingIcon = { Icon(Icons.Default.Search, "검색") },
                    placeholder = { Text("인플루언서를 검색해보세요") },
                    maxLines = 1,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .height(60.dp) // 검색 필드 높이 고정
                        .border(3.dp, Color.LightGray, RoundedCornerShape(10.dp)),
                    shape = RoundedCornerShape(10.dp)
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        "인플루언서",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(start = 16.dp, top = 16.dp, bottom = 4.dp),
                        color = Color.Black
                    )
                }

                ScrapInfluencerSection(
                    influencers = listOf(
                        Influencer("백종원", "누구나 쉽게 따라할 수 있는 요리가 가장 훌륭한 요리다"),
                        Influencer("류수영", "누구나 쉽게 따라할 수 있는 요리가 가장 훌륭한 요리다"),
                        Influencer("이영자", "누구나 쉽게 따라할 수 있는 요리가 가장 훌륭한 요리다")


                    )
                )
            }
        }
    }
}


@Composable
fun ScrapInfluencerSection(influencers: List<Influencer>) {
    val savedRecipes = remember { mutableStateMapOf<String, Boolean>() }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color(0xFFFFFFFF))
    ) {

        influencers.forEach { Influencer ->
            val isSaved = savedRecipes[Influencer.influencer] ?: false

            ScrapInfluencerCard(
                name = Influencer.influencer,
                descript = Influencer.descript,
                isSaved = isSaved,
                onSaveClick = { newState -> savedRecipes[Influencer.influencer] = newState }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}


@Composable
fun ScrapInfluencerCard(
//    imageUrl: String,
    name: String,
    descript: String,
    isSaved: Boolean,
    onSaveClick: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp, horizontal = 15.dp)
            .height(110.dp)
            .border(
                BorderStroke(
                    width = 2.dp,
                    color = colorResource(R.color.contentcolorgreen)
                ),
                shape = RoundedCornerShape(15.dp)
            ),
        shape = RoundedCornerShape(15.dp),
//        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults
            .cardColors(containerColor = Color.White)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.recipe1),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(15.dp))
                    .weight(0.4f),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.weight(0.02f))

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(0.58f)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.3f)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            modifier = Modifier.align(Alignment.BottomStart),
                            text = name,
                            color = Color.Black,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold
                        )

                        IconButton(modifier = Modifier.align(Alignment.TopEnd),
                            onClick = { onSaveClick(!isSaved) } // 저장 상태 토글
                        ) {
                            Icon(
                                painter = painterResource(
                                    if (isSaved) R.drawable.ic_aftersavebutton // 저장됨 아이콘
                                    else R.drawable.ic_beforesavebutton // 저장 전 아이콘
                                ),
                                contentDescription = null,
                                tint = Color.Unspecified, // 기본 색상 유지
                                modifier = Modifier.size(25.dp)
                            )
                        }
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.7f)
                ) {
                    Text(
                        text = descript,
                        color = Color.Black,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }


            }
        }
    }
}


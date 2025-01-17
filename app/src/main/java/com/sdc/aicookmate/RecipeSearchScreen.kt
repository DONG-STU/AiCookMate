package com.sdc.aicookmate

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Preview
@Composable
fun RecipeSearchScreenPreview() {
    RecipeSearchScreen()
}

@Composable
fun RecipeSearchScreen() {
    Column(modifier = Modifier.background(Color(0xFFFCF6E0))) {

        Spacer(modifier = Modifier.height(20.dp))

        Image(
            painter = painterResource(id = R.drawable.ic_arrowback),
            contentDescription = "뒤로가기",
            modifier = Modifier
                .size(40.dp)
                .clickable {
                    /**/
                }
        )

        Spacer(modifier = Modifier.height(20.dp))

        FoodCategories()

        Spacer(modifier = Modifier.height(300.dp))

        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(136, 193, 136),
                contentColor = Color.Black
            ), modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(12.dp),
            shape = RoundedCornerShape(8.dp)
        ) {

            Text(
                "레시피 추천 받기", style = TextStyle(
                    fontSize = 16.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            )
        }

    }
}
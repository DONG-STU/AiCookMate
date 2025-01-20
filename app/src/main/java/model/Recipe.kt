package com.sdc.aicookmate.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

//@Entity(tableName = "recipes", indices = [Index(value = ["title"], unique = true)])
//data class Recipe(
//    @PrimaryKey(autoGenerate = true) val id: Int = 0,
//    val title: String,
//    val description: String,
//    val views: Int
//)

@Entity(tableName = "recipes")
data class Recipe(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // 자동 생성되는 기본 키
    val title: String,
    val description: String,
    val views: Int
)

@Entity(tableName = "recent_recipes")
data class RecentRecipe(
    @PrimaryKey val recipeId: Int,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "subscribed_recipes")
data class SubscribedRecipe(
    @PrimaryKey val recipeId: Int
)

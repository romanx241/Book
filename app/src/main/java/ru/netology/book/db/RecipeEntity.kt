package ru.netology.book.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "recipe",
    foreignKeys = [ForeignKey(
        entity = CategoryEntity::class,
        parentColumns = ["category_id"],
        childColumns = ["category"]
    )]
)
class RecipeEntity(

    @PrimaryKey(autoGenerate = true)

    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "author")
    val author: String,
    @ColumnInfo(name = "content")
    val content: String,
    @ColumnInfo(name = "category")
    val category: Int,
    @ColumnInfo(name = "liked")
    val isLiked: Boolean,
    @ColumnInfo(name = "picture")
    val picture: String,
    )
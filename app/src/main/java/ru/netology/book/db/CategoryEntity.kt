package ru.netology.book.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "category")

class CategoryEntity(
        @PrimaryKey
        @ColumnInfo(name = "category_id")
        val id: Int,
        @ColumnInfo(name = "category_name")
        val categoryName: String,
        @ColumnInfo(name = "is_visible")
        val isVisible: Boolean,
)

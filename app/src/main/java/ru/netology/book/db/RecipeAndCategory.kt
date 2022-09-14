package ru.netology.book.db

import androidx.room.Embedded

data class RecipeAndCategory(
    @Embedded val recipe: RecipeEntity?,
    @Embedded val category: CategoryEntity?
)

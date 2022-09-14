package ru.netology.book.data

import androidx.lifecycle.LiveData
import ru.netology.book.dto.Category
import ru.netology.book.dto.Recipe

interface RecipeRepository {
    fun getAll(): LiveData<List<Recipe>>
    fun delete(recipeId: Long)
    fun save(recipe: Recipe)
    fun update(recipe: Recipe)
    fun insert(recipe: Recipe)
    fun saveAll(recipe: List<Recipe>)
    fun rowCount(): Int
    fun like(recipe: Recipe, isLiked: Boolean)
    fun getAllLike(): LiveData<List<Recipe>>
    fun saveFilterRecipe(list: List<String>)
    fun getFilterRecipe(): List<String>
    fun saveCategory(recipe: List<Category>)
    fun updateCategoryVisibility(list: List<Category>)
    fun categories(): LiveData<List<Category>>

    companion object {
        const val NEW_RECIPE_ID = 0L
    }

}
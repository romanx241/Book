package ru.netology.book.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import ru.netology.book.db.RecipeDao
import ru.netology.book.db.toEntity
import ru.netology.book.db.toModel
import ru.netology.book.dto.Category
import ru.netology.book.dto.Recipe

class RecipeRepositoryImpl(private val dao: RecipeDao) : RecipeRepository {
    private val data: LiveData<List<Recipe>> = dao.getAll().map {
        it.filter { it.category?.isVisible == true }.map { it.toModel() }
    }


    private val dataLike = dao.getAllLike().map {
        it.map {
            it.toModel()
        }
    }

    private val list = mutableListOf<String>()

    override fun getAll(): LiveData<List<Recipe>> = data
    override fun save(recipe: Recipe) {
        if (recipe.id == RecipeRepository.NEW_RECIPE_ID) dao.insert(recipe.toEntity())
        else dao.updateContentById(
            recipe.id, recipe.name, recipe.author, recipe.content, recipe.category
        )
    }

    override fun update(recipe: Recipe) {
        dao.updateContentById(
            recipe.id, recipe.name, recipe.author, recipe.content, recipe.category
        )
    }

    override fun insert(recipe: Recipe) {
        dao.insert(
            recipe.toEntity()
        )
    }

    override fun saveAll(recipe: List<Recipe>) {
        dao.saveAll(recipe.map { it.toEntity() })
    }

    override fun rowCount(): Int {
        return dao.rowCount()
    }

    override fun like(recipe: Recipe, isLiked: Boolean) {
        dao.updateLike(recipe.id, isLiked)
    }

    override fun getAllLike(): LiveData<List<Recipe>> = dataLike

    override fun saveFilterRecipe(list: List<String>) {
        this.list.addAll(list)
    }

    override fun getFilterRecipe(): List<String> = list

    override fun saveCategory(category: List<Category>) {
        dao.saveCategory(category.map { it.toEntity() })
    }

    override fun updateCategoryVisibility(list: List<Category>) {
        list.forEach {
            dao.updateCategoryVisibility(it.id, it.isVisible)
        }
    }

    override fun categories(): LiveData<List<Category>> = dao.categories().map {
        it.map {
            it.toModel()
        }
    }

    override fun delete(recipeId: Long) {
        dao.removeById(recipeId)
    }
    companion object {
        const val GENERATED_RECIPE_AMOUNT = 13
    }
}
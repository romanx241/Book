package ru.netology.book.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netology.book.adapter.RecipeInteractionListener
import ru.netology.book.data.RecipeRepository
import ru.netology.book.data.RecipeRepositoryImpl
import ru.netology.book.db.AppDb
import ru.netology.book.dto.Category
import ru.netology.book.dto.Recipe
import ru.netology.book.util.CategoryData
import ru.netology.book.util.RecipeData
import ru.netology.book.util.SingleLiveEvent
import ru.netology.book.util.toCategoryInt


class RecipeViewModel(application: Application) : AndroidViewModel(application),
    RecipeInteractionListener {
    private val repository: RecipeRepository = RecipeRepositoryImpl(
        dao = AppDb.getInstance(
            context = application
        ).postDao
    )
    var allRecipe = mutableListOf<Recipe>()
    var data = repository.getAll()
    var category = repository.categories()
    val dataLike = repository.getAllLike()
    private val currentRecipe = MutableLiveData<Recipe?>(null)
    private val navigateRecipeContentScreenEvent = SingleLiveEvent<String>()
    override fun onRemoveClicked(recipe: Recipe) = repository.delete(recipe.id)
    override fun onEditClicked(recipe: Recipe) {
        currentRecipe.value = recipe
        navigateRecipeContentScreenEvent.value = recipe.content.toString()
    }

    override fun onRecipeDetail(recipe: Recipe) {}
    override fun onRecipeLike(recipe: Recipe, isLiked: Boolean) {
        repository.like(recipe, isLiked)
    }

    override fun onRecipeFilter(list: List<Category>) {
        repository.saveCategory(list)
        repository.getAll()
    }

    fun onSaveButtonClicked(
        name: String,
        author: String,
        category: String,
        content: String,
        id: Long? = null
    ) {
        val recipe = currentRecipe.value?.copy(
            content = content
        ) ?: Recipe(
            id = id ?: RecipeRepository.NEW_RECIPE_ID,
            name = name,
            author = author,
            category = category.toCategoryInt(),
            content = content,
            isLiked = false,
            picture = "no",
        )
        repository.save(recipe)
        currentRecipe.value = null
    }

    fun addInitRecipes() {
        val k = repository.rowCount()
        if (k > 0) {
            return
        }
        val initRecipes = RecipeData.getInitRecipes()
        repository.saveCategory(CategoryData.getCategory())
        repository.saveAll(initRecipes)
    }

    fun addFullRecipe(recipe: List<Recipe>) {
        allRecipe.clear()
        allRecipe.addAll(recipe)
    }

    companion object {
        const val EUROPEAN = "Европейская кухня"
        const val MEDITERRANEAN = "Средиземноморская кухня"
        const val RUSSIAN = "Русская кухня"
        const val WEST = "Восточная кухня"
        const val AMERICAN = "Американская кухня"
        const val PANAZIA = "Паназиатская кухня"
        const val AZIA = "Азиатская кухня"
    }
}
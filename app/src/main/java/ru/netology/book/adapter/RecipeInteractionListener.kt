package ru.netology.book.adapter

import ru.netology.book.dto.Category
import ru.netology.book.dto.Recipe


interface RecipeInteractionListener {
     fun onRemoveClicked(recipe: Recipe)
     fun onEditClicked(recipe: Recipe)
     fun onRecipeDetail(recipe: Recipe)
     fun onRecipeLike(recipe: Recipe, isLiked: Boolean)
     fun onRecipeFilter(list: List<Category>)
//     fun onRecipeMove(recipe: Recipe)
}
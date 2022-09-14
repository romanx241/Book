package ru.netology.book.db

import ru.netology.book.dto.Recipe


internal fun RecipeEntity.toModel() = Recipe(

    id = id,
    name = name,
    author = author,
    content = content,
    category = category,
    isLiked = isLiked,
    picture = picture,

    )

internal fun Recipe.toEntity() = RecipeEntity(
    id = id,
    name = name,
    author = author,
    content = content,
    category = category,
    isLiked = isLiked,
    picture = picture,
)

internal fun RecipeAndCategory.toModel() = Recipe(
    id = recipe!!.id,
    name = recipe.name,
    author = recipe.author,
    content = recipe.content,
    category = recipe.category,
    isLiked = recipe.isLiked,
    picture = recipe.picture,
)
package ru.netology.book.db

import ru.netology.book.dto.Category


internal fun CategoryEntity.toModel() = Category(
    id = id,
    name = categoryName,
    isVisible = isVisible
)

internal fun Category.toEntity() = CategoryEntity(
    id = id,
    categoryName = name,
    isVisible = isVisible
)
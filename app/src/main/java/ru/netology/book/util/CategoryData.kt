package ru.netology.book.util

import ru.netology.book.dto.Category

object CategoryData {

    private var category = listOf(
        Category(
            0,
            "Европейская кухня",
            true
        ),
        Category(
            1,
            "Средиземноморская кухня",
            true
        ),
        Category(
            2,
            "Русская кухня",
            true
        ),
        Category(
            3,
            "Восточная кухня",
            true
        ),
        Category(
            4,
            "Американская кухня",
            true
        ),

        Category(
            5,
            "Паназиатская кухня",
            true
        ),

        Category(
            6,
            "Азиатская кухня",
            true
        ),
    )

    fun getCategory() = category
}

fun Int.toCategoryString() =
    when (this) {
        0 -> "Европейская кухня"
        1 -> "Средиземноморская кухня"
        2 -> "Русская кухня"
        3 -> "Восточная кухня"
        4 -> "Американская кухня"
        5 -> "Паназиатская кухня"
        6 -> "Азиатская кухня"
        else -> ""
    }

fun String.toCategoryInt() =
    when (this) {
        "Европейская кухня" -> 0
        "Средиземноморская кухня" -> 1
        "Русская кухня" -> 2
        "Восточная кухня" -> 3
        "Американская кухня" -> 4
        "Паназиатская кухня" -> 5
        "Азиатская кухня" -> 6
        else -> -1
    }
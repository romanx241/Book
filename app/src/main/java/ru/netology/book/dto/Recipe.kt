package ru.netology.book.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Recipe(
    var id: Long,
    val name: String,
    val author: String,
    val content: String,
    val category: Int,
    val isLiked: Boolean,
    val picture: String,
) : Parcelable
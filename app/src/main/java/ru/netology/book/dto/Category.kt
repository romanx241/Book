package ru.netology.book.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Category(
    var id: Int,
    val name: String,
    val isVisible: Boolean,

) : Parcelable
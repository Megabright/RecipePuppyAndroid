package com.mundaco.recipepuppy.data.model

import android.arch.persistence.room.Entity
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Recipe(
    val title: String,
    val href: String,
    val ingredients: String,
    val thumbnail: String
): Parcelable
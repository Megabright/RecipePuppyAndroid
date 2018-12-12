package com.mundaco.recipepuppy.datamodel

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Recipe(
    @field:PrimaryKey
    val title: String,
    val href: String,
    val ingredients: String,
    val thumbnail: String
) : Parcelable
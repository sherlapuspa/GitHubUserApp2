package com.dicoding.githubuserapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "user_fav")
data class UserFav(
    val login: String,
    @PrimaryKey val id: Int,
    @SerializedName("avatar_url") val avatarUrl: String,
    @SerializedName("html_url") val htmlUrl: String
) : Serializable

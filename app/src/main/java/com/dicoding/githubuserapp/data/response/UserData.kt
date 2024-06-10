package com.dicoding.githubuserapp.data.response

import com.google.gson.annotations.SerializedName

data class UserData(
    @field:SerializedName("login") val login: String,
    @field:SerializedName("id") val id: Int,
    @SerializedName("avatar_url") val avatarUrl: String,
    @SerializedName("html_url") val htmlUrl: String

)

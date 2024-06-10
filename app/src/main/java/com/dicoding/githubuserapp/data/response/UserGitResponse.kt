package com.dicoding.githubuserapp.data.response

import com.google.gson.annotations.SerializedName

data class UserGitResponse(
    @field:SerializedName("items") val items: ArrayList<UserData>

)

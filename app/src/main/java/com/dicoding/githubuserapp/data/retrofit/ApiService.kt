package com.dicoding.githubuserapp.data.retrofit

import com.dicoding.githubuserapp.data.response.UserData
import com.dicoding.githubuserapp.data.response.UserDetailResponse
import com.dicoding.githubuserapp.data.response.UserGitResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun findUserData(@Query("q") query: String): Call<UserGitResponse>

    @GET("users/{username}")
    fun getUserDetails(@Path("username") username: String): Call<UserDetailResponse>

    @GET("users/{username}/followers")
    fun getFollowersData(@Path("username") username: String): Call<ArrayList<UserData>>

    @GET("users/{username}/following")
    fun getFollowingData(@Path("username") username: String): Call<ArrayList<UserData>>

}
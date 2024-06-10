package com.dicoding.githubuserapp.vm

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.githubuserapp.data.database.UserDb
import com.dicoding.githubuserapp.data.database.UserFav
import com.dicoding.githubuserapp.data.database.UserFavDao
import com.dicoding.githubuserapp.data.response.UserDetailResponse
import com.dicoding.githubuserapp.data.retrofit.ApiClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetailVM(application: Application) : AndroidViewModel(application) {
    val userDetails = MutableLiveData<UserDetailResponse>()

    private var userDao: UserFavDao?
    private var userDb: UserDb?

    init {
        userDb = UserDb.getDatabase(application)
        userDao = userDb?.userFavDao()
    }

    fun setUserDetails(username: String) {
        ApiClient.apiInstance.getUserDetails(username)
            .enqueue(object : Callback<UserDetailResponse> {
                override fun onResponse(
                    call: Call<UserDetailResponse>, response: Response<UserDetailResponse>
                ) {
                    if (response.isSuccessful) {
                        userDetails.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<UserDetailResponse>, t: Throwable) {
                    Log.e("TAG", "onFailure: ${t.message}")
                }

            })
    }

    fun getUserDetails(): LiveData<UserDetailResponse> {
        return userDetails
    }

    fun addToFavUser(username: String, id: Int, avatarUrl: String, htmlUrl: String) {
        CoroutineScope(Dispatchers.IO).launch {
            var user = UserFav(
                username, id, avatarUrl, htmlUrl
            )
            userDao?.addToFavUser(user)
        }
    }

    suspend fun checkUserExistence(id: Int) = userDao?.checkUserExistence(id)

    fun deleteFromFav(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.deleteFromFav(id)
        }
    }
}
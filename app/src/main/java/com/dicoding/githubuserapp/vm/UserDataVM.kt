package com.dicoding.githubuserapp.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubuserapp.data.response.UserData
import com.dicoding.githubuserapp.data.response.UserGitResponse
import com.dicoding.githubuserapp.data.retrofit.ApiClient
import retrofit2.Call
import retrofit2.Response

class UserDataVM : ViewModel() {
    val dataUserList = MutableLiveData<ArrayList<UserData>>()

    fun setFindUser(queries: String) {
        ApiClient.apiInstance.findUserData(queries)
            .enqueue(object : retrofit2.Callback<UserGitResponse> {
                override fun onResponse(
                    call: Call<UserGitResponse>, response: Response<UserGitResponse>
                ) {
                    if (response.isSuccessful) {
                        dataUserList.postValue(response.body()?.items)
                    }
                }

                override fun onFailure(call: Call<UserGitResponse>, t: Throwable) {
                    Log.e("MainActivity", "onFailure: ${t.message}")
                }

            })
    }

    fun getFindUserData(): LiveData<ArrayList<UserData>> {
        return dataUserList
    }
}
package com.dicoding.githubuserapp.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubuserapp.data.response.UserData
import com.dicoding.githubuserapp.data.retrofit.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingVM : ViewModel() {
    val followingDataList = MutableLiveData<ArrayList<UserData>>()

    fun setFollowingList(username: String) {
        ApiClient.apiInstance.getFollowingData(username)
            .enqueue(object : Callback<ArrayList<UserData>> {
                override fun onResponse(
                    call: Call<ArrayList<UserData>>, response: Response<ArrayList<UserData>>
                ) {
                    if (response.isSuccessful) {
                        followingDataList.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<UserData>>, t: Throwable) {
                    Log.e("TAG", "onFailure: ${t.message}")
                }

            })
    }

    fun getFollowingList(): LiveData<ArrayList<UserData>> {
        return followingDataList
    }
}
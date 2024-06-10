package com.dicoding.githubuserapp.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.dicoding.githubuserapp.data.database.UserFavDao
import com.dicoding.githubuserapp.data.database.UserDb
import com.dicoding.githubuserapp.data.database.UserFav

class UserFavVM(application: Application) : AndroidViewModel(application) {
    private var userDao: UserFavDao?
    private var userDb: UserDb?

    init {
        userDb = UserDb.getDatabase(application)
        userDao = userDb?.userFavDao()
    }

    fun getFavUser(): LiveData<List<UserFav>>? {
        return userDao?.getFavUser()
    }
}
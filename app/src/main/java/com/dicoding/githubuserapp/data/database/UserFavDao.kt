package com.dicoding.githubuserapp.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserFavDao {
    @Insert
    suspend fun addToFavUser(favoriteUser: UserFav)

    @Query("SELECT * FROM user_fav")
    fun getFavUser(): LiveData<List<UserFav>>

    @Query("SELECT count(*) FROM user_fav WHERE user_fav.id = :id")
    suspend fun checkUserExistence(id: Int): Int

    @Query("DELETE FROM user_fav WHERE user_fav.id = :id")
    suspend fun deleteFromFav(id: Int): Int
}
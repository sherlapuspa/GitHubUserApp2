package com.dicoding.githubuserapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [UserFav::class], version = 1
)
abstract class UserDb : RoomDatabase() {
    companion object {
        @Volatile
        var INSTANCE: UserDb? = null

        fun getDatabase(context: Context): UserDb {
            return INSTANCE ?: synchronized(this) {
                val newInstance = Room.databaseBuilder(
                    context.applicationContext, UserDb::class.java, "user_db"
                ).build()
                INSTANCE = newInstance
                newInstance
            }
        }
    }

    abstract fun userFavDao(): UserFavDao
}
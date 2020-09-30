package com.ard.neonetzexamapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ard.neonetzexamapp.data.model.UserEntity
import com.ard.neonetzexamapp.domain.UserDao

@Database(entities = [UserEntity::class],version = 1)
abstract class AppDataBase: RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object{
        private var INSTANCE: AppDataBase? = null
        fun getDatabase(context: Context): AppDataBase{
            INSTANCE = INSTANCE ?: Room.databaseBuilder(context.applicationContext,AppDataBase::class.java,"user_table").build()
            return INSTANCE!!
        }

        fun destroyInstance(){
            INSTANCE = null
        }
    }
}
package com.ard.neonetzexamapp.domain

import androidx.room.*
import com.ard.neonetzexamapp.data.model.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM userentity")
    suspend fun getUser(): UserEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user:UserEntity):Long

    @Delete
    suspend fun deleteUser(user:UserEntity):Int
}
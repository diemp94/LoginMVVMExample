package com.ard.neonetzexamapp.domain

import com.ard.neonetzexamapp.data.model.UserEntity

interface UserDataSource {
    suspend fun getUserInfo(): UserEntity
    suspend fun insertUser(user: UserEntity): Long
    suspend fun deleteUser(user: UserEntity): Int
}
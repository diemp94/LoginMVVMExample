package com.ard.neonetzexamapp.domain

import com.ard.neonetzexamapp.data.model.UserEntity

class UserRepositoryImpl(private val userDataSource: UserDataSource) : UserRepository {
    override suspend fun getUserInfo(): UserEntity {
        return userDataSource.getUserInfo()
    }

    override suspend fun insertUser(user: UserEntity):Long {
        return userDataSource.insertUser(user)
    }

    override suspend fun deleteUser(user: UserEntity):Int {
        return userDataSource.deleteUser(user)
    }

}
package com.ard.neonetzexamapp.data

import com.ard.neonetzexamapp.data.model.UserEntity
import com.ard.neonetzexamapp.domain.UserDao
import com.ard.neonetzexamapp.domain.UserDataSource

class UserDataSourceImpl(private val userDao: UserDao) : UserDataSource {
    override suspend fun getUserInfo(): UserEntity {
        return userDao.getUser()
    }

    override suspend fun insertUser(user: UserEntity): Long {
        return userDao.insertUser(user)
    }

    override suspend fun deleteUser(user: UserEntity): Int {
        return userDao.deleteUser(user)
    }

}
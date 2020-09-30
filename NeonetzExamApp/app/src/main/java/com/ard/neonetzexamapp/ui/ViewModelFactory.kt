package com.ard.neonetzexamapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ard.neonetzexamapp.domain.UserRepository

class ViewModelFactory(private val userRepo:UserRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(UserRepository::class.java).newInstance(userRepo)
    }
}
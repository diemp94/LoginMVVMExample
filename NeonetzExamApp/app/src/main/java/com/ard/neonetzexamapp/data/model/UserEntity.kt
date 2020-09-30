package com.ard.neonetzexamapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "UserEntity")
data class UserEntity (
    @PrimaryKey
    val userId: String="0",
    val name : String,
    val lastname: String,
    val photoUrl : String,
    val phoneNumber: String,
    val password:String
):Serializable
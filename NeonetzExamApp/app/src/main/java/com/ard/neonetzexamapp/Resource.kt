package com.ard.neonetzexamapp

sealed class Resource {
    class Loading : Resource()
    class Sucess<out T>(val data: T) : Resource()
    class Failure : Resource()
}
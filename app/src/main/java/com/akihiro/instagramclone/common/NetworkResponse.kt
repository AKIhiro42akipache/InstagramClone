package com.akihiro.instagramclone.common

sealed class NetworkResponse<out T>{
    object Loading: NetworkResponse<Nothing>()

    data class Success<out T>(val data:T): NetworkResponse<T>()
    data class Error(val error:String): NetworkResponse<Nothing>()
}
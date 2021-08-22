package com.example.servenep.response

import  com.example.servenep.entities.Users

data class LoginResponse(
    val token:String?= null,
    val success: Boolean?= null,
    val data: Users? = null
)
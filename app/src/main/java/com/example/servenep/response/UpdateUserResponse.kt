package com.example.servenep.response

import com.example.servenep.entities.Users

data class UpdateUserResponse (
    val success:Boolean?=null,
    val message:String?=null,
    val data : Users? = null
)
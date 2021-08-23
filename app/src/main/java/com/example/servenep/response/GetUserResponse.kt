package com.example.servenep.response

import com.example.servenep.entities.Users

data class GetUserResponse(
    val success:Boolean?=null,
    val data:Users?=null
)
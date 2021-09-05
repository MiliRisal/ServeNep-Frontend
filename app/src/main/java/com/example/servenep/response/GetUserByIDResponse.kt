package com.example.servenep.response

import com.example.servenep.entities.Users

data class GetUserByIDResponse(
    val success:Boolean?=null,
    val data: Users?=null
)
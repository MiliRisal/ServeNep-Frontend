package com.example.servenep.api

import com.example.servenep.entities.Users
import com.example.servenep.response.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserAPI {
    //for User Registration
    @POST("/user/insert")
    suspend fun userRegister(
        @Body users : Users
    ):Response<RegisterResponse>
}
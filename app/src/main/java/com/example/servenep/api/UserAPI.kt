package com.example.servenep.api

import android.provider.ContactsContract
import com.example.servenep.entities.Users
import com.example.servenep.response.LoginResponse
import com.example.servenep.response.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface UserAPI {
    //for User Registration
    @POST("/user/insert")
    suspend fun userRegister(
        @Body users : Users
    ):Response<RegisterResponse>

    //for User login
    @FormUrlEncoded
    @POST("user/login")
    suspend fun userLogin(
        @Field("email") email: ContactsContract.CommonDataKinds.Email,
        @Field("password") password: String
    ):Response<LoginResponse>
}
package com.example.servenep.api

import android.provider.ContactsContract
import com.example.servenep.entities.Users
import com.example.servenep.response.GetTaskerCategory
import com.example.servenep.response.LoginResponse
import com.example.servenep.response.RegisterResponse
import retrofit2.Response
import retrofit2.http.*

interface UserAPI {
    //for User Registration
    @POST("/user/insert")
    suspend fun userRegister(
        @Body users : Users
    ):Response<RegisterResponse>

    //for User login
    @FormUrlEncoded
    @POST("/user/login")
    suspend fun verifyUser(
        @Field("email") email: String,
        @Field("password") password: String
    ):Response<LoginResponse>

    //for filtering helpers according to category
    @GET("tasker/{category}")
    suspend fun getTaskerByCategory(
        @Header("Authorization") token : String,
        @Path("taskerCategory") taskerCategory: String
    ):Response<GetTaskerCategory>
}
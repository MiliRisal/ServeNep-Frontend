package com.example.servenep.api

import com.example.servenep.entities.Users
import com.example.servenep.response.*
import okhttp3.MultipartBody
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

    //view user
    @GET("user/me")
    suspend fun getUser(
        @Header("Authorization") token: String
    ): Response<GetUserResponse>

    //update user
    @PUT("user/update/{id}")
    suspend fun updateUser(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Body users: Users
    ): Response<UpdateUserResponse>

    //profile upload
    @Multipart
    @PUT("user/profile/{id}")
    suspend fun uploadProfile(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Part file: MultipartBody.Part
    ): Response<ProfilePhotoResponse>
}
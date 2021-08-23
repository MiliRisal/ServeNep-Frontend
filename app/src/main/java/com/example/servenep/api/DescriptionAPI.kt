package com.example.servenep.api

import com.example.servenep.entities.Description
import com.example.servenep.response.DescriptionResponse
import com.example.servenep.response.GetTaskDescription
import retrofit2.Response
import retrofit2.http.*

interface DescriptionAPI {

    //for Inserting Task Description
    @POST("description/insert")
    suspend fun descriptionInsert(
        @Header("Authorization") token : String,
        @Body description: Description
    ):Response<DescriptionResponse>

    //for fetching all Task Description
    @GET("description/all")
    suspend fun allTaskDescription(
       // @Header("Authorization") token : String,
    ):Response<GetTaskDescription>

    //for fetching single Task Description
    @GET("description/{description_id}")
    suspend fun singleTaskDescription(
       // @Header("Authorization") token : String,
        @Path("id") id: String
    ):Response<GetTaskDescription>
}
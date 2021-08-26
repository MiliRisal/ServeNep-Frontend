package com.example.servenep.api

import com.example.servenep.entities.Description
import com.example.servenep.response.*
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
       @Header("Authorization") token : String
    ):Response<GetTaskDescription>

   //for fetching all Task Description
   @DELETE("description/delete/{id}")
   suspend fun deleteDescription(
       @Header("Authorization") token : String,
       @Path("id") id : String
   ):Response<DeleteDescriptionResponse>

   //for fetching all Task Description for Tasker
   @GET("description/{bookedUserId}")
   suspend fun allDescriptionByBookedUserId(
       @Header("Authorization") token : String,
       @Path("bookedUserId") bookedUserId : String
   ):Response<GetAllDescriptionByStatusResponse>

    //for fetching all Task Description for Tasker
    @GET("booking/{addedby}")
    suspend fun allDescriptionByAddedBy(
        @Header("Authorization") token : String,
        @Path("addedby") addedby : String
    ):Response<GetAllDescriptionByStatusResponse>

    //for fetching single Task Description
    @PUT("description/update/{description_id}")
    suspend fun updateDescription(
       @Header("Authorization") token : String,
       @Path("description_id") description_id: String,
       @Body description: Description
    ):Response<UpdateDescriptionResponse>
}
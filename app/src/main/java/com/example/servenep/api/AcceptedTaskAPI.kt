package com.example.servenep.api

import com.example.servenep.entities.AcceptedTask
import com.example.servenep.response.GetAcceptedTaskByTaskerResponse
import com.example.servenep.response.InsertAcceptedTaskResponse
import retrofit2.Response
import retrofit2.http.*

interface AcceptedTaskAPI {
    //for inserting accepted task details
    @POST("accept/insert")
    suspend fun insertAcceptedTask(
        @Header("Authorization") token : String,
        @Body acceptedTask: AcceptedTask
    ):Response<InsertAcceptedTaskResponse>

    // Get Accepted task by Tasker
    @GET("accepttask/tasker/{acceptedby}")
    suspend fun GetTaskAcceptedByTasker (
        @Header("Authorization") token : String,
        @Path("acceptedby") acceptedby : String
    ):Response<GetAcceptedTaskByTaskerResponse>

    // Get Accepted task by user
    @GET("accepttask/user/{userid}")
    suspend fun GetTaskAcceptedByUser (
        @Header("Authorization") token : String,
        @Path("userid") userid : String
    ):Response<GetAcceptedTaskByTaskerResponse>
}
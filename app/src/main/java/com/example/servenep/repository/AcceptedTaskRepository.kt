package com.example.servenep.repository

import com.example.servenep.api.AcceptedTaskAPI
import com.example.servenep.api.ServiceBuilder
import com.example.servenep.api.serveNepAPIRequest
import com.example.servenep.entities.AcceptedTask
import com.example.servenep.response.GetAcceptedTaskByTaskerResponse
import com.example.servenep.response.InsertAcceptedTaskResponse

class AcceptedTaskRepository : serveNepAPIRequest() {
    private  val acceptedTaskAPI = ServiceBuilder.buildService(AcceptedTaskAPI::class.java)

    //for Inserting Accepted Task Detail
    suspend fun insertAcceptedTask(acceptedTask: AcceptedTask): InsertAcceptedTaskResponse{
        return apiRequest {
            acceptedTaskAPI.insertAcceptedTask(
                ServiceBuilder.token!!, acceptedTask
            )
        }
    }

    //for fetching all accepted tasks Description for Tasker
    suspend fun GetTaskAcceptedByTasker(acceptedby : String): GetAcceptedTaskByTaskerResponse {
        return apiRequest {
            acceptedTaskAPI.GetTaskAcceptedByTasker(
                ServiceBuilder.token!!, acceptedby
            )
        }
    }

    //for fetching all accepted tasks Description for Tasker
    suspend fun GetTaskAcceptedByUser(userid : String): GetAcceptedTaskByTaskerResponse {
        return apiRequest {
            acceptedTaskAPI.GetTaskAcceptedByUser(
                ServiceBuilder.token!!, userid
            )
        }
    }
}
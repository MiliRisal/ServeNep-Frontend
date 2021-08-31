package com.example.servenep.repository

import com.example.servenep.api.DescriptionAPI
import com.example.servenep.api.ServiceBuilder
import com.example.servenep.api.serveNepAPIRequest
import com.example.servenep.entities.Description
import com.example.servenep.response.*

class DescriptionRepository: serveNepAPIRequest() {
    private val descriptionAPI = ServiceBuilder.buildService(DescriptionAPI::class.java)

    //for Inserting Task DescriptionRepository
    suspend fun descriptionInsert(description: Description): DescriptionResponse {
        return apiRequest {
            descriptionAPI.descriptionInsert(
                ServiceBuilder.token!!,
                description
            )
        }
    }

    //for fetching all task Description
    suspend fun allTaskDescription():GetTaskDescription{
        return apiRequest {
            descriptionAPI.allTaskDescription(
                ServiceBuilder.token!!
            )
        }
    }

    //for fetching all task Description
    suspend fun deleteDescription(id : String): DeleteDescriptionResponse {
        return apiRequest {
            descriptionAPI.deleteDescription(
                ServiceBuilder.token!!, id
            )
        }
    }
    //for fetching all task Description
    suspend fun allDescriptionByBookedUserId(bookedUserId : String): GetAllDescriptionByStatusResponse {
        return apiRequest {
            descriptionAPI.allDescriptionByBookedUserId(
                ServiceBuilder.token!!, bookedUserId
            )
        }
    }

    //for fetching all task Description for user
    suspend fun allDescriptionByAddedBy(addedby : String): GetAllDescriptionByStatusResponse {
        return apiRequest {
            descriptionAPI.allDescriptionByAddedBy(
                ServiceBuilder.token!!, addedby
            )
        }
    }

    //for fetching single task Description
    suspend fun updateDescription(description_id : String, description: Description): UpdateDescriptionResponse {
        return apiRequest {
            descriptionAPI.updateDescription(
                ServiceBuilder.token!!, description_id, description
            )
        }
    }
}
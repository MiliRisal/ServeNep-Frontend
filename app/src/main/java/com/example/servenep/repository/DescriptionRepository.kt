package com.example.servenep.repository

import com.example.servenep.api.DescriptionAPI
import com.example.servenep.api.ServiceBuilder
import com.example.servenep.api.serveNepAPIRequest
import com.example.servenep.entities.Description
import com.example.servenep.response.DescriptionResponse

class DescriptionRepository: serveNepAPIRequest() {
    private val descriptionAPI = ServiceBuilder.buildService(DescriptionAPI::class.java)

    //for Inserting Task DescriptionRepository
    suspend fun descriptionInsert(description: Description): DescriptionResponse {
        return apiRequest {
            descriptionAPI.descriptionInsert(description)
        }
    }
}
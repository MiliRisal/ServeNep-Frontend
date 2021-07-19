package com.example.servenep.repository

import android.widget.GridLayout
import com.example.servenep.api.DescriptionAPI
import com.example.servenep.api.ServiceBuilder
import com.example.servenep.api.SpecificationAPI
import com.example.servenep.api.serveNepAPIRequest
import com.example.servenep.entities.Description
import com.example.servenep.entities.TaskerSpecification
import com.example.servenep.response.DescriptionResponse
import com.example.servenep.response.SpecificationResponse

class SpecificationRepository: serveNepAPIRequest() {
    private val specificationAPI = ServiceBuilder.buildService(SpecificationAPI::class.java)

    //for Inserting Task DescriptionRepository
    suspend fun specification(spec:TaskerSpecification): SpecificationResponse {
        return apiRequest {
            specificationAPI.addspecifications(spec)
        }
    }
}
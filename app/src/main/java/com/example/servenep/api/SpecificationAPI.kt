package com.example.servenep.api

import com.example.servenep.entities.Description
import com.example.servenep.entities.TaskerSpecification
import com.example.servenep.response.DescriptionResponse
import com.example.servenep.response.SpecificationResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SpecificationAPI {

    //for Inserting Task DescriptionRepository
    @POST("specification/insert")
    suspend fun addspecifications(
        @Body spec: TaskerSpecification
    ): Response<SpecificationResponse>
}
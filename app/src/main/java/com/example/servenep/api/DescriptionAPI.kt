package com.example.servenep.api

import com.example.servenep.entities.Description
import com.example.servenep.response.DescriptionResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface DescriptionAPI {

    //for Inserting Task DescriptionRepository
    @POST("description/insert")
    suspend fun descriptionInsert(
        @Body description: Description
    ): Response<DescriptionResponse>
}
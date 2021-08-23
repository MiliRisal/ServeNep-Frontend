package com.example.servenep.api

import com.example.servenep.response.GetAllCategoryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface CategoryAPI {
// view all category
    @GET("category/all")
    suspend fun getAllCategory(
        @Header ("Authorization") token : String
    ): Response<GetAllCategoryResponse>

    @GET("category/{category_id}")
    suspend fun getSingleCategory(
        @Header ("Authorization") token : String,
        @Path ("category_id") category_id: String
    ): Response<GetAllCategoryResponse>
}
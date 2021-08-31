package com.example.servenep.repository

import com.example.servenep.api.ServiceBuilder
import com.example.servenep.api.serveNepAPIRequest
import com.example.servenep.response.GetAllCategoryResponse

class CategoryRepository : serveNepAPIRequest() {
private val CategoryAPI = (ServiceBuilder.buildService(com.example.servenep.api.CategoryAPI :: class.java))

    //Get All Category
    suspend fun getAllCategory() :GetAllCategoryResponse{
        return apiRequest {
            CategoryAPI.getAllCategory(
                ServiceBuilder.token!!
            )
        }
    }

    //Get single Category
    suspend fun getSingleCategory(category_id : String) :GetAllCategoryResponse{
        return apiRequest {
            CategoryAPI.getSingleCategory(
                ServiceBuilder.token!!, category_id
            )
        }
    }

}
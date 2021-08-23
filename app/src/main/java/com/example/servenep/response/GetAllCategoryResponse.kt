package com.example.servenep.response

import com.example.servenep.entities.Category

data class GetAllCategoryResponse (
    val success : Boolean? = null,
    val message : String? = null,
    val data : MutableList<Category>? = null
)
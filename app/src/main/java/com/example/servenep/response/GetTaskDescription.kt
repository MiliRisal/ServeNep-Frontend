package com.example.servenep.response

import com.example.servenep.entities.Description

data class GetTaskDescription(
    val success: Boolean?= null,
    val data : List<Description>? = null
)
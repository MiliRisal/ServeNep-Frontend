package com.example.servenep.response

import com.example.servenep.entities.Description

data class GetAllDescriptionByStatusResponse (
    val success : Boolean? = null,
    val data : MutableList<Description>? = null
        )
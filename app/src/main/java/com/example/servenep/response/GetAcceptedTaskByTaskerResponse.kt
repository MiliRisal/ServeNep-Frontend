package com.example.servenep.response

import com.example.servenep.entities.AcceptedTask

data class GetAcceptedTaskByTaskerResponse (
    val success : Boolean? = null,
    val data : MutableList<AcceptedTask>? = null
        )
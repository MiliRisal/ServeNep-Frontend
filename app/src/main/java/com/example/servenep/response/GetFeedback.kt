package com.example.servenep.response

import com.example.servenep.entities.Feedback

data class GetFeedback (
    val success: Boolean?= null,
    val count:Int?=null,
    val data : MutableList<Feedback>? = null
)
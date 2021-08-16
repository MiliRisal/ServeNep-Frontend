package com.example.servenep.response

import com.example.servenep.entities.Feedback

data class GetFeedback (
    val success: Boolean?= null,
    val data : List<Feedback>? = null
)
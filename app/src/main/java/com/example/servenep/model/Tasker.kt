package com.example.servenep.model

data class Tasker(
    val TaskerId: Int?=null,
    val TaskerName: String? = null,
    val TaskerCategory: String? = null,
    val TaskerArea: String? = null,
    val TaskerImageURL: String? = null,
    val TaskerPrice: Float? = null
)
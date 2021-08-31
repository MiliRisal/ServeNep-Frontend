package com.example.servenep.entities

data class Description (
    val _id : String? = null,
    val bookedUserId : String? = null,
    val title : String? = null,
    val taskDescription : String? = null,
    val estimatedTime : String? = null,
    val price : String? = null,
    val longitude : Double? = null,
    val latitude : Double? = null,
    val addedby: String? =null
)
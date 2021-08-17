package com.example.servenep.api


import com.example.servenep.entities.Feedback
import com.example.servenep.response.FeedbackResponse
import com.example.servenep.response.GetFeedback
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface FeedbackAPI {
    // for insert Customer feedback

    @POST("feedback/insert")
    suspend fun feedbackInsert(
        @Body feedback: Feedback
    ): Response<FeedbackResponse>

    //for fetching all feedback data
    @GET("feedback/all")
    suspend fun allFeedback(
        // @Header("Authorization") token : String,
    ): Response<GetFeedback>

    //for fetching single Task Description
    @GET("/feedback/{feedback_id}")
    suspend fun singleFeedback(
        // @Header("Authorization") token : String,
        @Path("id") id: String
    ): Response<GetFeedback>
}
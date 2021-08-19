package com.example.servenep.api


import com.example.servenep.entities.Feedback
import com.example.servenep.response.DeleteFeedback
import com.example.servenep.response.FeedbackResponse
import com.example.servenep.response.GetFeedback
import retrofit2.Response
import retrofit2.http.*


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
    @GET("/feedback/{id}")
    suspend fun singleFeedback(
        // @Header("Authorization") token : String,
        @Path("id") id: String
    ): Response<GetFeedback>

    // Delete feedback

    @DELETE("/feedback/delete/{id}")
    suspend fun deleteFeedback(
        // @Header("Authorization") token : String,
        @Path("id") id: String
    ): Response<DeleteFeedback>
}
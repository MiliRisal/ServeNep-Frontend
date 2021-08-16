package com.example.servenep.repository

import com.example.servenep.api.FeedbackAPI
import com.example.servenep.api.ServiceBuilder
import com.example.servenep.api.serveNepAPIRequest
import com.example.servenep.entities.Feedback
import com.example.servenep.response.FeedbackResponse
import com.example.servenep.response.GetFeedback
import com.example.servenep.response.GetTaskDescription

class FeedbackRespository: serveNepAPIRequest() {
    private val feedbackAPI = ServiceBuilder.buildService(FeedbackAPI::class.java)

    //for Inserting Task DescriptionRepository
    suspend fun insertFeedback(feedback: Feedback): FeedbackResponse {
        return apiRequest {
            feedbackAPI.feedbackInsert(feedback)
        }
    }

    //for fetching all task Description
    suspend fun allFeedback(): GetFeedback {
        return apiRequest {
            feedbackAPI.allFeedback()
        }
    }
    //for fetching single task Description
    suspend fun singleFeedback(id : String):GetFeedback{
        return apiRequest {
            feedbackAPI.singleFeedback(id)
        }
    }


}

package com.example.servenep.repository

import com.example.servenep.api.ServiceBuilder
import com.example.servenep.api.UserAPI
import com.example.servenep.api.serveNepAPIRequest
import com.example.servenep.entities.Users
import com.example.servenep.response.*
import okhttp3.MultipartBody

class UserRepository
    :serveNepAPIRequest() {
        private val userAPI = ServiceBuilder.buildService(UserAPI::class.java)

    //For User Register
    suspend fun userRegister(users : Users) : RegisterResponse{
        return apiRequest {
            userAPI.userRegister(users)
        }
    }

    //for user login
    suspend fun userLogin(email: String, password: String): LoginResponse {
        return apiRequest {
            userAPI.verifyUser(email,password)
        }
    }

    //to filter tasker according to their category
    suspend fun getTaskerCategory(category: String): GetTaskerCategory{
        return apiRequest {
            userAPI.getTaskerByCategory(
                ServiceBuilder.token!!, category
            )
        }
    }

    //view user
    suspend fun getUser(): GetUserResponse{
        return apiRequest {
            userAPI.getUser(
                ServiceBuilder.token!!
            )
        }
    }

    //update user profile
    suspend fun updateUser(id: String, users: Users):UpdateUserResponse{
        return apiRequest {
           userAPI.updateUser(
               ServiceBuilder.token!!,id,users
           )
        }
    }

    //upload Profile Image
    suspend fun uploadProfile(id: String, body : MultipartBody.Part): ProfilePhotoResponse {
        return apiRequest {
            userAPI.uploadProfile(
                ServiceBuilder.token!!, id, body
            )
        }
    }

}
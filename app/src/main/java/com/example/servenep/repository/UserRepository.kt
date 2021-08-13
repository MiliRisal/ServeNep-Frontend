package com.example.servenep.repository

import com.example.servenep.api.ServiceBuilder
import com.example.servenep.api.UserAPI
import com.example.servenep.api.serveNepAPIRequest
import com.example.servenep.entities.Users
import com.example.servenep.response.*

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
    suspend fun getTaskerCategory(taskerCategory: String): GetTaskerCategory{
        return apiRequest {
            userAPI.getTaskerByCategory(
                ServiceBuilder.token!!, taskerCategory
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

}
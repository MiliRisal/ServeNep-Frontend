package com.example.servenep

import com.example.servenep.entities.Users
import com.example.servenep.repository.UserRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test


class UserManagementTest {

    private var UserRepository : UserRepository?=null


    // .....................USERS Login and Register Testing..................//

//    @Test
//
//    fun checkLogin() = runBlocking {
//        UserRepository = UserRepository()
//        val response= UserRepository!!.loginUser("kishan", "kishan")
//        val ExpectedResult=true
//        val ActualResult=response.success
//        Assert.assertEquals(ExpectedResult, ActualResult)
//    }




    @Test

    fun checkRegister() = runBlocking {

        val users = Users(fullName = "Kiran Gautam", email = "gautamkiran38@gmail.com",
        phone = "9845969973", address = "bharatpur", password ="Happy@#9845", role = "Customer" )

        UserRepository = UserRepository()
        val response = UserRepository!!.userRegister(users)
        val ExpectedResult=false
        val ActualResult=response.success
        Assert.assertEquals(ExpectedResult,ActualResult)

    }
}
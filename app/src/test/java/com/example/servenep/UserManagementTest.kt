package com.example.servenep

import com.example.servenep.Adapter.TaskerAdapter

import com.example.servenep.entities.Description
import com.example.servenep.entities.Feedback
import com.example.servenep.entities.Users
import com.example.servenep.repository.DescriptionRepository
import com.example.servenep.repository.FeedbackRespository
import com.example.servenep.repository.UserRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test


class UserManagementTest {

    private var UserRepository: UserRepository? = null
    private var DescriptionRepository: DescriptionRepository? = null
    private var FeedbackRespository:FeedbackRespository? =null

    // .....................USERS Login and Register Testing..................//

    @Test

    fun checkLogin() = runBlocking {
        UserRepository = UserRepository()
        val response = UserRepository!!.userLogin("kiran@gmail.com", "123123")
        val ExpectedResult = true
        val ActualResult = response.success
        Assert.assertEquals(ExpectedResult, ActualResult)
    }


    @Test

    fun checkRegister() = runBlocking {

        val users = Users(

            fullName = "KiranGautam", email = "kiran12@gmail.com",
            phone = "1234", address = "ktm", password = "123123", role = "Tasker", bio = "good at cleaning room", price = "200",
            category = "Cleaner"
        )

        UserRepository = UserRepository()
        val response = UserRepository!!.userRegister(users)
        val expectedResult = false
        val actualResult = response.success
        Assert.assertEquals(expectedResult, actualResult)

    }

    // .....................Task Description Test.................//


    @Test

    fun checkdescription() = runBlocking {

        val description = Description(
            _id = "60eee399520ac80348ff81ca",
            title = "Cleaner",
            taskDescription = "house cleaning",
            estimatedTime = "2",
            price = "5000"
        )

        DescriptionRepository = DescriptionRepository()
        val response = DescriptionRepository!!.descriptionInsert(description)
        val ExpectedResult = true
        val ActualResult = response.success
        Assert.assertEquals(ExpectedResult, ActualResult)


    }

    @Test

    fun checkfeedback() = runBlocking {

        val feedback = Feedback(
            _id = "611babe020f9973940900a95",
            feedtitle = "happy",
            feeddescription = "good service from tasker"
        )

       FeedbackRespository = FeedbackRespository()
        val response = FeedbackRespository!!.insertFeedback(feedback)
        val ExpectedResult = true
        val ActualResult = response.success
        Assert.assertEquals(ExpectedResult, ActualResult)


    }


}

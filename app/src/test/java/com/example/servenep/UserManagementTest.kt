package com.example.servenep

import com.example.servenep.Adapter.TaskerAdapter
import com.example.servenep.UI.TaskerBookingActivity
import com.example.servenep.entities.Description
import com.example.servenep.entities.Users
import com.example.servenep.repository.DescriptionRepository
import com.example.servenep.repository.UserRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test


class UserManagementTest {

    private var UserRepository: UserRepository? = null
    private var DescriptionRepository: DescriptionRepository? = null
    private var TaskerBookingActivity:TaskerAdapter?=null


    // .....................USERS Login and Register Testing..................//

    @Test

    fun checkLogin() = runBlocking {
        UserRepository = UserRepository()
        val response = UserRepository!!.userLogin("gautamkiran38@gmail.com", "Happy@#9845")
        val ExpectedResult = true
        val ActualResult = response.success
        Assert.assertEquals(ExpectedResult, ActualResult)
    }


    @Test

    fun checkRegister() = runBlocking {

        val users = Users(
            _id = "60e3debe8c0ad71584500268",
            fullName = "Kiran Gautam", email = "gautamkiran38@gmail.com",
            phone = "9845969973", address = "bharatpur", password = "Happy@#9845", role = "Customer"
        )

        UserRepository = UserRepository()
        val response = UserRepository!!.userRegister(users)
        val ExpectedResult = false
        val ActualResult = response.success
        Assert.assertEquals(ExpectedResult, ActualResult)

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


}
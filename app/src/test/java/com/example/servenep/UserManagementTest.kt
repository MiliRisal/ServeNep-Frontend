package com.example.servenep

import com.example.servenep.entities.Description
import com.example.servenep.entities.TaskerSpecification
import com.example.servenep.entities.Users
import com.example.servenep.repository.DescriptionRepository
import com.example.servenep.repository.SpecificationRepository
import com.example.servenep.repository.UserRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test


class UserManagementTest {

    private var UserRepository: UserRepository? = null
    private var DescriptionRepository: DescriptionRepository? = null
    private var SpecificationRepository: SpecificationRepository?=null


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
            fullName = "jitendra sah", email = "sahjitendra731@gmail.com",
            phone = "9807278869", address = "birjung", password = "jetu", role = "Customer"
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
            title = "Cleaner",
            taskDescription = "house cleanin",
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

    fun checkspecification() = runBlocking {

        val TaskerSpecification = TaskerSpecification(
            name = "Ramesh",
            category = "Electrician",
            price = 5000,
            area = "baneshor height"

        )

        SpecificationRepository= SpecificationRepository()
        val response = SpecificationRepository!!. specification(TaskerSpecification)
        val ExpectedResult = true
        val ActualResult = response.success
        Assert.assertEquals(ExpectedResult, ActualResult)


    }

}
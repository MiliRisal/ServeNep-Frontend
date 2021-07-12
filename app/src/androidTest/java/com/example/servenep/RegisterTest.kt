package com.example.servenep

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import com.example.servenep.UI.RegisterActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@LargeTest
@RunWith(JUnit4::class)
class RegisterTest {

    @get:Rule

    val testRule= ActivityScenarioRule(RegisterActivity::class.java)

    @Test
    fun testRegisterUI(){

        Espresso.onView(withId(R.id.etfullname))
            .perform(ViewActions.typeText("Kiran Gautam")).perform(ViewActions.closeSoftKeyboard());


        Espresso.onView(withId(R.id.etemail))
            .perform(ViewActions.typeText("gautamkiran38@gmail.com")).perform(ViewActions.closeSoftKeyboard());


        Espresso.onView(withId(R.id.etaddress))
            .perform(ViewActions.typeText("bharatpur")).perform(ViewActions.closeSoftKeyboard());


//        Espresso.closeSoftKeyboard()

        Espresso.onView(withId(R.id.etphone))
            .perform(ViewActions.typeText("9845969973")).perform(ViewActions.closeSoftKeyboard());


        Espresso.onView(withId(R.id.etpassword))
            .perform(ViewActions.typeText("Happy@#9845")).perform(ViewActions.closeSoftKeyboard());


        Espresso.onView(withId(R.id.rduser))
            .perform(ViewActions.click())


        Espresso.onView(withId(R.id.btnsignup))
            .perform(ViewActions.click())

        Thread.sleep(3000)


        Espresso.onView(withId(R.id.btnsignup))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    }

}
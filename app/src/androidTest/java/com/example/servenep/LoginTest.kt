package com.example.servenep

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import com.example.servenep.UI.LoginActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@LargeTest
@RunWith(JUnit4::class)
class LoginTest {

    @get:Rule

    val testRule =ActivityScenarioRule(LoginActivity::class.java)

    @Test
    fun testLoginUI() {
        Espresso.onView(ViewMatchers.withId(R.id.etemail))
            .perform(ViewActions.typeText("gautamkiran38@gmail.com"))
            .perform(ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.etpassword))
            .perform(ViewActions.typeText("Happy@#9845"))
            .perform(ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.btnlogin))
            .perform(ViewActions.click())

        Thread.sleep(3000)
        Espresso.onView(ViewMatchers.withId(R.id.btnlogin))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        }
    }


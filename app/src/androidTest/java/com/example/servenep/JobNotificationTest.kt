package com.example.servenep

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import com.example.servenep.UI.JobNotificationActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@LargeTest
@RunWith(JUnit4::class)
class JobNotificationTest {

    @get:Rule

    val testRule = ActivityScenarioRule(JobNotificationActivity::class.java)

    @Test
    fun testBookingNotificationUI() {


        Espresso.onView(ViewMatchers.withId(R.id.btnReject))
            .perform(ViewActions.click())

        Thread.sleep(3000)


        Espresso.onView(ViewMatchers.withId(R.id.btnReject))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

}
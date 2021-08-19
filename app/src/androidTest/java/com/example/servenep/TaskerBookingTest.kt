package com.example.servenep

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import com.example.servenep.UI.JobNotificationActivity
import com.example.servenep.UI.TaskerBookingActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@LargeTest
@RunWith(JUnit4::class)
class TaskerBookingTest {

    @get:Rule

    val testRule = ActivityScenarioRule(TaskerBookingActivity::class.java)

    @Test
    fun testTaskerBookingUI() {

        Espresso.onView(ViewMatchers.withId(R.id.tvBook))
            .perform(ViewActions.click())

        Thread.sleep(3000)

        Espresso.onView(ViewMatchers.withId(R.id.tvBook))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}
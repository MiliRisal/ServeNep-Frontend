package com.example.servenep

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import com.example.servenep.UI.TaskDescriptionActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@LargeTest
@RunWith(JUnit4::class)
class TaskerSpecificationTest {

    @get:Rule

    val testRule = ActivityScenarioRule(TaskDescriptionActivity::class.java)

    @Test
    fun testTaskerDescriptionIU() {
        Espresso.onView(ViewMatchers.withId(R.id.ettitle))
            .perform(ViewActions.typeText("Cleaner"))
            .perform(ViewActions.closeSoftKeyboard());


        Espresso.onView(ViewMatchers.withId(R.id.ettaskdes))
            .perform(ViewActions.typeText("house cleaning")).
            perform(ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.rbminhour))
            .perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.etprice))
            .perform(ViewActions.typeText("5000")).
            perform(ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.btnsubmit))
            .perform(ViewActions.click())

        Thread.sleep(3000)


        Espresso.onView(ViewMatchers.withId(R.id.btnsubmit))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }




}
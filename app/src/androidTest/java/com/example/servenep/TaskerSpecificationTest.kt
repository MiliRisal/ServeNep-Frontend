package com.example.servenep

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import com.example.servenep.UI.TaskDescriptionActivity
import com.example.servenep.UI.TaskerJobDescriptionActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@LargeTest
@RunWith(JUnit4::class)
class TaskerSpecificationTest {

    @get:Rule
    val testRule = ActivityScenarioRule(TaskerJobDescriptionActivity::class.java)

    @Test
    fun testTaskerSpecificationIU() {
        Espresso.onView(ViewMatchers.withId(R.id.etName))
            .perform(ViewActions.typeText("Ramesh"))
            .perform(ViewActions.closeSoftKeyboard());


        Espresso.onView(withId(R.id.spTaskerCategory)).perform(click())
        Espresso.onView(withText("Delivery")).perform(click()).
        perform(ViewActions.closeSoftKeyboard());


        Espresso.onView(ViewMatchers.withId(R.id.etRate))
            .perform(ViewActions.typeText("5000")).
            perform(ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.etArea))
            .perform(ViewActions.typeText("baneshor height")).
            perform(ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.btnTaskerSubmit))
            .perform(ViewActions.click())

        Thread.sleep(3000)


        Espresso.onView(ViewMatchers.withId(R.id.btnTaskerSubmit))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

}
package com.example.servenep

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import com.example.servenep.UI.feedback.FeedbackActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@LargeTest
@RunWith(JUnit4::class)
class FeedbackTest {

        @get:Rule

        val testRule = ActivityScenarioRule(FeedbackActivity::class.java)

        @Test
        fun testFeedbackUI() {
            Espresso.onView(ViewMatchers.withId(R.id.etfeedtitle))
                .perform(ViewActions.typeText("Electrician"))
                .perform(ViewActions.closeSoftKeyboard());


            Espresso.onView(ViewMatchers.withId(R.id.etfeeddescription))
                .perform(ViewActions.typeText("service is satisfy"))
                .perform(ViewActions.closeSoftKeyboard());

            Espresso.onView(ViewMatchers.withId(R.id.btnFeedbackSubmit))
                .perform(ViewActions.click())

            Thread.sleep(3000)


            Espresso.onView(ViewMatchers.withId(R.id.btnFeedbackSubmit))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        }

    }
package com.sky.skygomovie.ui.movie

import android.content.Intent
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import com.sky.skygomovie.R
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.core.Is.`is`
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@LargeTest
@HiltAndroidTest
class MovieActivityTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var mActivityTestRule = IntentsTestRule(MovieActivity::class.java, true, false)


    @Before
    fun setup() {
        mActivityTestRule.launchActivity(Intent())
    }

    @Test
    fun recyclerviewDisplay() {
        onView(withId(R.id.rv_movies)).check(matches(isDisplayed()))
    }

    @Test
    fun recyclerviewDataCount() {
        onView(withId(R.id.rv_movies)).check( RecyclerViewItemCountAssertion(10));
    }

    @Test
    fun scrollToLastPosition() {
        // Attempt to scroll to an item that contains the special text.
        onView(ViewMatchers.withId(R.id.rv_movies))
            .perform(
                // scrollTo will fail the test if no item matches.
                RecyclerViewActions.scrollToLastPosition<RecyclerView.ViewHolder>()
            )
    }

    @Test
    fun searchClick(){
        onView(withId(R.id.actionSearch)).perform(click())
        onView(isAssignableFrom(EditText::class.java)).perform(
            typeText("Drama"),
            pressKey(KeyEvent.KEYCODE_ENTER)
        )
    }

    @After
    fun tearDown() {

    }

    class RecyclerViewItemCountAssertion(private val expectedCount: Int) :
        ViewAssertion {
        override fun check(view: View, noViewFoundException: NoMatchingViewException?) {
            if (noViewFoundException != null) {
                throw noViewFoundException
            }
            val recyclerView = view as RecyclerView
            val adapter = recyclerView.adapter
            assertThat(adapter?.itemCount, `is`(expectedCount))
        }
    }

}
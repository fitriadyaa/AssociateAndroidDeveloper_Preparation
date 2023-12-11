package com.dicoding.todoapp.ui.list

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import com.dicoding.todoapp.R
import org.junit.Rule
import org.junit.Test

//TODO 16 : Write UI test to validate when user tap Add Task (+), the AddTaskActivity displayed
@LargeTest
class TaskActivityTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(TaskActivity::class.java)

    @Test
    fun addNewTask() {
        onView(withId(R.id.fab)).perform(click())
        onView(withText("Add Task")).check(matches(isDisplayed()))
    }
}
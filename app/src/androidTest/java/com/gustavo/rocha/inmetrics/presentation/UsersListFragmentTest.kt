package com.gustavo.rocha.inmetrics.presentation

import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.gustavo.rocha.inmetrics.R
import com.gustavo.rocha.inmetrics.di.BaseUrlModule
import com.gustavo.rocha.inmetrics.di.CoroutinesModule
import com.gustavo.rocha.inmetrics.extension.asJsonString
import com.gustavo.rocha.inmetrics.launchFragmentInHiltContainer
import com.gustavo.rocha.inmetrics.ui.fragment.listUser.UsersListFragment
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@UninstallModules(BaseUrlModule::class, CoroutinesModule::class)
@HiltAndroidTest
class UsersListFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private lateinit var server: MockWebServer

    @Before
    fun setUp() {
        server = MockWebServer().apply {
            start(8080)
        }

        launchFragmentInHiltContainer<UsersListFragment>()
    }

    @Test
    fun shouldShowCharacters_whenViewIsCreated() {
        server.enqueue(MockResponse().setBody("users_page.json".asJsonString()))

        Espresso.onView(
            withId(R.id.usersRecyclerView)
        ).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed())
        )
    }

}
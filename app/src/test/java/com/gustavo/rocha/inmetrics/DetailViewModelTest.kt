package com.gustavo.rocha.inmetrics

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.gustavo.rocha.core.usecase.FetchReposUseCase
import com.gustavo.rocha.core.usecase.base.ResultStatus
import com.gustavo.rocha.inmetrics.ui.fragment.detail.viewmodel.DetailViewModel
import com.gustavo.rocha.inmetrics.ui.fragment.detail.viewmodel.DetailViewModel.*
import com.gustavo.rocha.testing.MainCoroutineRule
import com.gustavo.rocha.testing.model.UserReposFactory
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.isA
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    private lateinit var reposUseCase: FetchReposUseCase

    @Mock
    private lateinit var uiStateObserver: Observer<UiState>

    private val reposList = listOf(
        UserReposFactory().create(UserReposFactory.Repos.AgendamentoWorkManager),
        UserReposFactory().create(UserReposFactory.Repos.Aluvery)
    )

    private lateinit var viewModel: DetailViewModel

    @Before
    fun setup() {
        viewModel = DetailViewModel(reposUseCase)
        viewModel.uiState.observeForever(uiStateObserver)
    }

    @Test
    fun `should notify uiState with Success from UiState when get user repos returns success`() =
        runTest {
            //Arrange
            whenever(reposUseCase(any())).thenReturn(
                flowOf(
                    ResultStatus.Success(reposList)
                )
            )

            //Act
            viewModel.fetchRepos("")

            //Assert
            verify(uiStateObserver).onChanged(isA<UiState.Success>())

            val uiStateSuccess = viewModel.uiState.value as UiState.Success

            assertEquals(2, uiStateSuccess.reposUserList.size)
        }

    @Test
    fun `should notify uiState with Error from UiState when get user repos returns an exception`() =
        runTest {
            //Arrange
            whenever(reposUseCase(any())).thenReturn(
                flowOf(
                    ResultStatus.Error(Throwable())
                )
            )

            //Act
            viewModel.fetchRepos("")

            //Assert
            verify(uiStateObserver).onChanged(isA<UiState.Error>())
        }
}
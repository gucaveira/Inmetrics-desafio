package com.gustavo.rocha.inmetrics.ui.fragment.listUser.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.gustavo.rocha.core.base.CoroutinesDispatchers
import com.gustavo.rocha.core.domain.modal.UserGitHub
import com.gustavo.rocha.core.usecase.GetUsersListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.map

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val getUsersListUseCase: GetUsersListUseCase,
    coroutinesDispatchers: CoroutinesDispatchers,
) : ViewModel() {

    var currentSearchQuery = ""

    private val action = MutableLiveData<Action>()
    val state: LiveData<UiState> = action.switchMap {
        getUsersListUseCase(
            GetUsersListUseCase.GetUsersListUseCaseParams(
                currentSearchQuery,
                getPageConfig()
            )
        ).cachedIn(viewModelScope).map {
            UiState.SearchResult(it)
        }.asLiveData(coroutinesDispatchers.main())
    }

    private fun getPageConfig() = PagingConfig(pageSize = 10)

    fun searchCharacters() {
        action.value = Action.Search
    }

    fun closeSearch() {
        if (currentSearchQuery.isNotEmpty()) {
            currentSearchQuery = ""
        }
    }

    sealed class UiState {
        data class SearchResult(val data: PagingData<UserGitHub>) : UiState()
    }

    sealed class Action {
        object Search : Action()
    }
}
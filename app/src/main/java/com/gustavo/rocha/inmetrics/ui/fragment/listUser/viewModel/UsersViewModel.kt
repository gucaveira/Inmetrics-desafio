package com.gustavo.rocha.inmetrics.ui.fragment.listUser.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.gustavo.rocha.core.domain.modal.UserGitHub
import com.gustavo.rocha.core.usecase.GetUsersListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val getUsersListUseCase: GetUsersListUseCase,
) : ViewModel() {

    fun usersPagingData(query: String): Flow<PagingData<UserGitHub>> {
        return getUsersListUseCase(
            GetUsersListUseCase.GetUsersListUseCaseParams(query, getPageConfig())
        ).cachedIn(viewModelScope)
    }

    private fun getPageConfig() = PagingConfig(pageSize = 10)
}
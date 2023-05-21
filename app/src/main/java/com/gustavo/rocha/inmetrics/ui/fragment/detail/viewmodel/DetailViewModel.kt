package com.gustavo.rocha.inmetrics.ui.fragment.detail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gustavo.rocha.core.domain.modal.ReposUser
import com.gustavo.rocha.core.usecase.FetchReposUseCase
import com.gustavo.rocha.core.usecase.base.ResultStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val fetchReposUseCase: FetchReposUseCase,
) : ViewModel() {

    private val _uiState = MutableLiveData<UiState>()
    val uiState: LiveData<UiState> get() = _uiState

    fun fetchRepos(userNameLogin: String) = viewModelScope.launch {
        fetchReposUseCase(FetchReposUseCase.Params(userNameLogin)).watchStatus()
    }

    private fun Flow<ResultStatus<List<ReposUser>>>.watchStatus() = viewModelScope.launch {
        collect { status ->
            _uiState.value = when (status) {
                ResultStatus.Loading -> UiState.Loading
                is ResultStatus.Success -> UiState.Success(status.data)
                is ResultStatus.Error -> UiState.Error
            }
        }
    }

    sealed class UiState {
        object Loading : UiState()
        data class Success(val reposUserList: List<ReposUser>) : UiState()
        object Error : UiState()

    }
}
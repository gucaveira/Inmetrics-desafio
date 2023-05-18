package com.gustavo.rocha.inmetrics.ui.fragment.listUser.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gustavo.rocha.core.data.repository.UserGithubRepository
import com.gustavo.rocha.core.domain.modal.UserGitHub
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val repository: UserGithubRepository,
) : ViewModel() {

    private val _userGitHubData = MutableLiveData<List<UserGitHub>>()
    val userGitHubData: LiveData<List<UserGitHub>> = _userGitHubData

    fun getUsers() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                _userGitHubData.value = repository.getUsers()
            } catch (e: Exception) {
                Log.e("ComicViewModel", e.toString())
            }
        }
    }
}
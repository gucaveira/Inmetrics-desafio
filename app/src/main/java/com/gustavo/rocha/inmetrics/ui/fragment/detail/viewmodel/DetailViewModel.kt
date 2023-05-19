package com.gustavo.rocha.inmetrics.ui.fragment.detail.viewmodel

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
class DetailViewModel @Inject constructor(
    private val repository: UserGithubRepository,
) : ViewModel() {

    private val _userGitHubData = MutableLiveData<UserGitHub>()
    val userGitHubData: LiveData<UserGitHub> = _userGitHubData

    fun fetchUser(userNameLogin: String) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                _userGitHubData.value = repository.fetchUser(userNameLogin)
            } catch (e: Exception) {
                Log.e("ComicViewModel", e.toString())
            }
        }
    }
}

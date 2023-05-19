package com.gustavo.rocha.core.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.gustavo.rocha.core.data.repository.UserGithubRepository
import com.gustavo.rocha.core.domain.modal.UserGitHub
import com.gustavo.rocha.core.usecase.GetUsersListUseCase.GetUsersListUseCaseParams
import com.gustavo.rocha.core.usecase.base.PagingUseCase
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

interface GetUsersListUseCase {
    operator fun invoke(params: GetUsersListUseCaseParams): Flow<PagingData<UserGitHub>>

    data class GetUsersListUseCaseParams(val query: String, val pagingConfig: PagingConfig)
}

class GetUsersListUseCaseParamsImpl @Inject constructor(private val userGithubRepository: UserGithubRepository) :
    PagingUseCase<GetUsersListUseCaseParams, UserGitHub>(), GetUsersListUseCase {

    override fun createFlowObservable(params: GetUsersListUseCaseParams): Flow<PagingData<UserGitHub>> {
        val pagingSource = userGithubRepository.getUsers(params.query)
        return Pager(config = params.pagingConfig) {
            pagingSource
        }.flow
    }

}
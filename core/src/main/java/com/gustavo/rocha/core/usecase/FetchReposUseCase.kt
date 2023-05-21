package com.gustavo.rocha.core.usecase

import com.gustavo.rocha.core.data.repository.UserGithubRepository
import com.gustavo.rocha.core.domain.modal.ReposUser
import com.gustavo.rocha.core.usecase.base.CoroutinesDispatchers
import com.gustavo.rocha.core.usecase.base.ResultStatus
import com.gustavo.rocha.core.usecase.base.UserCase
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

interface FetchReposUseCase {

    operator fun invoke(params: Params): Flow<ResultStatus<List<ReposUser>>>

    data class Params(val userNameLogin: String)
}

class FetchReposUseCaseImpl @Inject constructor(
    private val repository: UserGithubRepository,
    private val dispatchers: CoroutinesDispatchers,
) : UserCase<FetchReposUseCase.Params, List<ReposUser>>(), FetchReposUseCase {

    override suspend fun doWork(params: FetchReposUseCase.Params): ResultStatus<List<ReposUser>> {
        return withContext(dispatchers.io()) {
            ResultStatus.Success(repository.fetchRepos(params.userNameLogin))
        }
    }
}
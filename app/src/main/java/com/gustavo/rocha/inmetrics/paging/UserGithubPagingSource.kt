package com.gustavo.rocha.inmetrics.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.gustavo.rocha.core.domain.modal.UserGitHub
import com.gustavo.rocha.inmetrics.network.InmetricsApi

class UserGithubPagingSource(
    private val inmetricsApi: InmetricsApi,
    private val query: String,
) : PagingSource<Int, UserGitHub>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserGitHub> {
        return try {
            val since = params.key ?: STARTING_PAGE_INDEX

            val result = if (query.isEmpty()) {
                searchEmpty(since, params)
            } else {
                searchNotEmpty(since, params)
            }

            LoadResult.Page(
                data = result,
                prevKey = if (since == STARTING_PAGE_INDEX) null else since - 1,
                nextKey = if (result.isEmpty()) null else since + 1
            )

        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    private suspend fun searchEmpty(since: Int, params: LoadParams<Int>): List<UserGitHub> {
        val queries = hashMapOf(
            "since" to since.toString(),
            "per_page" to params.loadSize.toString()
        )
        return inmetricsApi.getUsers(queries)
    }

    private suspend fun searchNotEmpty(since: Int, params: LoadParams<Int>) = inmetricsApi
        .getUserPaged(query, since, params.loadSize).items

    override fun getRefreshKey(state: PagingState<Int, UserGitHub>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    companion object {
        private const val STARTING_PAGE_INDEX: Int = 1
    }

}
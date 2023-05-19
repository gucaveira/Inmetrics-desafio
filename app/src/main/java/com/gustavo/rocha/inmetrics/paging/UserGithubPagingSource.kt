package com.gustavo.rocha.inmetrics.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.gustavo.rocha.core.domain.modal.UserGitHub
import com.gustavo.rocha.inmetrics.network.InmetricsApi

class UserGithubPagingSource(private val inmetricsApi: InmetricsApi, private val query: String) :
    PagingSource<Int, UserGitHub>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserGitHub> {
        return try {
            val since = params.key ?: STARTING_PAGE_INDEX

            val queries = hashMapOf(
                "since" to since.toString(),
                "per_page" to params.loadSize.toString()
            )

            val response = inmetricsApi.getUsers(queries)


            LoadResult.Page(
                data = response,
                prevKey = if (since == STARTING_PAGE_INDEX) null else since - 10,
                nextKey = if (response.isEmpty()) null else since + 10
            )

        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, UserGitHub>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(10)
                ?: anchorPage?.nextKey?.minus(10)
        }
    }

    companion object {
        private const val STARTING_PAGE_INDEX: Int = 1
    }

}
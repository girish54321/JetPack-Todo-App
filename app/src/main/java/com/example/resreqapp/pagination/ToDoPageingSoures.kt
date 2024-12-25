package hoods.com.quotesyt.data.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.resreqapp.API.AppApi
import com.example.resreqapp.DataType.RemortData.Todo
import com.example.resreqapp.DataType.RemortData.toPageingTodo
import okio.IOException

class ToDoPageingSoures(
    private val api: AppApi,
):PagingSource<Int,Todo>() {

    companion object {
        private const val STATE_KEY = 1
    }

    override fun getRefreshKey(state: PagingState<Int, Todo>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Todo> {
        val startKey = params.key ?: STATE_KEY
        return try {
            val response = api.getUserToDoApi(startKey, params.loadSize)

            // Ensure response.todo and response.page are safely accessed
            val todoList = response.todo ?: emptyList()
            val currentPage = response.page ?: startKey
            val totalPages = response.totalPages ?: 0

            val nextKey = if (todoList.isEmpty() || currentPage >= totalPages) null else currentPage + 1

            LoadResult.Page(
                data = todoList.map { it.toPageingTodo(startKey) },
                prevKey = if (startKey == STATE_KEY) null else startKey - 1,
                nextKey = nextKey
            )
        } catch (e: IOException) {
            // Handle network IO exceptions
            LoadResult.Error(e)
        } catch (httpError: retrofit2.HttpException) {
            // Handle HTTP errors
            LoadResult.Error(httpError)
        }
    }


}


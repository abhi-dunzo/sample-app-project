package com.example.sampleappproject.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.sampleappproject.api.CharacterService
import com.example.sampleappproject.models.Result
import retrofit2.HttpException
import java.io.IOException

class CharacterPagingSource(private val characterService: CharacterService) :
    PagingSource<Int, Result>() {
    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        return try {
            val page = params.key ?: 1
            val response = characterService.getCharacters(page)
            val result = response.body()!!.results
            println(result)
            LoadResult.Page(
                data = result,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.body()!!.info.pages == page) null else page + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

}
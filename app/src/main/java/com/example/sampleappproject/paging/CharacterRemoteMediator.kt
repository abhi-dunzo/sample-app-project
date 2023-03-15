package com.example.sampleappproject.paging

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.sampleappproject.api.CharacterService
import com.example.sampleappproject.db.CharactersDatabase
import com.example.sampleappproject.models.CharacterRemoteKeys
import com.example.sampleappproject.models.Result

@OptIn(ExperimentalPagingApi::class)
class CharacterRemoteMediator(
    private var characterService: CharacterService,
    private var charactersDatabase: CharactersDatabase,
    private var messagesMutableLiveData: MutableLiveData<String>
) :
    RemoteMediator<Int, Result>() {

    private val characterDao = charactersDatabase.characterDao()
    private val characterRemoteDao = charactersDatabase.characterRemoteDao()

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Result>): MediatorResult {
        return try {
            val currentPage = when (loadType) {

                LoadType.REFRESH -> {
                    messagesMutableLiveData.postValue("refresh called")

                    val remoteKey = getRemoteKeyForTheClosetPosition(state)
                    println(remoteKey)
                    remoteKey?.nextPage?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    messagesMutableLiveData.postValue("prepend called")

                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    // If remoteKeys is null, that means the refresh result is not in the database yet.
                    val prevKey = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                    prevKey
                }
                LoadType.APPEND -> {
                    messagesMutableLiveData.postValue("append called")

                    val remoteKey = getRemoteKeyForLastItem(state)
                    println("append")
                    println(remoteKey?.id)
                    println(remoteKey?.nextPage)
                    println(remoteKey?.prevPage)


//                    Log.d("hi" ,remoteKey.id)
                    val nextPage = remoteKey?.nextPage
                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKey != null)
                    nextPage
                }
            }
            val response = characterService.getCharacters(currentPage)
            val endOfPagination = currentPage == 42
            println(response.body()!!.results)
            println(currentPage)
            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPagination) null else currentPage + 1
            charactersDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    messagesMutableLiveData.postValue("load refresh in db transaction ")

                    println("hi")
                    characterDao.delete()
                    characterRemoteDao.delete()
                }
                val keys = response.body()!!.results.map {
                    CharacterRemoteKeys(
                        id = it.id,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }

                characterRemoteDao.addCharactersKeys(keys)
                characterDao.addCharacters(response.body()!!.results)

            }
//            val keys  = response.body()!!.results.map{
//                CharacterRemoteKeys(
//                    id = it.id ,
//                    prevPage = prevPage,
//                    nextPage = nextPage
//                )
//            }
//            characterRemoteDao.addCharactersKeys(keys)
//            characterDao.addCharacters(response.body()!!.results)
            messagesMutableLiveData.postValue("end of pagination")
            MediatorResult.Success(endOfPaginationReached = endOfPagination)

        } catch (e: Exception) {
            messagesMutableLiveData.postValue("error while trying to fetch next page")

            MediatorResult.Error(e)

        }

    }

    private suspend fun getRemoteKeyForTheClosetPosition(state: PagingState<Int, Result>): CharacterRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                characterRemoteDao.getRemoteKeys(id = id)
            }
        }

    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Result>): CharacterRemoteKeys? {
        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let { it -> characterRemoteDao.getRemoteKeys(id = it.id) }

    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Result>): CharacterRemoteKeys? {
        return state.pages.lastOrNull() {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { it -> characterRemoteDao.getRemoteKeys(id = it.id) }
    }
}
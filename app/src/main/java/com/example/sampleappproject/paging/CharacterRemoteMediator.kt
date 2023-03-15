package com.example.sampleappproject.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.sampleappproject.api.CharacterService
import com.example.sampleappproject.db.CharactersDatabase
import com.example.sampleappproject.models.CharacterRemoteKeys
import com.example.sampleappproject.models.Result
import java.io.InvalidObjectException

@OptIn(ExperimentalPagingApi::class)
class CharacterRemoteMediator(
    private var characterService: CharacterService,
    private var charactersDatabase: CharactersDatabase
    ) :
    RemoteMediator<Int, Result>() {

    private val characterDao = charactersDatabase.characterDao()
    private val characterRemoteDao = charactersDatabase.characterRemoteDao()

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Result>): MediatorResult {
     return  try {
    val currentPage=  when(loadType){
               LoadType.REFRESH -> {
                   val remoteKey = getRemoteKeyForTheClosetPosition(state)
                   println(remoteKey)
                   remoteKey?.nextPage?.minus(1) ?: 1
               }
               LoadType.PREPEND ->{
                   val remoteKey = getRemoteKeyForFirstItem(state)
                   println(remoteKey)

                   val prevPage = remoteKey?.prevPage
                       ?: return MediatorResult.Success(endOfPaginationReached = remoteKey!=null)
                    prevPage
               }
               LoadType.APPEND ->{

                   val remoteKey = getRemoteKeyForLastItem(state)
                   println("append")
                    print(remoteKey)
                   val nextPage = remoteKey?.nextPage
                       ?: return MediatorResult.Success(
                           endOfPaginationReached = remoteKey!=null
                       )
                   nextPage
               }
           }
           val response = characterService.getCharacters(currentPage)
           val endOfPagination = currentPage == 42
         println(response.body()!!.results)
         println(currentPage)
           val prevPage  = if(currentPage == 1) null else currentPage -1
           val nextPage = if(endOfPagination) null else currentPage+1
           charactersDatabase.withTransaction {
               if(loadType == LoadType.REFRESH){
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
           MediatorResult.Success(endOfPaginationReached = endOfPagination)
       }
       catch (e:Exception){
           MediatorResult.Error(e)

       }

    }

    private suspend fun getRemoteKeyForTheClosetPosition(state: PagingState<Int, Result>): CharacterRemoteKeys? {
        return state.anchorPosition?.let { position ->
                state.closestItemToPosition(position)?.id?.let{
                    id->characterRemoteDao.getRemoteKeys(id=id)
            }
        }

    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Result>): CharacterRemoteKeys? {
            return state.pages.firstOrNull{
                it.data.isNotEmpty()
            } ?.data?.firstOrNull()?.let { it -> characterRemoteDao.getRemoteKeys(id=it.id)}

    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Result>): CharacterRemoteKeys? {
        return state.pages.lastOrNull(){
            it.data.isNotEmpty()
        } ?.data?.lastOrNull()?.let { it -> characterRemoteDao.getRemoteKeys(id=it.id)}
    }
}
package com.example.premierleague.data.repository

import com.example.premierleague.data.local.MatchDao
import com.example.premierleague.data.remote.ApiService
import com.example.premierleague.model.Match
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MatchRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val matchDao: MatchDao
) : MatchRepository {

    override suspend fun getMatches(): Flow<List<Match>> = flow {
        val localMatches = matchDao.getAllMatches()
        if (localMatches.isNotEmpty()) {
            emit(localMatches)
        } else {
            val remoteMatches = apiService.getMatches()
            matchDao.insertAll(remoteMatches)
            emit(remoteMatches)
        }
    }

    override suspend fun getMatchesPaginated(limit: Int, offset: Int): List<Match> {
        return matchDao.getMatchesPaginated(limit, offset)
    }

    override suspend fun searchMatches(query: String): List<Match> {
        return matchDao.searchMatches("%${query}%")
    }

    override suspend fun getMatchById(id: Int): Match? {
        return matchDao.getMatchById(id)
    }
}

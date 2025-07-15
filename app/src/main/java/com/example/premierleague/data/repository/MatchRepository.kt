package com.example.premierleague.data.repository
import com.example.premierleague.data.remote.ApiService
import com.example.premierleague.model.Match
import com.example.premierleague.data.local.MatchDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MatchRepository @Inject constructor(
    private val apiService: ApiService,
    private val matchDao: MatchDao
) {
    suspend fun getMatchesPaginated(limit: Int, offset: Int): List<Match> {
        return matchDao.getMatchesPaginated(limit, offset)
    }

    suspend fun getMatches(): Flow<List<Match>> {
        return flow {
            // Сначала пробуем получить из кеша
            val cachedMatches = matchDao.getAllMatches()
            if (cachedMatches.isNotEmpty()) {
                emit(cachedMatches)
            }

            // Затем запрашиваем с сервера
            try {
                val matches = apiService.getMatches()
                matchDao.insertAll(matches)
                emit(matches)
            } catch (e: Exception) {
                if (cachedMatches.isEmpty()) {
                    throw e
                }
            }
        }
    }

    suspend fun searchMatches(query: String): List<Match> {
        return matchDao.searchMatches("%$query%")
    }
    suspend fun getMatchById(id: Int): Match? {
        return matchDao.getMatchById(id)
    }

}





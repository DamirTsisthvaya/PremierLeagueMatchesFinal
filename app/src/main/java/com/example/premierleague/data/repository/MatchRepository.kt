package com.example.premierleague.data.repository

import com.example.premierleague.model.Match
import kotlinx.coroutines.flow.Flow

interface MatchRepository {
    suspend fun getMatches(): Flow<List<Match>>
    suspend fun getMatchesPaginated(limit: Int, offset: Int): List<Match>
    suspend fun searchMatches(query: String): List<Match>
    suspend fun getMatchById(id: Int): Match?
}


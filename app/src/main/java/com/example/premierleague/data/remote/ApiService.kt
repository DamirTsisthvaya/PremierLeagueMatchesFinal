package com.example.premierleague.data.remote
import retrofit2.http.GET
import com.example.premierleague.model.Match
import retrofit2.http.Path

interface ApiService {
    @GET("epl-2023")
    suspend fun getMatches(): List<Match>

    @GET("matches/{id}")
    suspend fun getMatchById(@Path("id") id: Int): Match
}
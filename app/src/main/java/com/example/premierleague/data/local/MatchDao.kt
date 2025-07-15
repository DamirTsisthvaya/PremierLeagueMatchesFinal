package com.example.premierleague.data.local
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.OnConflictStrategy
import com.example.premierleague.model.Match

@Dao
interface MatchDao {
    @Query("SELECT * FROM `match`")
    suspend fun getAllMatches(): List<Match>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(matches: List<Match>)

    @Query("SELECT * FROM `match` WHERE HomeTeam LIKE :query OR AwayTeam LIKE :query")
    suspend fun searchMatches(query: String): List<Match>
    @Query("SELECT * FROM `match` LIMIT :limit OFFSET :offset")
    suspend fun getMatchesPaginated(limit: Int, offset: Int): List<Match>
    @Query("SELECT * FROM 'match' WHERE MatchNumber = :id")
    suspend fun getMatchById(id: Int): Match?

}
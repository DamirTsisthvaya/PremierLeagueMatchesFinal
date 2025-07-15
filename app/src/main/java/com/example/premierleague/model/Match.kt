package com.example.premierleague.model
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
@Entity(tableName = "match")
data class Match(
    @PrimaryKey val MatchNumber: Int,
    val RoundNumber: Int,
    val DateUtc: String,
    val HomeTeam: String,
    val AwayTeam: String,
    val Location: String,
    val HomeTeamScore: Int?,
    val AwayTeamScore: Int?
) {
    fun getFormattedDate(): String {
        return try {
            val instant = Instant.parse(DateUtc)
            val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm")
                .withZone(ZoneId.systemDefault())
            formatter.format(instant)
        } catch (e: Exception) {
            DateUtc
        }
    }

    fun getShortDate(): String {
        return try {
            val instant = Instant.parse(DateUtc)
            val formatter = DateTimeFormatter.ofPattern("dd MMM")
                .withZone(ZoneId.systemDefault())
            formatter.format(instant)
        } catch (e: Exception) {
            DateUtc.substring(0, 10)
        }
    }
}



package com.example.premierleague.ui.screens.matches

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.premierleague.data.repository.MatchRepository
import com.example.premierleague.model.Match
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MatchViewModel @Inject constructor(
    private val repository: MatchRepository,
) : ViewModel() {

    private val _matches = MutableStateFlow<List<Match>>(emptyList())
    val matches: StateFlow<List<Match>> = _matches

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _isPaginating = MutableStateFlow(false)
    val isPaginating: StateFlow<Boolean> = _isPaginating

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private var allLoadedMatches: List<Match> = emptyList()
    private var currentPage = 0
    private val pageSize = 10

    init {
        loadMatches()
    }

    fun loadMatches() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                repository.getMatches().collect { all ->
                    allLoadedMatches = all
                    currentPage = 1
                    _matches.value = getPaginatedMatches()
                }
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadNextPage() {
        if (_isPaginating.value || _searchQuery.value.isNotBlank()) return

        viewModelScope.launch {
            _isPaginating.value = true
            currentPage++
            _matches.value = getPaginatedMatches()
            _isPaginating.value = false
        }
    }

    private fun getPaginatedMatches(): List<Match> {
        val end = currentPage * pageSize
        return allLoadedMatches.take(end.coerceAtMost(allLoadedMatches.size))
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query

        viewModelScope.launch {
            if (query.isBlank()) {
                _matches.value = getPaginatedMatches()
            } else {
                val filtered = allLoadedMatches.filter {
                    it.HomeTeam.contains(query, ignoreCase = true) ||
                            it.AwayTeam.contains(query, ignoreCase = true)
                }
                _matches.value = filtered
            }
        }
    }

    fun clearAndReload() {
        _searchQuery.value = ""
        loadMatches()
    }
}


package com.example.premierleague.ui.screens.matches

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.premierleague.data.repository.MatchRepository
import com.example.premierleague.model.Match
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
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

    private var currentPage = 0
    private val pageSize = 10
    private var isLastPage = false

    init {
        loadFirstPage()
    }

    private fun loadFirstPage() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                currentPage = 0
                isLastPage = false
                val firstPage = repository.getMatchesPaginated(pageSize, 0)
                _matches.value = firstPage
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadNextPage() {
        if (_isPaginating.value || isLastPage || _searchQuery.value.isNotBlank()) return

        viewModelScope.launch {
            _isPaginating.value = true
            try {
                currentPage++
                val nextPage = repository.getMatchesPaginated(pageSize, currentPage * pageSize)
                if (nextPage.isEmpty()) {
                    isLastPage = true
                } else {
                    _matches.value = _matches.value + nextPage
                }
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isPaginating.value = false
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
        viewModelScope.launch {
            if (query.isBlank()) {
                currentPage = 0
                isLastPage = false
                val firstPage = repository.getMatchesPaginated(pageSize, 0)
                _matches.value = firstPage
            } else {
                val filtered = repository.searchMatches(query)
                _matches.value = filtered
            }
        }
    }

    fun clearAndReload() {
        _searchQuery.value = ""
        loadFirstPage()
    }
}

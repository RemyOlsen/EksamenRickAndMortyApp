package com.example.eksamenrickandmortyapp.screens.episode_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eksamenrickandmortyapp.data.EpisodeRepository
import com.example.eksamenrickandmortyapp.data.entities.Episode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EpisodeListViewModel : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private var _currentPage = MutableStateFlow(0)
    val currentPage = _currentPage.asStateFlow()

    private var _amountOfEpisodes = MutableStateFlow(0)
    val amountOfEpisodes = _amountOfEpisodes.asStateFlow()

    private var _totalPages = MutableStateFlow<Int?>(null)
    val totalPages = _totalPages.asStateFlow()

    private var _totalEpisodes = MutableStateFlow<Int?>(null)
    val totalEpisodes = _totalEpisodes.asStateFlow()

    private val _loadingAttempts = MutableStateFlow(0)
    val loadingAttempts = _loadingAttempts.asStateFlow()

    private val _allEpisodes = MutableStateFlow<List<Episode>>(emptyList())
    private val _episodes = MutableStateFlow<List<Episode>>(emptyList())
    val episodes = _episodes.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()


    init {
        loadAllFunctions()
    }

    fun loadAllFunctions() {
        getTotalEpisodePagesCount()
        getTotalEpisodes()
        loadAllPages()
    }

    // To add the episode page count to mutableStateFlow
    private fun getTotalEpisodePagesCount() {
        viewModelScope.launch(Dispatchers.IO) {
            val totalPages = EpisodeRepository.getTotalPagesCount()
            Log.d("EpisodeListViewModel", "Total pages found in API: $totalPages")
            _totalPages.value = totalPages
        }
    }

    // To add the episode count to mutableStateFlow
    private fun getTotalEpisodes() {
        viewModelScope.launch(Dispatchers.IO) {
            val totalEpisodes = EpisodeRepository.getTotalEpisodeCount()
            Log.d("EpisodeListViewModel", "Total episodes found in API: $totalEpisodes")
            _totalEpisodes.value = totalEpisodes
        }
    }

    private fun loadAllPages() {
        Log.d("EpisodeListViewModel", "Loading all pages")
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true

            val cachedEpisodes = EpisodeRepository.getCachedEpisodes().size
            val totalApiEpisodes = EpisodeRepository.getTotalEpisodeCount()

            Log.d("EpisodeListViewModel", "Cached episodes: $cachedEpisodes")
            Log.d("EpisodeListViewModel", "Total episodes found the API: $totalApiEpisodes")

            // This will check if the total number of episodes in the api is greater than the cached episodes
            // Should theoretically reload all pages if the api changes
            if ( totalApiEpisodes != null && totalApiEpisodes > cachedEpisodes ) {
                val totalPages = EpisodeRepository.getTotalPagesCount()

                Log.d("EpisodeListViewModel", "Total pages: $totalPages")

                if (totalPages != null) {
                    for (page in 1..totalPages) {
                        _currentPage.value = page
                        Log.d("EpisodeListViewModel", "Loading page: $page")

                        getAndVerifyAndStoreEpisodes(page)
                    }
                }

            } else {
                _allEpisodes.value = EpisodeRepository.getCachedEpisodes()
                _episodes.value = _allEpisodes.value
                Log.d("EpisodeListViewModel", "Loaded cached episodes from cache ${_episodes.value.size}")
            }
            _allEpisodes.value = EpisodeRepository.getCachedEpisodes()
            _episodes.value = _allEpisodes.value
            _loading.value = false
            Log.d("EpisodeListViewModel", "Loaded ${_episodes.value.size} episodes")
            Log.d("EpisodeListViewModel", "Cached episodes: $cachedEpisodes")

        }
    }

    private suspend fun getAndVerifyAndStoreEpisodes(page: Int) {
        Log.d("EpisodeListViewModel", "Getting episodes from page: $page")
        var success = false
        var attempts = 0

        while (!success) {
            try {
                val newEpisodes = EpisodeRepository.getEpisodesFromApi(page)
                _loadingAttempts.value = attempts + 1
                Log.d("EpisodeListViewModel", "Loaded ${newEpisodes.size} episodes from page $page")
                Log.d("EpisodeListViewModel", "Attempts: $attempts")
                if (newEpisodes.isNotEmpty()) {
                    _episodes.value += newEpisodes
                    _amountOfEpisodes.value += newEpisodes.size
                    success = true
                } else {
                    attempts++
                    Log.e("EpisodeListViewModel", "No episodes loaded from page $page: attempts: $attempts")
                }

            } catch (e: Exception) {
                attempts++
                Log.e("EpisodeListViewModel", "Error loading page $page: attempts: $attempts \n ${e.message}")
                if (attempts == 10) {
                    Log.e("EpisodeListViewModel", "Failed to load page $page after 3 attempts")

                }
            }
        }
    }

    fun clearSearchQuery() {
        _searchQuery.value = ""
        searchEpisodes("")
    }

    fun searchEpisodes(input: String) {
        _searchQuery.value = input
        Log.d("EpisodeListViewModel", "Searching for: $input")

        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            val searchResults = if (input.isEmpty()) {
                _allEpisodes.value
            } else {
                _allEpisodes.value.filter { episode ->
                    episode.name.contains(input, ignoreCase = true) ||
                            episode.airDate.contains(input, ignoreCase = true) ||
                            episode.id.toString().contains(input, ignoreCase = true) ||
                            episode.episode.contains(input, ignoreCase = true) ||
                            episode.created.contains(input, ignoreCase = true)
                }
            }
            _episodes.value = searchResults
            _loading.value = false
        }
    }

}
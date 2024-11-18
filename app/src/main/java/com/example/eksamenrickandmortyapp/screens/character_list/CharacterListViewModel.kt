package com.example.eksamenrickandmortyapp.screens.character_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eksamenrickandmortyapp.data.entities.Character
import com.example.eksamenrickandmortyapp.data.CharacterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class CharacterListViewModel : ViewModel() {

    // If the character list is in list or card view
    private val _isListView = MutableStateFlow(false)
    val isListView = _isListView.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private var _currentPage = MutableStateFlow(0)
    val currentPage = _currentPage.asStateFlow()

    private var _amountOfCharacters = MutableStateFlow(0)
    val amountOfCharacters = _amountOfCharacters.asStateFlow()

    private var _totalPages = MutableStateFlow<Int?>(null)
    val totalPages = _totalPages.asStateFlow()

    private var _totalCharacters = MutableStateFlow<Int?>(null)
    val totalCharacters = _totalCharacters.asStateFlow()

    private val _loadingAttempts = MutableStateFlow(0)
    val loadingAttempts = _loadingAttempts.asStateFlow()

    private val _allCharacters = MutableStateFlow<List<Character>>(emptyList())
    private val _characters = MutableStateFlow<List<Character>>(emptyList())
    val characters = _characters.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()


    // Toggle the view between list and grid
    fun toggleView() {
        _isListView.value = !_isListView.value
    }

    init {
        loadAllFunctions()
    }

    fun loadAllFunctions() {
        getTotalPagesAndCharacters()
        getTotalCharacters()
        loadAllPages()
    }

    private fun getTotalPagesAndCharacters() {
        viewModelScope.launch(Dispatchers.IO) {
            val totalPages = CharacterRepository.getTotalPageCount()
            Log.d("CharacterListViewModel", "Total pages found in API: $totalPages")
            _totalPages.value = totalPages
        }
    }

    private fun getTotalCharacters() {
        viewModelScope.launch(Dispatchers.IO) {
            val totalCharacters = CharacterRepository.getTotalCharacterCount()
            Log.d("CharacterListViewModel", "Total characters found in API: $totalCharacters")
            _totalCharacters.value = totalCharacters
        }
    }

    private fun loadAllPages() {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true

            val cachedCharacters = CharacterRepository.getCachedCharacters().size
            val totalApiCharacters = CharacterRepository.getTotalCharacterCount()

            Log.d("CharacterListViewModel", "Cached characters: $cachedCharacters")
            Log.d("CharacterListViewModel", "Total characters found the API: $totalApiCharacters")

            // This will check if the total number of characters in the api is greater than the cached characters
            // Should theoretically reload all pages if the api changes
            if ( totalApiCharacters != null && totalApiCharacters > cachedCharacters ) {
                val totalPages = CharacterRepository.getTotalPageCount()

                Log.d("CharacterListViewModel", "Total pages: $totalPages")

                if (totalPages != null) {
                    for (page in 1..totalPages) {
                        _currentPage.value = page
                        Log.d("CharacterListViewModel", "Loading page: $page")

                        getAndVerifyAndStoreCharacters(page)
                    }
                }

            } else {
                _allCharacters.value = CharacterRepository.getCachedCharacters()
                _characters.value = _allCharacters.value
                Log.d("CharacterListViewModel", "Loaded cached characters from cache ${_characters.value.size}")
            }
            _allCharacters.value = CharacterRepository.getCachedCharacters()
            _characters.value = _allCharacters.value
            _loading.value = false
            Log.d("CharacterListViewModel", "Loaded ${_characters.value.size} characters")
            Log.d("CharacterListViewModel", "Cached characters: $cachedCharacters")

        }
    }
    /*
        * Had a weird issue where it would load pages but then not store them, instead skip them
        * This was when emulating "poor" EDGE connection to load the pages slowly
        * I assume it was because it took too long to respond or something else
        * So I added a fail check to detect if it didn't get added to the list and repeatedly try until it got them
     */
    private suspend fun getAndVerifyAndStoreCharacters(page: Int) {
        var success = false
        var attempts = 0

        while (!success) {
            try {
                val newCharacters = CharacterRepository.getCharacters(page)
                _loadingAttempts.value = attempts + 1
                Log.d("CharacterListViewModel", "Loaded ${newCharacters.size} characters from page $page")
                Log.d("CharacterListViewModel", "Attempts: $attempts")
                if (newCharacters.isNotEmpty()) {
                    _characters.value += newCharacters
                    _amountOfCharacters.value += newCharacters.size
                    success = true
                } else {
                    attempts++
                    Log.e("CharacterListViewModel", "No characters loaded from page $page: attempts: $attempts")
                }

            } catch (e: Exception) {
                attempts++
                Log.e("CharacterListViewModel", "Error loading page $page: attempts: $attempts \n ${e.message}")
                if (attempts == 10) {
                    Log.e("CharacterListViewModel", "Failed to load page $page after 3 attempts")

                }
            }
        }

    }

    fun clearSearchQuery() {
        _searchQuery.value = ""
        searchCharacters("")
    }

    fun searchCharacters(input: String) {
        _searchQuery.value = input
        Log.d("CharacterListViewModel", "Searching for: $input")

        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            val searchResults = if (input.isEmpty()) {
                _allCharacters.value
            } else {
                _allCharacters.value.filter { character ->
                    character.name.contains(input, ignoreCase = true) ||
                    character.status.contains(input, ignoreCase = true) ||
                    character.id.toString().contains(input, ignoreCase = true) ||
                    character.species.contains(input, ignoreCase = true) ||
                    character.gender.contains(input, ignoreCase = true) ||
                    character.type.contains(input, ignoreCase = true) ||
                    character.origin.name.contains(input, ignoreCase = true) ||
                    character.location.name.contains(input, ignoreCase = true)
                }
            }
            _characters.value = searchResults
            _loading.value = false
        }
    }

//    fun searchCharactersByName(name: String) {
//        viewModelScope.launch(Dispatchers.IO) {
//            _loading.value = true
//
//            if (name.isBlank()) { // Not in Search mode
//                _characters.value = CharacterRepository.getCachedCharacters()
//
//            } else { // In Search mode
//                Log.d("CharacterListViewModel", "Searching for: $name")
//                val searchResults = CharacterRepository.getCharactersByName(name)
//                _characters.value = searchResults
//            }
//
//            _loading.value = false
//        }
//    }



    //Load the first page of characters
//    init {
//        viewModelScope.launch {
//            _loading.value = true
//
//            val cachedCharacters = CharacterRepository.getCachedCharacters()
//            _characters.value = cachedCharacters
//
//            val lastFetchedPage = CharacterRepository.getLastFetchedPage() ?: 0
//            currentPage = lastFetchedPage +1
//
//            _loading.value = false
//        }
//    }

//    init {
//        viewModelScope.launch(Dispatchers.IO) {
//            loadCharacters()
//        }
//    }
//
//
//    private suspend fun loadCharacters() {
//            _loading.value = true
//            if (CharacterRepository.getCachedCharacters().isEmpty()) {
//                fromApi()
//            } else {
//                fromDatabase()
//            }
//            _loading.value = false
//    }
//
//    private suspend fun fromApi() {
//        Log.d("CharacterListViewModel", "Loading all characters")
//        val allCharacters = CharacterRepository.getAllCharacters()
//        Log.d("CharacterListViewModel", "Loaded ${allCharacters.size} characters")
//        _characters.value = allCharacters
//    }
//
//    private suspend fun fromDatabase() {
//        Log.d("CharacterListViewModel", "Loading cached characters")
//        _characters.value = CharacterRepository.getCachedCharacters()
//    }



}
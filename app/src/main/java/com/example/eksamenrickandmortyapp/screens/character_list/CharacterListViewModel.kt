package com.example.eksamenrickandmortyapp.screens.character_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eksamenrickandmortyapp.data.Character
import com.example.eksamenrickandmortyapp.data.CharacterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class CharacterListViewModel : ViewModel() {

    // If the character list is in list or card view
    private val _isListView = MutableStateFlow(true)
    val isListView = _isListView.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private var currentPage = 1

    private val _characters = MutableStateFlow<List<Character>>(emptyList())
    val characters = _characters.asStateFlow()

    // Toggle the view between list and grid
    fun toggleView() {
        _isListView.value = !_isListView.value
    }

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

    init {
        viewModelScope.launch {
            loadCharacters()
        }
    }


    private suspend fun loadCharacters() {
            _loading.value = true
            if (CharacterRepository.getCachedCharacters().isEmpty()) {
                fromApi()
            } else {
                fromDatabase()
            }
            _loading.value = false
    }

    private suspend fun fromApi() {
        Log.d("CharacterListViewModel", "Loading all characters")
        val allCharacters = CharacterRepository.getAllCharacters()
        Log.d("CharacterListViewModel", "Loaded ${allCharacters.size} characters")
        _characters.value = allCharacters
    }

    private suspend fun fromDatabase() {
        Log.d("CharacterListViewModel", "Loading cached characters")
        _characters.value = CharacterRepository.getCachedCharacters()
    }



//
//    fun loadAllPages() {
//        viewModelScope.launch {
//            Log.d("CharacterListViewModel", "Loading next page: $currentPage")
//
//            if(_loading.value) return@launch
//
//            _loading.value = true
//            val newCharacters = CharacterRepository.getCharacters(currentPage)
//
//            if (newCharacters.isNotEmpty()) {
//                _characters.value += newCharacters
//                currentPage++
//            }
//
//            _loading.value = false
//        }
//    }


    fun searchCharactersByName(name: String) {
        viewModelScope.launch {
            _loading.value = true

            if (name.isBlank()) { // Not in Search mode
                _isSearching.value = false
                _characters.value = CharacterRepository.getCachedCharacters()

            } else { // In Search mode
                Log.d("CharacterListViewModel", "Searching for: $name")
                _isSearching.value = true
                val searchResults = CharacterRepository.getCharactersByName(name)
                _characters.value = searchResults
            }

            _loading.value = false
        }
    }


}
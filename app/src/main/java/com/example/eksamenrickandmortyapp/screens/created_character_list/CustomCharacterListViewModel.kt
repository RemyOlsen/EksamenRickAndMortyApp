package com.example.eksamenrickandmortyapp.screens.created_character_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eksamenrickandmortyapp.data.entities.CustomCharacter
import com.example.eksamenrickandmortyapp.data.CustomCharacterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CustomCharacterListViewModel : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _allCustomCharacters = MutableStateFlow<List<CustomCharacter>>(emptyList())
    private val _customCharacters = MutableStateFlow<List<CustomCharacter>>(emptyList())
    val customCharacters = _customCharacters.asStateFlow()

    init {
        loadCustomCharacters()
    }

    fun loadCustomCharacters() {
        viewModelScope.launch {
            _loading.value = true

            try {
                val getCustomCharacters = CustomCharacterRepository.getCustomCharacters()
                _allCustomCharacters.value = getCustomCharacters
            } catch (e: Exception) {

                _allCustomCharacters.value = emptyList()
            } finally {
                _loading.value = false
                _customCharacters.value = _allCustomCharacters.value
            }

            _loading.value = false
        }
    }


    fun searchCharacters(input: String) {
        _searchQuery.value = input
        Log.d("CharacterListViewModel", "Searching for: $input")

        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            val searchResults = if (input.isEmpty()) {
                _allCustomCharacters.value
            } else {
                _allCustomCharacters.value.filter { character ->
                    character.name.contains(input, ignoreCase = true) ||
                    character.status.contains(input, ignoreCase = true) ||
                    character.id.toString().contains(input, ignoreCase = true) ||
                    character.species.contains(input, ignoreCase = true) ||
                    character.gender.contains(input, ignoreCase = true) ||
                    character.origin.contains(input, ignoreCase = true)
                }
            }
            _customCharacters.value = searchResults
            _loading.value = false
        }
    }

}
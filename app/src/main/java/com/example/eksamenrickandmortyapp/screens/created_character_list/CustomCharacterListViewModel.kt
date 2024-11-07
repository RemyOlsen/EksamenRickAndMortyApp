package com.example.eksamenrickandmortyapp.screens.created_character_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eksamenrickandmortyapp.data.CustomCharacter
import com.example.eksamenrickandmortyapp.data.CustomCharacterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CustomCharacterListViewModel : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _customCharacters = MutableStateFlow<List<CustomCharacter>>(emptyList())
    val customCharacters = _customCharacters.asStateFlow()

    init {
        loadCustomCharacters()
    }

    private fun loadCustomCharacters() {
        viewModelScope.launch {
            _loading.value = true

            try {
                val getCustomCharacters = CustomCharacterRepository.getCustomCharacters()
                _customCharacters.value = getCustomCharacters
            } catch (e: Exception) {
                // Handle exception, possibly set an error state
                _customCharacters.value = emptyList()
            } finally {
                _loading.value = false
            }

            _loading.value = false
        }
    }
}
package com.example.eksamenrickandmortyapp.screens.character_details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eksamenrickandmortyapp.data.entities.Character
import com.example.eksamenrickandmortyapp.data.CharacterRepository
import com.example.eksamenrickandmortyapp.data.EpisodeRepository
import com.example.eksamenrickandmortyapp.data.entities.Episode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CharacterDetailsViewModel : ViewModel() {

    private val _selectedCharacter = MutableStateFlow<Character?>(null)
    val selectedCharacter = _selectedCharacter.asStateFlow()

    private val _cachedEpisodes = MutableStateFlow<List<Episode>>(emptyList())
    val cachedEpisodes = _cachedEpisodes.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    fun loadCharacterDetails(characterId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            try {
                // Fetch the character from the database
                val character = CharacterRepository.getCharacterById(characterId)
                _selectedCharacter.value = character

                if (character != null) {
                    // Extract episode IDs and fetch episodes
                    val episodeIds = extractEpisodeIds(character)
                    val episodes = EpisodeRepository.getEpisodesByIds(episodeIds)

                    // Update the cache with the fetched episodes
                    _cachedEpisodes.value = episodes
                } else {
                    Log.e("CharacterDetailsViewModel", "Character with ID $characterId not found")
                }
            } catch (e: Exception) {
                Log.e("CharacterDetailsViewModel", "Error loading character details", e)
            } finally {
                Log.e(
                    "CharacterDetailsViewModel",
                    "Finished loading character details, ${_cachedEpisodes.value.size} episodes loaded"
                )
                _loading.value = false
            }
        }
    }

    private fun extractEpisodeIds(character: Character): List<Int> {
        return character.episode.mapNotNull { episodeUrl ->
            episodeUrl.substringAfterLast("/").toIntOrNull()
        }
    }
}
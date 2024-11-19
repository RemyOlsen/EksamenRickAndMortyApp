package com.example.eksamenrickandmortyapp.screens.episode_details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eksamenrickandmortyapp.data.CharacterRepository
import com.example.eksamenrickandmortyapp.data.EpisodeRepository
import com.example.eksamenrickandmortyapp.data.entities.Character
import com.example.eksamenrickandmortyapp.data.entities.Episode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EpisodeDetailsViewModel : ViewModel() {

    private val _selectedEpisode = MutableStateFlow<Episode?>(null)
    val selectedEpisode = _selectedEpisode.asStateFlow()

    private val _cachedCharacters = MutableStateFlow<List<Character>>(emptyList())
    val cachedCharacters = _cachedCharacters.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()


    fun loadEpisodeDetails(episodeId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            try {
                // Fetch the episode from the database
                val episode = EpisodeRepository.getEpisodeById(episodeId)
                _selectedEpisode.value = episode

                if (episode != null) {
                    // Extract character IDs and fetch characters
                    val characterIds = extractCharacterIds(episode)
                    val characters = CharacterRepository.getCharactersByIds(characterIds)

                    // Update the cache with the fetched characters
                    _cachedCharacters.value = characters
                } else {
                    Log.e("EpisodeDetailsViewModel", "Episode with ID $episodeId not found")
                }
            } catch (e: Exception) {
                Log.e("EpisodeDetailsViewModel", "Error loading episode details", e)
            } finally {
                Log.e(
                    "EpisodeDetailsViewModel",
                    "Finished loading episode details, ${_cachedCharacters.value.size} characters loaded"
                )
                _loading.value = false
            }
        }
    }

    private fun extractCharacterIds(episode: Episode): List<Int> {
        return episode.characters.mapNotNull { characterUrl ->
            characterUrl.substringAfterLast("/").toIntOrNull()
        }
    }

}

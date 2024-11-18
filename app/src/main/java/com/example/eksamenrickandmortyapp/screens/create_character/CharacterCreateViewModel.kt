package com.example.eksamenrickandmortyapp.screens.create_character

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eksamenrickandmortyapp.data.CharacterRepository
import com.example.eksamenrickandmortyapp.data.entities.CustomCharacter
import com.example.eksamenrickandmortyapp.data.CustomCharacterRepository
import com.example.eksamenrickandmortyapp.data.entities.Character
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CharacterCreateViewModel : ViewModel() {

    // StateFlows for inputs
    private val _characterName = MutableStateFlow("")
    val characterName = _characterName.asStateFlow()

    private val _status = MutableStateFlow("")
    val status = _status.asStateFlow()

    private val _species = MutableStateFlow("")
    val species = _species.asStateFlow()

    private val _type = MutableStateFlow("")
    val type = _type.asStateFlow()

    private val _gender = MutableStateFlow("")
    val gender = _gender.asStateFlow()

    private val _origin = MutableStateFlow("")
    val origin = _origin.asStateFlow()

    private val _description = MutableStateFlow<String?>(null)
    val description = _description.asStateFlow()

    private val _imageUrl = MutableStateFlow("https://rickandmortyapi.com/api/character/avatar/19.jpeg")
    val imageUrl = _imageUrl.asStateFlow()

    private val _cachedCharacters = MutableStateFlow<List<Character>>(emptyList())
    val cachedCharacters = _cachedCharacters.asStateFlow()

    // StateFlow for saving
    private val _saveSuccess = MutableStateFlow<Boolean?>(null)
    val saveSuccess = _saveSuccess.asStateFlow()

    // Functions to update inputs
    fun characterNameInput(nameInput: String) {
        _characterName.value = nameInput
    }

    fun statusInput(statusInput: String) {
        _status.value = statusInput
    }

    fun speciesInput(speciesInput: String) {
        _species.value = speciesInput
    }

    fun genderInput(genderInput: String) {
        _gender.value = genderInput
    }

    fun originInput(originInput: String) {
        _origin.value = originInput
    }

    fun descriptionInput(descriptionInput: String?) {
        _description.value = descriptionInput
    }

    fun imageInput(imageInput: String) {
        _imageUrl.value = imageInput
    }

    init {
        getCharacterImages()
    }

    private fun clearAllInputs() {
        _characterName.value = ""
        _status.value = ""
        _species.value = ""
        _gender.value = ""
        _origin.value = ""
        _imageUrl.value = "https://rickandmortyapi.com/api/character/avatar/19.jpeg"
        _description.value = null
    }

    private fun checkIfInputIsBlankSetUnknown(input: String, setTo: String): String {
        return if (input.isBlank()) {
            setTo
        } else (
            input
        )
    }

    fun getCharacterImages() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _cachedCharacters.value = CharacterRepository.getCachedCharacters()

            } catch(e: Exception) {
                e.printStackTrace()
                Log.d("CustomCharacterCreateViewModel", "Error getting character images: ${e.message}")
            }

        }
    }


    // Function to save the custom character to the database
    fun saveCustomCharacter() {
        viewModelScope.launch(Dispatchers.IO) {

            Log.d("CustomCharacterCreateViewModel", "Saving custom character...")

            try {
                val customCharacter = CustomCharacter(
                    name = _characterName.value.trim(),
                    status = checkIfInputIsBlankSetUnknown(_status.value, "unknown"), // Probably don't need to set status as "unknown" as it's unknown in mutableStateFlow, but decided for consistency
                    species = checkIfInputIsBlankSetUnknown(_species.value, "unknown"),
                    gender = checkIfInputIsBlankSetUnknown(_gender.value, "unknown"),
                    origin = checkIfInputIsBlankSetUnknown(_origin.value, "unknown"),
                    description = _description.value,
                    imageUrl = _imageUrl.value

                )

                CustomCharacterRepository.insertCustomCharacter(customCharacter)
                _saveSuccess.value = true;
                Log.d("CustomCharacterCreateViewModel", "New custom character saved: $customCharacter")
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                clearAllInputs()
            }
        }
    }

    // Function to reset the save state
    fun resetSaveState() {
        _saveSuccess.value = null
    }

}
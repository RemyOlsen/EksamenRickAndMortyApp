package com.example.eksamenrickandmortyapp.screens.create_character

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eksamenrickandmortyapp.data.CustomCharacter
import com.example.eksamenrickandmortyapp.data.CustomCharacterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CharacterCreateViewModel : ViewModel() {

    // StateFlows for inputs
    private val _characterName = MutableStateFlow("")
    val characterName = _characterName.asStateFlow()

    private val _status = MutableStateFlow("unknown")
    val status = _status.asStateFlow()

    private val _species = MutableStateFlow("unknown")
    val species = _species.asStateFlow()

    private val _type = MutableStateFlow<String?>(null)
    val type = _type.asStateFlow()

    private val _gender = MutableStateFlow("unknown")
    val gender = _gender.asStateFlow()

    private val _origin = MutableStateFlow("unknown")
    val origin = _origin.asStateFlow()

    private val _location = MutableStateFlow("unknown")
    val location = _location.asStateFlow()

    private val _description = MutableStateFlow<String?>(null)
    val description = _description.asStateFlow()

    private val _imageUrl = MutableStateFlow("https://rickandmortyapi.com/api/character/avatar/19.jpeg")
    val imageUrl = _imageUrl.asStateFlow()

    // StateFlow for saving
    private val _isSaving = MutableStateFlow(false)
    val isSaving = _isSaving.asStateFlow()

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

    fun typeInput(typeInput: String?) {
        _type.value = typeInput
    }

    fun genderInput(genderInput: String) {
        _gender.value = genderInput
    }

    fun originInput(originInput: String) {
        _origin.value = originInput
    }

    fun locationInput(locationInput: String) {
        _location.value = locationInput
    }

    fun descriptionInput(descriptionInput: String?) {
        _description.value = descriptionInput
    }

    fun imageInput(imageInput: String) {
        _description.value = imageInput
    }


    // Function to save the custom character to the database
    fun saveCustomCharacter() {
        viewModelScope.launch {
            _isSaving.value = true

            Log.d("CustomCharacterCreateViewModel", "Saving custom character...")

            try {
                val newCustomCharacter = CustomCharacter(
                    name = _characterName.value,
                    status = _status.value,
                    species = _species.value,
                    type = _type.value,
                    gender = _gender.value,
                    origin = _origin.value,
                    location = _location.value,
                    description = _description.value,
                    imageUrl = _imageUrl.value

                )

                CustomCharacterRepository.insertCustomCharacter(newCustomCharacter)

                Log.d("CustomCharacterCreateViewModel", "New custom character saved: $newCustomCharacter")
                _saveSuccess.value = true
            } catch (e: Exception) {
                e.printStackTrace()
                _saveSuccess.value = false
            } finally {
                _isSaving.value = false
            }
        }
    }

    // Function to reset the save state
    fun resetSaveState() {
        _saveSuccess.value = null
    }

}
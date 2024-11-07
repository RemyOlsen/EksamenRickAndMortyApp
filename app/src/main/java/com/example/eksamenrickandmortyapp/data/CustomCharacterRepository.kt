package com.example.eksamenrickandmortyapp.data

object CustomCharacterRepository {
    private val _customCharacterDao by lazy { CharacterRepository.getAppDatabase().customCharacterDao() }

    suspend fun insertCustomCharacter(customCharacter: CustomCharacter) {
        return _customCharacterDao.insertCustomCharacter(customCharacter)
    }

    suspend fun getCustomCharacters(): List<CustomCharacter> {
        return _customCharacterDao.getAllCustomCharacters()
    }



}
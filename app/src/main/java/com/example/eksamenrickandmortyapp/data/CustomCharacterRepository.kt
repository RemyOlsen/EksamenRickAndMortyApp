package com.example.eksamenrickandmortyapp.data

object CustomCharacterRepository {
    private val customCharacterDao by lazy { CharacterRepository.getAppDatabase().customCharacterDao() }

    suspend fun insertCustomCharacter(customCharacter: CustomCharacter) {
        return customCharacterDao.insertCustomCharacter(customCharacter)
    }

    suspend fun getCustomCharacters(): List<CustomCharacter> {
        return customCharacterDao.getAllCustomCharacters()
    }



}
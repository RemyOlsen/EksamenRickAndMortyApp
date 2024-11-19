package com.example.eksamenrickandmortyapp.data

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.eksamenrickandmortyapp.data.database.AppDatabase
import com.example.eksamenrickandmortyapp.data.entities.Character
import okio.IOException

object CharacterRepository {

   private val _apiService = ApiClient.apiService

    private lateinit var _appDatabase: AppDatabase
    private val _characterDao by lazy { _appDatabase.characterDao() }

    fun initializeDatabase(context: Context) {
        _appDatabase = Room.databaseBuilder(
            context = context,
            klass = AppDatabase::class.java,
            name = "character-database"
        ).build()
    }

    // Getter for the appDatabase so I can split the repositories
    fun getAppDatabase(): AppDatabase {
        if (!::_appDatabase.isInitialized) {
            throw IllegalStateException("you need to 'initializeDatabase() before getting it.")
        }
        return _appDatabase
    }

    suspend fun getCharactersFromApi(page: Int): List<Character> {

        try {

            val response = _apiService.getAllCharacters(page)

            if (response.isSuccessful) {

                Log.d("CharacterRepository", "Updating characters from API from page: $page")

                val characters = response.body()?.results ?: emptyList()

                // Storing the characters in the database
                _characterDao.insertCharacters(characters)

                Log.d("CharacterRepository", "Successfully updated characters from page: $page from the API")

                // Returning only the new characters
                return characters
            } else {
                throw Exception("Response was not successful")
            }
        } catch (e: IOException) {
            Log.e("CharacterRepository", "Network error, unable to connect to the API, getting characters from database", e)
            return emptyList()
        } catch (e: Exception) {
            Log.e("CharacterRepository", "Failed to get list of characters, getting characters from database", e)
            return emptyList()
        }
    }

    // getting the total page count from the meta data
    suspend fun getTotalPagesCount(): Int? {
        try {
            val response = _apiService.getAllCharacters(1)
            return if (response.isSuccessful) {
                response.body()?.info?.pages
            } else {
                null
            }
        } catch(e: Exception) {
            Log.e("CharacterRepository", "Failed to get total page count", e)
            return null
        }
    }

    // Getting total characters from the meta data
    suspend fun getTotalCharacterCount(): Int? {
        try {
            val response =_apiService.getAllCharacters(1)
            return if (response.isSuccessful) {
                response.body()?.info?.count
            } else {
                null
            }

        } catch(e: Exception) {
            Log.e("CharacterRepository", "Failed to get character count", e)
            return null
        }
    }

    suspend fun getCharacterById(characterId: Int): Character? {
        return try {
            _characterDao.getCharacterById(characterId)
        } catch (e: Exception) {
            Log.e("CharacterRepository", "Error fetching character by ID", e)
            null
        }
    }

    suspend fun getCachedCharacters(): List<Character> {
        Log.d("CharacterRepository", "Getting characters from database")
        return try {
            _characterDao.getCharacters()
        } catch (e: Exception) {
            Log.e("CharacterRepository", "Error fetching cached characters", e)
            emptyList()
        }
    }

    suspend fun getCharactersByIds(ids: List<Int>): List<Character> {
        return try {
            _characterDao.getCharactersByIds(ids)
        } catch (e: Exception) {
            Log.e("CharacterRepository", "Error fetching characters by IDs", e)
            emptyList()
        }
    }

}
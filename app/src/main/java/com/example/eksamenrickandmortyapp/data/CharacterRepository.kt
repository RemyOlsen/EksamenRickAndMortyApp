package com.example.eksamenrickandmortyapp.data

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.eksamenrickandmortyapp.data.database.AppDatabase
import com.example.eksamenrickandmortyapp.data.entities.Character
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okio.IOException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CharacterRepository {

    // Logging interceptor for better logging
    private val _httpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
        )
        .build()

    // Retrofit to connect to the API
    private val _retrofit =
        Retrofit.Builder()
            .client(_httpClient)
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    private val _characterService = _retrofit.create(CharacterService::class.java)

    private lateinit var _appDatabase: AppDatabase
    private val _characterDao by lazy { _appDatabase.characterDao() }
//    private val _metaInfoDao by lazy { _appDatabase.metaInfoDao() }

    fun initializeDatabase(context: Context) {
        _appDatabase = Room.databaseBuilder(
            context = context,
            klass = AppDatabase::class.java,
            name = "character-database"
        ).build()
    }

    fun getAppDatabase(): AppDatabase {
        if (!::_appDatabase.isInitialized) {
            throw IllegalStateException("you need to 'initializeDatabase() before getting it.")
        }
        return _appDatabase
    }

    suspend fun getCharacters(page: Int): List<Character> {

        try {

            val response = _characterService.getAllCharacters(page)

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
    suspend fun getTotalPageCount(): Int? {
        try {
            val response = _characterService.getAllCharacters(1)
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
            val response =_characterService.getAllCharacters(1)
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
        return _characterDao.getCharacterById(characterId)
    }


    suspend fun getCachedCharacters(): List<Character> {
        Log.d("CharacterRepository - fun getCachedCharacters", "Getting characters from database")
        return _characterDao.getCharacters()
    }

}
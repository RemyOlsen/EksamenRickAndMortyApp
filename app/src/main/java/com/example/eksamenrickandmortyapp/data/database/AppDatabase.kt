package com.example.eksamenrickandmortyapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.eksamenrickandmortyapp.data.entities.Character
import com.example.eksamenrickandmortyapp.data.entities.CustomCharacter
//import com.example.eksamenrickandmortyapp.data.MetaInfo
import com.example.eksamenrickandmortyapp.data.database.character.CharacterDao
import com.example.eksamenrickandmortyapp.data.database.custom_character.CustomCharacterDao
import com.example.eksamenrickandmortyapp.data.database.episode.EpisodeDao
import com.example.eksamenrickandmortyapp.data.entities.Episode

//import com.example.eksamenrickandmortyapp.data.database.meta_info.MetaInfoDao

@Database(
    entities =
    [
        Character::class,
        CustomCharacter::class,
        Episode::class
    ],
    version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
    abstract fun customCharacterDao(): CustomCharacterDao
    abstract fun episodeDao(): EpisodeDao
}
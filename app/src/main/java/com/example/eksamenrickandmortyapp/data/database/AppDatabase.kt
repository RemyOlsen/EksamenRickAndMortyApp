package com.example.eksamenrickandmortyapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.eksamenrickandmortyapp.data.Character
import com.example.eksamenrickandmortyapp.data.CustomCharacter
import com.example.eksamenrickandmortyapp.data.PageInfo
import com.example.eksamenrickandmortyapp.data.database.character.CharacterDao
import com.example.eksamenrickandmortyapp.data.database.custom_character.CustomCharacterDao
import com.example.eksamenrickandmortyapp.data.database.page_info.PageInfoDao

@Database(
    entities =
    [
        Character::class,
        PageInfo::class,
        CustomCharacter::class
    ],
    version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
    abstract fun pageInfoDao(): PageInfoDao
    abstract fun customCharacterDao(): CustomCharacterDao
}
package com.example.eksamenrickandmortyapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.eksamenrickandmortyapp.data.Character
import com.example.eksamenrickandmortyapp.data.PageInfo
import com.example.eksamenrickandmortyapp.data.database.character.CharacterDao
//import com.example.eksamenrickandmortyapp.data.database.custom_character.CustomCharacterDao
import com.example.eksamenrickandmortyapp.data.database.page_info.PageInfoDao

@Database(entities = [Character::class, PageInfo::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
    abstract fun pageInfoDao(): PageInfoDao
}

//@Database(entities = [CustomCharacter::class], version = 1, exportSchema = false)
//abstract class CustomCharacterDatabase : RoomDatabase() {
//    abstract fun customCharacterDao(): CustomCharacterDao
//
//}
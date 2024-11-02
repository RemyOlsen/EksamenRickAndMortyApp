package com.example.eksamenrickandmortyapp.data.database.type_converter

import androidx.room.TypeConverter
import com.example.eksamenrickandmortyapp.data.Location
import com.example.eksamenrickandmortyapp.data.Origin
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object CharacterTypeConverter {
    private val gson = Gson()

    // Converter for Origin
    @TypeConverter
    @JvmStatic
    fun fromOrigin(origin: Origin): String {
        return gson.toJson(origin)
    }

    @TypeConverter
    @JvmStatic
    fun toOrigin(originString: String): Origin {
        return gson.fromJson(originString, Origin::class.java)
    }

    // Converter for Location
    @TypeConverter
    @JvmStatic
    fun fromLocation(location: Location): String {
        return gson.toJson(location)
    }

    @TypeConverter
    @JvmStatic
    fun toLocation(locationString: String): Location {
        return gson.fromJson(locationString, Location::class.java)
    }


    // Converter for List<String> (episode urls)
    @TypeConverter
    @JvmStatic
    fun fromStringList(episodes: List<String>): String {
        return gson.toJson(episodes)
    }

    @TypeConverter
    @JvmStatic
    fun toStringList(episodeString: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(episodeString, listType)
    }
}
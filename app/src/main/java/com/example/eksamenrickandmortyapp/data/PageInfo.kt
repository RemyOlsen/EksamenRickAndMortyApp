package com.example.eksamenrickandmortyapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

// To remember the last fetched page so when you scroll down the page after restarting the app you won't have to fetch all the pages again before getting new data
@Entity(tableName = "PageInfo")
data class PageInfo(
    @PrimaryKey
    val id: Int = 1,
    val lastFetchedPage: Int
)
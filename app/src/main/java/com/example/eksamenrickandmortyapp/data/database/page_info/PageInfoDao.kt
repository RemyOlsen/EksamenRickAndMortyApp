package com.example.eksamenrickandmortyapp.data.database.page_info

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.eksamenrickandmortyapp.data.PageInfo

@Dao
interface PageInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPageInfo(pageInfo: PageInfo)

    @Query("SELECT lastFetchedPage FROM PageInfo WHERE id = 1")
    suspend fun getLastFetchedPage(): Int?
}
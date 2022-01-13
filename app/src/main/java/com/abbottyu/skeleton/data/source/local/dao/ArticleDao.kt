package com.abbottyu.skeleton.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.abbottyu.skeleton.data.source.local.models.Article

@Dao
interface ArticleDao {
    @Query("select * from article")
    suspend fun query(): List<Article>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(articles: List<Article>)
}
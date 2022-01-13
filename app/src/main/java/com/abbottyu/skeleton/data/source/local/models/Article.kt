package com.abbottyu.skeleton.data.source.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "article")
data class Article(
    @PrimaryKey
    var id: Long = 0,
    var link: String?,
    var title: String?
)
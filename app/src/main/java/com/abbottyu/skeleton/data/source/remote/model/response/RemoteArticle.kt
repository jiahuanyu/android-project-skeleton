package com.abbottyu.skeleton.data.source.remote.model.response

import androidx.annotation.Keep

@Keep
data class RemoteArticle(
    val link: String?,
    val title: String?,
    val id: Long
)
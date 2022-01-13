package com.abbottyu.skeleton.data.source.remote.model

import androidx.annotation.Keep

@Keep
class CommonResponse<T> {
    val errorCode: Int = 0
    val errorMsg: String = ""
    val data: T? = null
}
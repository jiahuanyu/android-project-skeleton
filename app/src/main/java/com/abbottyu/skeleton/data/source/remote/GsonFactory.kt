package com.abbottyu.skeleton.data.source.remote

import com.google.gson.Gson
import com.google.gson.GsonBuilder

object GsonFactory {
    @JvmStatic
    val gson: Gson
        get() = GsonBuilder().registerTypeAdapterFactory(EnumTypeAdapterFactory("code")).create()

    @JvmStatic
    val gsonExcludeFieldsWithoutExposeAnnotation: Gson
        get() = GsonBuilder().excludeFieldsWithoutExposeAnnotation().registerTypeAdapterFactory(
            EnumTypeAdapterFactory("code")
        ).create()
}

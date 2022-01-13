package com.abbottyu.skeleton.data.source.remote.service

import com.abbottyu.skeleton.data.source.remote.model.CommonResponse
import com.abbottyu.skeleton.data.source.remote.model.response.RemoteBanner
import com.abbottyu.skeleton.data.source.remote.model.response.data.ArticleData
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    /**
     * 获取文章列表
     */
    @GET("/article/list/{page}/json")
    suspend fun queryArticles(@Path("page") page: Int): CommonResponse<ArticleData?>

    /**
     * 获取首页 Banner
     */
    @GET("/banner/json")
    suspend fun queryBanners(): CommonResponse<List<RemoteBanner?>?>
}
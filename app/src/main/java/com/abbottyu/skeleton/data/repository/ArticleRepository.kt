package com.abbottyu.skeleton.data.repository

import com.abbottyu.skeleton.base.IO_DISPATCHER
import com.abbottyu.skeleton.data.source.local.dao.ArticleDao
import com.abbottyu.skeleton.data.source.remote.model.response.RemoteBanner
import com.abbottyu.skeleton.data.source.remote.service.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import com.abbottyu.skeleton.data.source.local.models.Article
import javax.inject.Inject

class ArticleRepository @Inject constructor(
    private val apiService: ApiService,
    private val articleDao: ArticleDao
) {

    fun queryArticles(): Flow<List<Article>> {
        return flow {
            try {
                val commonResponse = apiService.queryArticles(0)
                if (commonResponse.errorCode == 0) {
                    val remoteArticles = commonResponse.data?.datas?.filterNotNull() ?: emptyList()
                    emit(remoteArticles.map {
                        Article(
                            id = it.id,
                            link = it.link,
                            title = it.title,
                        )
                    })
                } else {
                    error(commonResponse.errorMsg)
                }
            } catch (e: Exception) {
                error(e.toString())
            }
        }.flowOn(IO_DISPATCHER)
    }

    fun queryBanners() : Flow<List<RemoteBanner>> {
        return flow {
            try {
                val commonResponse = apiService.queryBanners()
                if(commonResponse.errorCode == 0) {
                    val banners = commonResponse.data?.filterNotNull() ?: emptyList()
                    emit(banners)
                } else {
                    error(commonResponse.errorMsg)
                }
            } catch (e: Exception) {
                error(e.toString())
            }
        }.flowOn(IO_DISPATCHER)
    }
}
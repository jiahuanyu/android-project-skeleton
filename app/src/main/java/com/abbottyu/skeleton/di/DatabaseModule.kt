package com.abbottyu.skeleton.di

import android.content.Context
import com.abbottyu.skeleton.data.source.local.dao.ArticleDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import com.abbottyu.skeleton.data.source.local.AppDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Singleton
    @Provides
    fun provideArticleDao(@ApplicationContext applicationContext: Context): ArticleDao {
        return AppDatabase.get(applicationContext).articleDao()
    }
}
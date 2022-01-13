package com.abbottyu.skeleton.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.abbottyu.skeleton.data.source.local.dao.ArticleDao
import com.abbottyu.skeleton.data.source.local.models.Article

@Database(entities = [Article::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        private var instance: AppDatabase? = null

        fun get(context: Context): AppDatabase {
            if (instance == null) {
                synchronized(AppDatabase::class) {
                    if (instance == null) {
                        instance = Room.databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java,
                            "app_database"
                        )
                            .addMigrations()
                            .fallbackToDestructiveMigrationOnDowngrade()
                            .build()
                    }
                }
            }
            return instance!!
        }
    }

    abstract fun articleDao(): ArticleDao
}
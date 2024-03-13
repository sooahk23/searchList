package com.example.daumsearch

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.daumsearch.db.AppDatabase

class DaumSearchApp : Application() {
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // 이미 인스턴스가 있으면 반환, 없으면 새로 생성
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "daum_search"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

    fun getDatabase(): AppDatabase {
        return INSTANCE?: throw IllegalStateException("Database not initialized")
    }

    override fun onCreate() {
        super.onCreate()
    }
}
package com.elacqua.gpacademic.di

import android.content.Context
import androidx.room.Room
import com.elacqua.gpacademic.data.db.LocalDatabase
import com.elacqua.gpacademic.data.db.dao.TermDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): LocalDatabase{
        return Room.databaseBuilder(appContext,
                LocalDatabase::class.java,
                "gpaDb")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideTermDao(db: LocalDatabase): TermDao{
        return db.termDao()
    }
}
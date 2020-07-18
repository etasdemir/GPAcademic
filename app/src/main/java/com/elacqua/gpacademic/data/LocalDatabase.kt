package com.elacqua.gpacademic.data

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.elacqua.gpacademic.utility.LessonConverter

@Database(entities = arrayOf(Term::class), version = 1)
@TypeConverters(LessonConverter::class)
abstract class LocalDatabase: RoomDatabase() {

    abstract fun termDao(): TermDao

    companion object{
        @Volatile
        private lateinit var instance: LocalDatabase

        fun getDatabase(application: Application): LocalDatabase{
            if (!::instance.isInitialized){
                instance = Room.databaseBuilder(application, LocalDatabase::class.java, "gpaDb")
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance
        }
    }
}
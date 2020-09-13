package com.elacqua.gpacademic.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.elacqua.gpacademic.data.db.dao.TermDao
import com.elacqua.gpacademic.data.db.entities.Term
import com.elacqua.gpacademic.util.LessonConverter

@Database(entities = [Term::class], version = 1)
@TypeConverters(LessonConverter::class)
abstract class LocalDatabase: RoomDatabase() {
    abstract fun termDao(): TermDao
}
package com.elacqua.gpacademic.utility

import androidx.room.TypeConverter
import com.elacqua.gpacademic.data.Lesson
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class LessonConverter{
    @TypeConverter
    fun fromSource(source: List<Lesson>) :String{
        return Gson().toJson(source)
    }
    @TypeConverter
    fun toSource(name: String): List<Lesson> {
        val notesType = object : TypeToken<List<Lesson>>() {}.type
        return Gson().fromJson<List<Lesson>>(name, notesType)
    }
}
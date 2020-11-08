package com.elacqua.gpacademic.util

import com.elacqua.gpacademic.data.db.entities.Lesson
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test

class LessonConverterTest {
    private lateinit var lessonConverter: LessonConverter

    @Before
    fun initLessonConverter() {
        lessonConverter = LessonConverter()
    }

    @Test
    fun fromSource_lessonList_returnsGsonString() {
        val lessons = arrayListOf(
            Lesson("Chika", 2, "B1")
        )
        val result = lessonConverter.fromSource(lessons)
        assertThat(result, `is`("[{\"lessonName\":\"Chika\",\"credits\":2,\"grade\":\"B1\"}]"))
    }

    @Test
    fun toSource_gsonString_returnsLessonList() {
        val gsonString = "[{\"lessonName\":\"Chika\",\"credits\":2,\"grade\":\"B1\"}]"
        val result = lessonConverter.toSource(gsonString)
        assertThat(result, `is`(arrayListOf(Lesson("Chika", 2, "B1"))))
    }
}
package com.elacqua.gpacademic.util

import com.elacqua.gpacademic.data.db.entities.Lesson
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class UtilityTest {

    @Test
    fun calculateGpa_typeOne_returnsThreePointTwentyFive() {
        val lessons = arrayListOf(
            Lesson("", 1, "A1"),
            Lesson("", 3, "B2")
        )
        val result = Utility.calculateGPA(1, lessons)
        assertThat(result, `is`("3.25"))
    }

    @Test
    fun calculateGpa_typeTwo_returnsTwoPointThirtyFive() {
        val lessons = arrayListOf(
            Lesson("", 1, "A-"),
            Lesson("", 1, "D")
        )
        val result = Utility.calculateGPA(2, lessons)
        assertThat(result, `is`("2.35"))
    }

    @Test
    fun calculateGpa_typeThree_returnsTwoPointFifty() {
        val lessons = arrayListOf(
            Lesson("", 1, "AA"),
            Lesson("", 1, "CB"),
            Lesson("", 1, "DD")
        )
        val result = Utility.calculateGPA(3, lessons)
        assertThat(result, `is`("2.50"))
    }

    @Test
    fun calculateGPA_emptyList_returnsNaN() {
        // Given
        val lessonList = ArrayList<Lesson>()
        // When
        val result = Utility.calculateGPA(1, lessonList)
        // Then
        assertThat(result, `is`("NaN"))
    }

}
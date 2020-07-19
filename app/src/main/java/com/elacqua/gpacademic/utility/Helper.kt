package com.elacqua.gpacademic.utility

import com.elacqua.gpacademic.data.local.Lesson
import java.util.*

class Helper {
    companion object {
        fun calculateGPA(type: Int, lessonList: ArrayList<Lesson>): String {
            var totalCredit = 0
            var total = 0.00

            for (lesson in lessonList) {
                totalCredit += lesson.credits
                total += findNumericNote(type, lesson.grade) * lesson.credits
            }
            return String.format("%.2f", (total / totalCredit))
        }

        private fun findNumericNote(type: Int, lessonGrade: String) =
            when (type) {
                1 -> findNumericNoteType1(lessonGrade)
                2 -> findNumericNoteType2(lessonGrade)
                3 -> findNumericNoteType3(lessonGrade)
                else -> 0.00
            }

        private fun findNumericNoteType1(lessonGrade: String) =
            when (lessonGrade) {
                "A1" -> 4.00
                "A2" -> 3.75
                "A3" -> 3.50
                "B1" -> 3.25
                "B2" -> 3.00
                "B3" -> 2.75
                "C1" -> 2.50
                "C2" -> 2.25
                "C3" -> 2.00
                "D" -> 1.75
                else -> 0.00
            }

        private fun findNumericNoteType2(lessonGrade: String) =
            when(lessonGrade) {
                "A" -> 4.00
                "A-" -> 3.7
                "B+" -> 3.30
                "B" -> 3.00
                "B-" -> 2.70
                "C+" -> 2.30
                "C" -> 2.00
                "C-" -> 1.70
                "D+" -> 1.30
                "D" -> 1.00
                else -> 0.00
            }

        private fun findNumericNoteType3(lessonGrade: String) =
            when(lessonGrade) {
                "AA" -> 4.00
                "BA" -> 3.50
                "BB" -> 3.00
                "CB" -> 2.50
                "CC" -> 2.00
                "DC" -> 1.50
                "DD" -> 1.00
                else -> 0.00
            }
    }
}




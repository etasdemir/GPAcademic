package com.elacqua.gpacademic.ui.dreamgpa

import androidx.lifecycle.ViewModel

class DreamGpaViewModel : ViewModel() {
    fun calculateDreamGpa(totalCredit: String, termCredit: String, currentGpa: String, dreamGpa: String): Float {
        if (totalCredit.isBlank() || termCredit.isBlank() || currentGpa.isBlank() || dreamGpa.isBlank()){
            return 0F
        }
        val convertedTotalCredit = totalCredit.toInt()
        val convertedTermCredit = termCredit.toInt()
        val convertedCurrentGpa = currentGpa.toFloat()
        val convertedDreamGpa = dreamGpa.toFloat()

        return (((convertedTotalCredit + convertedTermCredit)*convertedDreamGpa)
                - (convertedTotalCredit*convertedCurrentGpa))/convertedTermCredit
    }


}
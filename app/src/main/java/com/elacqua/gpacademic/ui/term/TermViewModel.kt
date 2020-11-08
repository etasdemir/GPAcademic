package com.elacqua.gpacademic.ui.term

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elacqua.gpacademic.data.Repository
import com.elacqua.gpacademic.data.db.entities.Lesson
import com.elacqua.gpacademic.data.db.entities.Term
import com.elacqua.gpacademic.util.Utility
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TermViewModel @ViewModelInject constructor(private val repository: Repository)
    : ViewModel() {

    val termsLiveData = getTerms()

    private fun getTerms(): LiveData<List<Term>> = repository.getAllTerms()

    fun deleteTerm(term: Term){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTerm(term)
        }
    }

    fun calculateCumulativeGpa(
        type: Int,
        termHashedMap: HashMap<Term, Boolean>,
        termList: MutableList<Term>
    ): String {
        val totalLessonList = ArrayList<Lesson>()
        for (term in termList){
            val isChecked = termHashedMap[term]
            if (isChecked != null && isChecked){
                totalLessonList.addAll(term.lessons)
            }
        }
        val gpa = Utility.calculateGPA(type, totalLessonList)
        return "CGPA: $gpa"
    }
}
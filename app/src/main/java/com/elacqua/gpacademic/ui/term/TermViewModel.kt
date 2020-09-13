package com.elacqua.gpacademic.ui.term

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.elacqua.gpacademic.data.Repository
import com.elacqua.gpacademic.data.db.entities.Lesson
import com.elacqua.gpacademic.data.db.entities.Term
import com.elacqua.gpacademic.util.Helper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import timber.log.Timber

class TermViewModel @ViewModelInject constructor(private val repository: Repository)
    : ViewModel() {

    val termsLiveData = getTerms()

    init {
        Timber.d("term view model created: " )
    }

    private fun getTerms(): LiveData<List<Term>> {
        Timber.d("getTerms: terms fetched")
        return repository.getAllTerms()
    }

    fun deleteTerm(term: Term){
        CoroutineScope(IO).launch {
            repository.deleteTerm(term)
        }
    }

    fun calculateCgpa(
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
        val gpa = Helper.calculateGPA(type, totalLessonList)
        return "CGPA: $gpa"
    }
}
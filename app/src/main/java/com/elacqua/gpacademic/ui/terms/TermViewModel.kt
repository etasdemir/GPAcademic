package com.elacqua.gpacademic.ui.terms

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.elacqua.gpacademic.data.Repository
import com.elacqua.gpacademic.data.local.Lesson
import com.elacqua.gpacademic.data.local.Term
import com.elacqua.gpacademic.utility.Helper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class TermViewModel(application: Application) : AndroidViewModel(application) {
    private val repository by lazy { Repository(application) }
    val termsLiveData: LiveData<List<Term>> = getTerms()

    private fun getTerms(): LiveData<List<Term>> {
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
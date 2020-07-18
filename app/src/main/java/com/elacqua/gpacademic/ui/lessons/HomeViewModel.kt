package com.elacqua.gpacademic.ui.lessons

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.elacqua.gpacademic.data.Lesson
import com.elacqua.gpacademic.data.Repository
import com.elacqua.gpacademic.data.Term
import com.elacqua.gpacademic.utility.Helper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.util.*

private const val TAG = "HomeViewModel"

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private var gpa = "GPA: "
    private val repository by lazy { Repository(application) }
    var updateId: Int ?= null

    fun insertOrUpdateTerm(name:String, lessonList: List<Lesson>){
        if (updateId != null){
            updateTerm(name, lessonList)
        } else {
            insertTerm(name, lessonList)
        }
    }

    private fun updateTerm(name: String, lessonList: List<Lesson>){
        CoroutineScope(IO).launch {
            val term = Term(updateId, name, lessonList)
            repository.updateTerm(term)
            Log.e(TAG, "insertTerm: Updated")
        }
    }

    private fun insertTerm(name: String, lessonList: List<Lesson>){
        CoroutineScope(IO).launch {
            val term = Term(null, name, lessonList)
            val insertedId = repository.insertTerm(term)
            Log.e(TAG, "insertTerm: $insertedId")
        }
    }

    fun getGpa(type: Int, lessonList: ArrayList<Lesson>): String {
        val grade = Helper.calculateGPA(type, lessonList)
        return gpa + grade
    }

}

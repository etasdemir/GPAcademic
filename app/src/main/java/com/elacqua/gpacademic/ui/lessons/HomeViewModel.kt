package com.elacqua.gpacademic.ui.lessons

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.elacqua.gpacademic.data.Repository
import com.elacqua.gpacademic.data.local.Lesson
import com.elacqua.gpacademic.data.local.Term
import com.elacqua.gpacademic.utility.Helper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

private const val TAG = "HomeViewModel"

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private var gpa = "GPA: "
    private val repository by lazy { Repository(application) }
    private val _lessonMutableLiveList
            = MutableLiveData<ArrayList<Lesson>>().apply {
        value = ArrayList()
    }
    val lessonLiveDataList = _lessonMutableLiveList
    var updateId: Int ?= null


    fun addLesson() {
        val lesson = Lesson("", 1, "")
        _lessonMutableLiveList.value!!.add(lesson)
        _lessonMutableLiveList.value = _lessonMutableLiveList.value
    }

    fun deleteLessonAt(position: Int) {
        _lessonMutableLiveList.value!!.removeAt(position)
        _lessonMutableLiveList.value = _lessonMutableLiveList.value
    }

    fun setLessonList(lessons: List<Lesson>) {
        _lessonMutableLiveList.value = lessons as ArrayList<Lesson>
    }

    fun insertOrUpdateTerm(name:String){
        val lessonList: ArrayList<Lesson> = _lessonMutableLiveList.value!!
        if (updateId != null){
            updateTerm(name, lessonList)
        } else {
            insertTerm(name, lessonList)
        }
    }

    private fun updateTerm(name: String, lessonList: List<Lesson>){
        CoroutineScope(IO).launch {
            val term = Term(
                updateId,
                name,
                lessonList
            )
            repository.updateTerm(term)
            Log.e(TAG, "insertTerm: Updated")
        }
    }

    private fun insertTerm(name: String, lessonList: List<Lesson>){
        CoroutineScope(IO).launch {
            val term =
                Term(null, name, lessonList)
            val insertedId = repository.insertTerm(term)
            Log.e(TAG, "insertTerm: $insertedId")
        }
    }

    fun getGpa(type: Int): String {
        val lessonList: ArrayList<Lesson> = _lessonMutableLiveList.value!!
        val grade = Helper.calculateGPA(type, lessonList)
        return gpa + grade
    }

}

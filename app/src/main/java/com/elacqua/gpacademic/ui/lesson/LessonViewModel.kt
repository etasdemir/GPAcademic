package com.elacqua.gpacademic.ui.lesson

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elacqua.gpacademic.data.Repository
import com.elacqua.gpacademic.data.db.entities.Lesson
import com.elacqua.gpacademic.data.db.entities.Term
import com.elacqua.gpacademic.util.Utility
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import timber.log.Timber

class LessonViewModel @ViewModelInject constructor(private val repository: Repository)
    : ViewModel() {

    private var gpa = "GPA: "
    private val _lessonMutableLiveList
            = MutableLiveData<ArrayList<Lesson>>(ArrayList())
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
        viewModelScope.launch(IO) {
            val term = Term(
                updateId,
                name,
                lessonList
            )
            repository.updateTerm(term)
        }
    }

    private fun insertTerm(name: String, lessonList: List<Lesson>){
        viewModelScope.launch(IO) {
            val term =
                Term(null, name, lessonList)
            val insertedId = repository.insertTerm(term)
            Timber.d( "insertTerm: $insertedId")
        }
    }

    fun getGpa(type: Int): String {
        val lessonList: ArrayList<Lesson> = _lessonMutableLiveList.value!!
        val grade = Utility.calculateGPA(type, lessonList)
        return gpa + grade
    }

}

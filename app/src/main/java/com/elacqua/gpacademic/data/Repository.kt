package com.elacqua.gpacademic.data

import android.app.Application
import androidx.lifecycle.LiveData
import com.elacqua.gpacademic.data.local.LocalDatabase
import com.elacqua.gpacademic.data.local.Term
import com.elacqua.gpacademic.data.local.TermDao

class Repository(application: Application) {
    private var database: LocalDatabase = LocalDatabase.getDatabase(application)
    private var termDao: TermDao

    init {
        termDao = database.termDao()
    }

    suspend fun insertTerm(term: Term): Long{
        return termDao.insertTerm(term)
    }

    suspend fun deleteTerm(term: Term){
        termDao.deleteTerm(term)
    }

    fun getAllTerms(): LiveData<List<Term>>{
        return termDao.getAllTerms()
    }

    suspend fun updateTerm(term: Term){
        termDao.updateTerm(term)
    }

    suspend fun nukeTermTable(){
        termDao.nukeTermTable()
    }

}
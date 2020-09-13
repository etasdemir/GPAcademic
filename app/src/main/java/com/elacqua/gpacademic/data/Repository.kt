package com.elacqua.gpacademic.data

import androidx.lifecycle.LiveData
import com.elacqua.gpacademic.data.db.dao.TermDao
import com.elacqua.gpacademic.data.db.entities.Term
import javax.inject.Inject

class Repository @Inject constructor(private val termDao: TermDao) {

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
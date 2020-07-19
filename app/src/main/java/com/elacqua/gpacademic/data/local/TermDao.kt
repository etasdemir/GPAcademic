package com.elacqua.gpacademic.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.elacqua.gpacademic.data.local.Term

@Dao
interface TermDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTerm(term: Term): Long

    @Delete
    suspend fun deleteTerm(term: Term)

    @Query("select * from Term")
    fun getAllTerms(): LiveData<List<Term>>

    @Query("delete from Term")
    suspend fun nukeTermTable()

    @Update
    suspend fun updateTerm(term: Term)
}
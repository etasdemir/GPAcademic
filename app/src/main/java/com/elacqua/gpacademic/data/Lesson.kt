package com.elacqua.gpacademic.data

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Lesson(var lessonName: String, var credits: Int, var grade: String): Parcelable
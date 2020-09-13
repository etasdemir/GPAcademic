package com.elacqua.gpacademic.data.db.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.elacqua.gpacademic.util.LessonConverter
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Term(@PrimaryKey(autoGenerate = true) val id: Int?,
                val termName: String,
                @TypeConverters(LessonConverter::class) val lessons: List<Lesson>): Parcelable {
    constructor():this(null, "", emptyList())
}


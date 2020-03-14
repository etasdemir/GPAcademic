package com.elacqua.gpacalculate

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.single_line_recycler.view.*

class CustomRecycleAdapter(lessons: ArrayList<Lesson>): RecyclerView.Adapter<CustomRecycleAdapter.LessonViewHolder>() {
    val TAG = "CustomRecyclerAdapter"
    var lessonArray = lessons

    fun arrayListChanged(newLessonArray: ArrayList<Lesson>){
        this.lessonArray = newLessonArray
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val singleView = inflater.inflate(R.layout.single_line_recycler, parent, false)

        return LessonViewHolder(singleView)
    }

    override fun getItemCount(): Int {
        return lessonArray.size
    }

    override fun onBindViewHolder(holder: LessonViewHolder, position: Int) {
        Log.d(TAG, "new lesson: ${lessonArray[position].lessonName}, ${lessonArray[position].lessonCredit}")
        holder.title.text = lessonArray[position].lessonName
        holder.credit.text = lessonArray[position].lessonCredit
        holder.grade.text = lessonArray[position].lessonGrade

        holder.clickListener(position, lessonArray)

    }
    

    inner class LessonViewHolder(curView: View): RecyclerView.ViewHolder(curView) {
        val TAG = "CustomRcyc::InnerClass"
        val view = curView
        val title = view.txtSingleTitle
        val grade = view.txtSingleGrade
        val credit = view.txtSingleCredit

        fun clickListener(position: Int, lessonArray: ArrayList<Lesson>){
            view.btnSingleDelete.setOnClickListener {
                Log.d(TAG, "item position: $position item name: ${lessonArray[position].lessonName}")
                lessonArray.removeAt(position)//Not updated in recycler view
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, lessonArray.size)
            }
        }

    }
}
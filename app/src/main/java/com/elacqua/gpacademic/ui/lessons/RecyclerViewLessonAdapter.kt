package com.elacqua.gpacademic.ui.lessons

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.recyclerview.widget.RecyclerView
import com.elacqua.gpacademic.R
import com.elacqua.gpacademic.data.Lesson
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.item_lesson.view.*

class RecyclerViewLessonAdapter(context: Context, private val type: Int):
    RecyclerView.Adapter<RecyclerViewLessonAdapter.ViewHolder>(){

    private var lessonList = ArrayList<Lesson>()
    private val creditAdapter = ArrayAdapter
        .createFromResource(context,
            R.array.credits, android.R.layout.simple_spinner_dropdown_item)
    private val gradeAdapter = ArrayAdapter
        .createFromResource(context, getGradeArray(), android.R.layout.simple_spinner_dropdown_item)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_lesson, parent, false)
        return ViewHolder(view, CustomEditTextListener())
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.customEditTextListener.updatePosition(position)
        holder.spinnerGrade.run {
            adapter = gradeAdapter
            setSelection(gradeAdapter.getPosition(lessonList[position].grade))
            onItemSelectedListener = spinnerGradeSelectListener(position)
        }

        holder.spinnerCredit.run {
            adapter = creditAdapter
            setSelection(creditAdapter.getPosition(lessonList[position].credits.toString()))
            onItemSelectedListener = spinnerCreditSelected(position)
        }

        holder.txtLesson.run {
            editText?.setText(lessonList[holder.adapterPosition].lessonName)
        }
    }

    private fun getGradeArray() = when (type) {
        1 -> R.array.grades_type_1
        2 -> R.array.grades_type_2
        3 -> R.array.grades_type_3
        else -> R.array.grades_type_1
    }

    private fun spinnerGradeSelectListener(itemPosition: Int) = object: AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            if (itemPosition < lessonList.size){
                lessonList[itemPosition].grade = parent?.selectedItem as String
            }
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
            return
        }
    }

    private fun spinnerCreditSelected(itemPosition: Int) = object: AdapterView.OnItemSelectedListener{
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            if (itemPosition < lessonList.size){
                lessonList[itemPosition].credits = (parent?.selectedItem as String).toInt()
            }
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
            return
        }
    }

    override fun getItemCount(): Int {
        return lessonList.size
    }

    fun addLesson(){
        val lesson = Lesson("", 1, "")
        lessonList.add(lesson)
        notifyItemInserted(lessonList.size)
    }

    fun deleteItemAt(position: Int) {
        lessonList.removeAt(position)
        notifyItemRemoved(position)
    }

    fun getAdapterList(): ArrayList<Lesson>{
        return lessonList
    }

    fun setAdapterList(lessonList: List<Lesson>){
        this.lessonList = ArrayList(lessonList)
        notifyDataSetChanged()
    }

    inner class ViewHolder(
        view: View,
        var customEditTextListener: CustomEditTextListener
    ): RecyclerView.ViewHolder(view){
        val txtLesson = view.txtLesson as TextInputLayout
        val spinnerGrade = view.spinnerGrade as Spinner
        val spinnerCredit = view.spinnerCredit as Spinner

        init {
            txtLesson.editText?.addTextChangedListener(customEditTextListener)
        }

    }

    inner class CustomEditTextListener: TextWatcher {
        private var position = -1

        fun updatePosition(position: Int) {
            this.position = position;
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (position != -1 && position < lessonList.size){
                lessonList[position].lessonName = s.toString()
            }
        }

        override fun afterTextChanged(s: Editable?) {
            return
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            return
        }
    }
}
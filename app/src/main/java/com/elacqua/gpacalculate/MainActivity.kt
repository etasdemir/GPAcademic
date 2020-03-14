package com.elacqua.gpacalculate

import android.os.Bundle
import android.view.MotionEvent
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val arrayLesson = ArrayList<Lesson>()
    val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        MobileAds.initialize(this) {}
        val adRequest = AdRequest.Builder().addTestDevice("70DA19D80F160BE76A1B5D4B2BE32A17").build()
        adBottomBanner.loadAd(adRequest)

        val gradesAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.notes,
            android.R.layout.simple_spinner_dropdown_item
        )
        val creditsAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.credits,
            android.R.layout.simple_spinner_dropdown_item
        )

        spnGrade.adapter = gradesAdapter
        spnCredits.adapter = creditsAdapter

        val recyclerAdapter = CustomRecycleAdapter(arrayLesson)
        recyclerView.adapter = recyclerAdapter
        val recyclerLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerView.layoutManager = recyclerLayoutManager

        btnAdd.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    v.background = ContextCompat.getDrawable(this, R.drawable.add_button_clicked)
                    v.invalidate()
                }
                MotionEvent.ACTION_UP -> {
                    v.background = ContextCompat.getDrawable(this, R.drawable.add_button_new)
                    v.invalidate()
                }
            }
            false
        }

        btnCalc.setOnTouchListener{v,event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    v.background = ContextCompat.getDrawable(this, R.drawable.calculate_clicked)
                    v.invalidate()
                }
                MotionEvent.ACTION_UP -> {
                    v.background = ContextCompat.getDrawable(this, R.drawable.bottom_txt)
                    v.invalidate()
                }
            }
            false
        }

        btnAdd.setOnClickListener {
            recyclerHandler()
            recyclerAdapter.arrayListChanged(arrayLesson)
        }

        btnCalc.setOnClickListener{
            calculateGPA()
        }

    }


    private fun recyclerHandler(){
        val lessonName = txtLesson.text.toString()
        val credit = spnCredits.selectedItem.toString()
        val grade = spnGrade.selectedItem.toString()

        arrayLesson.add(Lesson(lessonName, credit, grade))
    }

    private fun calculateGPA(){
        var totalCredit= 0
        var total = 0.00

        for(elements in arrayLesson){
            totalCredit += creditToInt(elements.lessonCredit)
            total += findNumericNote(elements.lessonGrade)*creditToInt(elements.lessonCredit)
        }
        val gpa = String.format("%.2f", (total/totalCredit))
        txtGPA.text = "GPA: $gpa"
    }

    private fun creditToInt(lessonCredit: String): Int {
        return lessonCredit.split(" ")[0].toInt()
    }

    private fun findNumericNote(lessonGrade: String): Double{
        return when(lessonGrade){
            "A1" -> 4.00
            "A2" -> 3.75
            "A3" -> 3.50
            "B1" -> 3.25
            "B2" -> 3.00
            "B3" -> 2.75
            "C1" -> 2.50
            "C2" -> 2.25
            "C3" -> 2.00
            "D" -> 1.75
            else -> 0.00
        }
    }
}


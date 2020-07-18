package com.elacqua.gpacademic

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.elacqua.gpacademic.utility.AppPreferences
import kotlinx.android.synthetic.main.activity_gpa_type.*

class GpaTypeActivity : AppCompatActivity() {
    private var type: Int ?= null
    private var doNotShowAgain = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gpa_type)

        btnType1.setOnClickListener {
            type = 1
            saveToSharedPreferences()
            changeActivityToMain()
        }

        btnType2.setOnClickListener {
            type = 2
            saveToSharedPreferences()
            changeActivityToMain()
        }

        btnType3.setOnClickListener {
            type = 3
            saveToSharedPreferences()
            changeActivityToMain()
        }
    }

    private fun saveToSharedPreferences() {
        if (checkBoxDoNotShow.isChecked) {
            doNotShowAgain = true
        }
        val sharedRef = AppPreferences(this)
        sharedRef.saveGpaType(type!!)
        sharedRef.saveDoNotShowAgain(doNotShowAgain)
    }

    private fun changeActivityToMain(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
package com.elacqua.gpacademic.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.elacqua.gpacademic.R
import com.elacqua.gpacademic.util.AppPreferences
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_gpa_type.*

@AndroidEntryPoint
class GpaTypeActivity : AppCompatActivity(){
    private var type: Int ?= null
    private var doNotShowAgain = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gpa_type)

        initButtonListeners()
    }

    private fun initButtonListeners() {
        btnType1.setOnClickListener {
            type = 1
            saveAndNavigate()
        }
        btnType2.setOnClickListener {
            type = 2
            saveAndNavigate()
        }
        btnType3.setOnClickListener {
            type = 3
            saveAndNavigate()
        }
    }

    private fun saveAndNavigate(){
        saveToSharedPreferences()
        navigateToMainActivity()
    }

    private fun saveToSharedPreferences() {
        if (checkBoxDoNotShow.isChecked) {
            doNotShowAgain = true
        }
        val sharedRef = AppPreferences(this)
        sharedRef.saveGpaType(type!!)
        sharedRef.saveDoNotShowAgain(doNotShowAgain)
    }

    private fun navigateToMainActivity(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
package com.elacqua.gpacademic.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.elacqua.gpacademic.R
import com.elacqua.gpacademic.util.AppPreferences
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        CoroutineScope(Dispatchers.Main).launch {
            delay(1000)
            navigateToGpaTypeOrMain()
        }
    }

    private suspend fun navigateToGpaTypeOrMain(){
        val sharedRef = AppPreferences(this)
        val doNotShowAgain = sharedRef.doNotShowAgain

        val intent = if (doNotShowAgain){
            Intent(this, MainActivity::class.java)
        } else {
            Intent(this, GpaTypeActivity::class.java)
        }
        startActivity(intent)
        finish()
    }
}
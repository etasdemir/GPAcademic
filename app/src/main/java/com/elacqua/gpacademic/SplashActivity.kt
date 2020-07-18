package com.elacqua.gpacademic

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.elacqua.gpacademic.utility.AppPreferences

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({navigateToGpaTypeOrMain()}, 1200)
    }

    private fun navigateToGpaTypeOrMain(){
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
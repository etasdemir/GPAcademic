package com.elacqua.gpacalculate

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        Glide.with(this).load(R.drawable.logo).into(imgLogo)

        object: CountDownTimer(1500,1500){
            override fun onFinish() {
                val intent = Intent(this@SplashScreen,MainActivity::class.java)
                startActivity(intent)
                finish()
            }

            override fun onTick(millisUntilFinished: Long) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        }.start()
    }
}

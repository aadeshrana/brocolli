package com.example.broccoli

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed({

            val isUserRegistered = getSharePref()
            if(isUserRegistered.equals("false")) {
                val intent = Intent(this, PreRegister::class.java)
                startActivity(intent)
                finish()
            }else{
                val intent = Intent(this, PostRegisted::class.java)
                startActivity(intent)
                finish()
            }
        }, 3000)
    }

    //Check shared preference to redirect to activity
    fun getSharePref(): String? {
        val sharedPreferences: SharedPreferences = this.getSharedPreferences("userDetails",
            Context.MODE_PRIVATE)
        val isUserRegistered = sharedPreferences.getString("isRegistered","false")
        return isUserRegistered
    }
}
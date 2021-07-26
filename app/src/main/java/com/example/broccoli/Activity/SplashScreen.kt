package com.example.broccoli.Activity

import HelperClass.SharePreferenceProvider
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.broccoli.R

class SplashScreen : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
       val sharePreferenceProvider = SharePreferenceProvider(this)

        Handler().postDelayed({

            val isUserRegistered = sharePreferenceProvider.getString("isRegistered")
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


}
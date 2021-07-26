package com.example.broccoli.Activity

import HelperClass.KonfettiProvider
import HelperClass.SharePreferenceProvider
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import androidx.appcompat.app.AppCompatActivity
import com.example.broccoli.R
import nl.dionsegijn.konfetti.KonfettiView
import nl.dionsegijn.konfetti.models.Shape
import nl.dionsegijn.konfetti.models.Size
import kotlin.concurrent.fixedRateTimer


class SuccessRegister : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.success_registered_temp)
        val sharePreferenceProvider = SharePreferenceProvider(this)
        sharePreferenceProvider.putString("isRegistered","true")


        val viewKonfetti = findViewById<KonfettiView>(R.id.viewKonfetti)


        viewKonfetti.getViewTreeObserver().addOnGlobalLayoutListener(
                object : OnGlobalLayoutListener {
                    override fun onGlobalLayout() {

                        startTimer(viewKonfetti,viewKonfetti.width)

                        viewKonfetti.getViewTreeObserver().removeOnGlobalLayoutListener(this)
                    }
                })

    }

    // starts confetti every 4.5 seconds
    fun startTimer(viewKonfetti : KonfettiView, widthConfetti: Int){
        fixedRateTimer("timer",false,0,4500){
            this@SuccessRegister.runOnUiThread {
                val konfettiProvider = KonfettiProvider(viewKonfetti)
                konfettiProvider.buildKonfetti(0f,0f)
                konfettiProvider.buildKonfetti(widthConfetti + 0f,0f)

            }
        }
    }



    // Success button click
    fun dissmissSuccess(view : View){
        val intent = Intent(this, PostRegisted::class.java)
        startActivity(intent)
    }
}
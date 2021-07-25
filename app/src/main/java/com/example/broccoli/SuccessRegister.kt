package com.example.broccoli

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import androidx.appcompat.app.AppCompatActivity
import nl.dionsegijn.konfetti.KonfettiView
import nl.dionsegijn.konfetti.models.Shape
import nl.dionsegijn.konfetti.models.Size
import kotlin.concurrent.fixedRateTimer


class SuccessRegister : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.success_registered_temp)

        val sharedPreferences: SharedPreferences = this.getSharedPreferences("userDetails",
            Context.MODE_PRIVATE)
        val editor:SharedPreferences.Editor =  sharedPreferences.edit()
        editor.putString("isRegistered","true")
        editor.apply()
        editor.commit()

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
                runConfetti(viewKonfetti,widthConfetti)
            }
        }
    }

    // starts confetti
    fun runConfetti(viewKonfetti : KonfettiView, widthConfetti: Int){

        viewKonfetti.build()
            .addColors(Color.YELLOW, Color.GREEN, Color.MAGENTA,Color.WHITE)
            .setDirection(0.0, 359.0)
            .setSpeed(0f, 5f)
            .setFadeOutEnabled(true)
            .setTimeToLive(100000)
            .addShapes(Shape.Square, Shape.Circle)
            .addSizes(Size(12))
            .setPosition(0f, 0f)
            .burst(150)



        viewKonfetti.build()
            .addColors(Color.YELLOW, Color.GREEN, Color.MAGENTA)
            .setDirection(0.0, 359.0)
            .setSpeed(0f, 5f)
            .setFadeOutEnabled(true)
            .setTimeToLive(100000)
            .addShapes(Shape.Square, Shape.Circle)
            .addSizes(Size(14))
            .setPosition(widthConfetti + 0f, 0f)
            .burst(150)
    }

    // Success button click
    fun dissmissSuccess(view : View){
        val intent = Intent(this,PostRegisted::class.java)
        startActivity(intent)
    }
}
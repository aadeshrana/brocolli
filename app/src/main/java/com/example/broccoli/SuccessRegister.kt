package com.example.broccoli

import android.content.Intent
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
        setContentView(R.layout.success_registered)

        val viewKonfetti = findViewById<KonfettiView>(R.id.viewKonfetti)






        viewKonfetti.getViewTreeObserver().addOnGlobalLayoutListener(
                object : OnGlobalLayoutListener {
                    override fun onGlobalLayout() {

                        startTimer(viewKonfetti,viewKonfetti.width)

                        viewKonfetti.getViewTreeObserver().removeOnGlobalLayoutListener(this)
                    }
                })
//        var loc = IntArray(2)
//        viewKonfetti.getLocationInWindow(loc)
//        Log.e("value","v"+loc[0])
    }

    fun startTimer(viewKonfetti : KonfettiView, widthConfetti: Int){
        fixedRateTimer("timer",false,0,2000){
            this@SuccessRegister.runOnUiThread {
                runConfetti(viewKonfetti,widthConfetti)
            }
        }
    }

    fun runConfetti(viewKonfetti : KonfettiView, widthConfetti: Int){





        viewKonfetti.build()
            .addColors(Color.YELLOW, Color.GREEN, Color.MAGENTA)
            .setDirection(0.0, 359.0)
            .setSpeed(1f, 5f)
            .setFadeOutEnabled(true)
            .setTimeToLive(2000L)
            .addShapes(Shape.Square, Shape.Circle)
            .addSizes(Size(12))
            .setPosition(0f, 0f)
            .burst(200)


        viewKonfetti.build()
            .addColors(Color.YELLOW, Color.GREEN, Color.MAGENTA)
            .setDirection(0.0, 359.0)
            .setSpeed(1f, 5f)
            .setFadeOutEnabled(true)
            .setTimeToLive(5000L)
            .addShapes(Shape.Square, Shape.Circle)
            .addSizes(Size(12))
            .setPosition(widthConfetti + 0f, 0f)
            .burst(200)
    }

    fun dissmissSuccess(view : View){
        val intent = Intent(this,PostRegisted::class.java)
        startActivity(intent)
    }
}
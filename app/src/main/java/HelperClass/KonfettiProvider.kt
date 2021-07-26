package HelperClass

import android.graphics.Color
import nl.dionsegijn.konfetti.KonfettiView
import nl.dionsegijn.konfetti.models.Shape
import nl.dionsegijn.konfetti.models.Size

class KonfettiProvider(viewKonfetti : KonfettiView) {
    private val konfettiView = viewKonfetti

    fun buildKonfetti(positionX : Float, positionY: Float){
        konfettiView.build()
            .addColors(Color.YELLOW, Color.GREEN, Color.MAGENTA, Color.WHITE)
            .setDirection(0.0, 359.0)
            .setSpeed(0f, 5f)
            .setFadeOutEnabled(true)
            .setTimeToLive(100000)
            .addShapes(Shape.Square, Shape.Circle)
            .addSizes(Size(12))
            .setPosition(positionX, positionY)
            .burst(150)
    }
}
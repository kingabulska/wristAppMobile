package pl.kinga.wristapp

import android.graphics.Color
import android.graphics.RectF

sealed class Ellipse(
    val left: Float,
    val right: Float,
    val top: Float,
    val bottom: Float,
    val id: Int,
    val rotation: Float = 0f,
    val color: Int = Color.RED
) {
    fun toRectF(displayWidth: Int, displayHeight: Int): RectF {
        val absLeft = left * displayWidth
        val absTop = top * displayHeight
        val absRight = right * displayWidth
        val absBottom = bottom * displayHeight

        return RectF(
            absLeft,
            absTop,
            absRight,
            absBottom
        )
    }

    class Right(
        left: Float,
        right: Float,
        top: Float,
        bottom: Float,
        id: Int,
        rotation: Float = 0f
    ) : Ellipse(left, right, top, bottom, id, rotation, Color.RED)


    class Left(
        left: Float,
        right: Float,
        top: Float,
        bottom: Float,
        id: Int,
        rotation: Float = 0f
    ) : Ellipse(left, right, top, bottom, id, rotation, Color.GREEN)

}
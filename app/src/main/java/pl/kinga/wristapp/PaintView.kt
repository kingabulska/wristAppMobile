package pl.kinga.wristapp

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.math.abs

private const val TOUCH_TOLERANCE = 4f

class PaintView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    private var mX = 0f
    private var mY = 0f
    private var currentPath: Path? = null

    private val mPaint: Paint = Paint().apply {
        isAntiAlias = true
        isDither = true
        color = Color.RED
        style = Paint.Style.STROKE
        strokeJoin = Paint.Join.ROUND
        strokeCap = Paint.Cap.ROUND
        xfermode = null
        alpha = 0xff
        strokeWidth = 12.0f
    }

    private val pathColor = Color.BLUE

    private val displayMetrics = this.context.resources.displayMetrics

    private var displayWidth: Int = displayMetrics.widthPixels
    private var displayHeight: Int = displayMetrics.heightPixels

    private val mBitmap: Bitmap = Bitmap.createBitmap(
        displayWidth,
        displayHeight,
        Bitmap.Config.ARGB_8888
    )
    private val mCanvas: Canvas = Canvas(mBitmap)

    var onTouchStart: () -> Unit = {}
    var onTouchUp: () -> Unit = {}
    var onNewPoint: (Float, Float) -> Unit = { _, _ -> }

    var currentOval: Ellipse? = null
        set(value) {
            field = value
            this.draw(this.mCanvas)
        }


    init {
        matrix.postRotate(35.0f)
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawColor(Color.WHITE)

        // canvas.drawBitmap(mBitmap, 0f, 0f, mBitmapPaint) // Idk if this is required. Works without it too

        currentOval?.let {
            canvas.save()
            val oval = it.toRectF(displayWidth, displayHeight)
            canvas.rotate(it.rotation, oval.centerX(), oval.centerY())
            mPaint.color = it.color
            canvas.drawOval(oval, mPaint)
            canvas.restore()
        }

        currentPath?.let {
            canvas.save()
            mPaint.color = pathColor
            canvas.drawPath(it, mPaint)
            canvas.restore()
        }

        super.onDraw(canvas)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (currentOval != null) {
            val x = event.x
            val y = event.y
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    touchStart(x, y)
                    invalidate()
                }
                MotionEvent.ACTION_MOVE -> {
                    touchMove(x, y)
                    invalidate()
                }
                MotionEvent.ACTION_UP -> {
                    touchUp()
                    invalidate()
                }
            }
        }

        return true
    }

    private fun touchStart(x: Float, y: Float) {
        onTouchStart()

        val path = Path()
        currentPath = path
        path.reset()
        path.moveTo(x, y)

        mX = x
        mY = y
    }

    private fun touchMove(x: Float, y: Float) {
        onNewPoint(x, y)

        val dx = abs(x - mX)
        val dy = abs(y - mY)

        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            currentPath?.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2)
            mX = x
            mY = y
        }
    }

    private fun touchUp() {
        currentPath?.reset()

        onTouchUp()
    }

}
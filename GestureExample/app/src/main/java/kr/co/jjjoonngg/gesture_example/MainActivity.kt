package kr.co.jjjoonngg.gesture_example

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

    lateinit var swipeUp: ImageView
    private var isExpand = true

    private var expandSize = 0.0f

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        swipeUp = findViewById<ImageView>(R.id.swipeUp)
        expandSize = (300 * resources.displayMetrics.density).roundToInt().toFloat()


        val gestureDetector = GestureDetector(this, object : GestureDetector.OnGestureListener {
            override fun onDown(p0: MotionEvent?): Boolean {
                return true
            }

            override fun onShowPress(p0: MotionEvent?) {
            }

            override fun onSingleTapUp(p0: MotionEvent?): Boolean {
                return true
            }

            override fun onScroll(
                p0: MotionEvent?,
                p1: MotionEvent?,
                p2: Float,
                p3: Float
            ): Boolean {
                return true
            }

            override fun onLongPress(p0: MotionEvent?) {
            }

            override fun onFling(
                p0: MotionEvent?,
                p1: MotionEvent?,
                p2: Float,
                p3: Float
            ): Boolean {
                if (p0?.y?.toInt()!! > p1?.y?.toInt()!!) {
                    if (!isExpand) {
                        swipeUp.expand(expandSize)
                        isExpand = true
                    }
                } else {
                    if (isExpand) {
                        swipeUp.collapse(expandSize)
                        isExpand = false
                    }
                }
                return true
            }
        })

        swipeUp.setOnTouchListener { _, motionEvent ->
            return@setOnTouchListener gestureDetector.onTouchEvent(motionEvent)
        }

    }
}
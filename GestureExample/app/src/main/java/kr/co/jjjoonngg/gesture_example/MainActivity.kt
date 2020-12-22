package kr.co.jjjoonngg.gesture_example

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    lateinit var swipeUp: ImageView

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        swipeUp = findViewById<ImageView>(R.id.swipeUp)


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
                if (p0?.y?.toInt()!! > p1?.y?.toInt()!!) {
                    //TODO : EXPAND
                } else {
                    swipeUp.collapse()
                }
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

                return true
            }
        })

        swipeUp.setOnTouchListener { view, motionEvent ->
            return@setOnTouchListener gestureDetector.onTouchEvent(motionEvent)
        }

    }
}
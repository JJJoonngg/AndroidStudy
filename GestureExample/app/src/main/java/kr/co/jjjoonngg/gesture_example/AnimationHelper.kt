package kr.co.jjjoonngg.gesture_example

import android.animation.ValueAnimator
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import java.lang.Integer.parseInt

/*
* Created by JJJoonngg
*/


fun slideAnimator(view: View, start: Float, end: Float): ValueAnimator {
    return ValueAnimator.ofFloat(start, end).also {
        it.addUpdateListener { valueAnimator ->
            view.layoutParams.apply {
                height = (valueAnimator.animatedValue as Float).toInt()
                view.layoutParams = this
            }
        }
        it.duration = 500L
    }
}

fun View.collapse(size: Float) {
    slideAnimator(this, size, size * 0.2f).also {
        it.start()
    }
}

fun View.expand(size: Float) {
    slideAnimator(this, size * 0.2f, size).also {
        it.start()
    }
}
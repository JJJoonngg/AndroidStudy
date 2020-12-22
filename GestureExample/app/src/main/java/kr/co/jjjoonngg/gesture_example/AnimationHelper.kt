package kr.co.jjjoonngg.gesture_example

import android.animation.ValueAnimator
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import java.lang.Integer.parseInt

/*
* Created by JJJoonngg
*/


fun slideAnimator(view: View, start: Int, end: Int): ValueAnimator {
    return ValueAnimator.ofInt(start, end).also {
        it.addUpdateListener { valueAnimator ->
            view.layoutParams.apply {
                height = valueAnimator.animatedValue as Int
                view.layoutParams = this
            }
        }
        it.duration = 500L
    }
}

fun View.collapse() {
    slideAnimator(this, this.height, 0).also {
        it.start()
    }
}
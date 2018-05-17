package com.example.animationsample

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.animation.AccelerateInterpolator
import android.view.animation.AnticipateInterpolator
import android.view.animation.Interpolator
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        scale()
        moveOnToOff()
        conflict()
    }

    private fun conflict() {
        conflictButton.setOnClickListener {
            val leftX = leftImageView.x
            val rightX = rightImageView.x

            val moveToX = ((rightX - leftX) / 2f)// - (leftImageView.width * 1.05f)
            val leftAnimatorX = ObjectAnimator.ofFloat(leftImageView, "translationX", moveToX)
            leftAnimatorX.interpolator = ConflictBounceInterpolator()
            val leftAnimatorRotation = ObjectAnimator.ofFloat(leftImageView, "rotation", 0f, 360f)
            leftAnimatorRotation.interpolator = AccelerateInterpolator()

            val rightAnimator = ObjectAnimator.ofFloat(rightImageView, "translationX", moveToX * -1)
            rightAnimator.interpolator = ConflictBounceInterpolator()
            val rightAnimatorRotation = ObjectAnimator.ofFloat(rightImageView, "rotation", 0f, 360f)
            rightAnimatorRotation.interpolator = AccelerateInterpolator()

            AnimatorSet().also {
                it.playTogether(leftAnimatorX,
                        leftAnimatorRotation,
                        rightAnimator,
                        rightAnimatorRotation)
                it.duration = 600
            }.start()
        }
    }

    private fun scale() {
        actionButton.setOnClickListener {
            val animatorSet = AnimatorSet()
            animatorSet.playTogether(
                    ObjectAnimator.ofFloat(scaleImageView, "scaleX", 1f, 2f, 2f, 1f),
                    ObjectAnimator.ofFloat(scaleImageView, "scaleY", 1f, 2f, 2f, 1f)
            )
            animatorSet.interpolator = StarInterpolator()
            animatorSet.duration = 500
            animatorSet.start()
        }
    }

    private fun moveOnToOff() {
        actionButton.setOnClickListener {
            val moveToX = star_big_off.x - scaleImageView.x
            val moveToY = star_big_off.y - scaleImageView.y

            val animatorSet = AnimatorSet()
            val xAnimator = ObjectAnimator.ofFloat(scaleImageView, "translationX", moveToX)
            xAnimator.interpolator = AccelerateInterpolator()
            val yAnimator = ObjectAnimator.ofFloat(scaleImageView, "translationY", moveToY)
            yAnimator.interpolator = AnticipateInterpolator()
            animatorSet.playTogether(
                    xAnimator,
                    yAnimator
            )
            animatorSet.duration = 600
            animatorSet.start()

//            scaleImageView.animate()
//                    .translationX(moveToX)
//                    .translationY(moveToY)
//                    .setDuration(2000)
//                    .setInterpolator(StarInterpolator())
//                    .start()
        }
    }
}

class ConflictBounceInterpolator() : Interpolator {
    private fun bounce(t: Float, v: Float): Float {
        return t * t * v
    }

    override fun getInterpolation(v: Float): Float {
        val t = v * 1.1226f
        return when {
            t < 0.7f -> bounce(t, 2f)
            else -> bounce(t - 0.8526f, 2.75f) + 0.8f
//            else -> bounce(t - 1.0435f, 8f) + 0.95f
        }
    }
}

class StarInterpolator : Interpolator {

    private fun bounce(t: Float): Float {
        return t * t * 8.0f
    }

    override fun getInterpolation(v: Float): Float {
        val t = v * 1.1226f
        return when {
            t < 0.3535f -> bounce(t)
            t < 0.7408f -> bounce(t - 0.54719f) + 0.7f
            t < 0.9644f -> bounce(t - 0.8526f) + 0.9f
            else -> bounce(t - 1.0435f) + 0.95f
        }
    }
}
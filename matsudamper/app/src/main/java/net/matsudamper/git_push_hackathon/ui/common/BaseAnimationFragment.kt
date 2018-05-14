package net.matsudamper.git_push_hackathon.ui.common

import android.animation.Animator
import android.animation.ObjectAnimator
import android.app.FragmentTransaction
import android.graphics.Point
import android.support.v4.app.Fragment

abstract class BaseAnimationFragment : Fragment() {

    override fun onCreateAnimator(transit: Int, enter: Boolean, nextAnim: Int): Animator? {

        val display = activity?.windowManager?.defaultDisplay
        if (display != null) {
            val point = Point().also { display.getSize(it) }
            val width = point.x.toFloat()

            if (transit == FragmentTransaction.TRANSIT_FRAGMENT_OPEN) {
                return if (enter) {
                    ObjectAnimator.ofFloat(view, "translationX", width, 0.0f).setDuration(150)
                } else {
                    ObjectAnimator.ofFloat(view, "translationX", 0.0f, -width).setDuration(150)
                }
            } else if (transit == FragmentTransaction.TRANSIT_FRAGMENT_CLOSE) {
                return if (enter) {
                    ObjectAnimator.ofFloat(view, "translationX", -width, 0.0f).setDuration(150)
                } else {
                    ObjectAnimator.ofFloat(view, "translationX", 0.0f, width).setDuration(150)
                }
            }
        }

        return super.onCreateAnimator(transit, enter, nextAnim)
    }
}
package io.github.jiezhi.havebook.utils;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.PathInterpolator;

/**
 * Created by jiezhi on 5/25/16.
 * Function:
 */
public class ViewUtils {
    private static final String TAG = "Utils";
    private static final long COLOR_ANIMATION_DURATION = 1000;
    private static final long DEFAULT_DELAY = 0;

    /**
     *
     * @param view
     * @param startColor
     * @param endColor
     */
    public static void animateViewColor(View view, int startColor, int endColor) {
        ObjectAnimator animator = ObjectAnimator.ofObject(view,  "backgroundColor", new ArgbEvaluator(), startColor, endColor);
        animator.setInterpolator(new PathInterpolator(0.4f, 0f, 1f, 1f));
        animator.setDuration(COLOR_ANIMATION_DURATION);
        animator.start();
    }

    /**
     * Scale and set the pivot when the animation will start from
     * @param view
     */
    public static void configHideYView(View view) {
        if (view != null) {
            view.setScaleY(0);
            view.setPivotY(0);
        }
    }

    public static ViewPropertyAnimator showViewByScale(View v) {
        return v.animate().setStartDelay(DEFAULT_DELAY).scaleX(1).scaleY(1);
    }

    public static ViewPropertyAnimator hideViewByScaleXY(View v) {
        return hideViewByScale(v, DEFAULT_DELAY, 0, 0);
    }

    private static ViewPropertyAnimator hideViewByScale(View v, long defaultDelay, int x, int y) {
        return v.animate().setStartDelay(defaultDelay).scaleX(x).scaleY(y);
    }

    public static ViewPropertyAnimator hideViewByScaleY(View view) {
        return hideViewByScale(view, DEFAULT_DELAY, 1, 0);
    }

}

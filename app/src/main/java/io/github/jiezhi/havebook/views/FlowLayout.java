package io.github.jiezhi.havebook.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiezhi on 6/1/16.
 * Function:
 */
public class FlowLayout extends ViewGroup {
    private static final String TAG = "FlowLayout";

    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);

        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        // wrap_content
        int width = 0;
        int height = 0;

        // every line width and height
        int lineWidth = 0;
        int lineHeight = 0;

        // child view count
        int childCount = getChildCount();
        View childView;
        MarginLayoutParams marginLP;
        int childWidth;
        int childHeight;
        for (int i = 0; i < childCount; i++) {
            childView = getChildAt(i);
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
            marginLP = (MarginLayoutParams) childView.getLayoutParams();
            childWidth = childView.getMeasuredWidth() + marginLP.leftMargin + marginLP.rightMargin;
            childHeight = childView.getMeasuredHeight() + marginLP.topMargin + marginLP.bottomMargin;
            if (lineWidth + childWidth + getPaddingLeft() + getPaddingRight() > sizeWidth) {
                width = Math.max(width, lineWidth);
                lineWidth = childWidth;

                height += lineHeight;
                lineHeight = childHeight;
            } else { // not change line
                lineWidth += childWidth;
                lineHeight = Math.max(lineHeight, childHeight);
            }

            if (i == childCount - 1) {
                width = Math.max(lineWidth, width);
                height += lineHeight;
            }
        }

        Log.d(TAG, "sizeWidth:" + sizeWidth);
        Log.d(TAG, "sizeHeight:" + sizeHeight);
        setMeasuredDimension(modeWidth == MeasureSpec.EXACTLY ? sizeWidth : width + getPaddingLeft() + getPaddingRight(),
                modeHeight == MeasureSpec.EXACTLY ? sizeHeight : height + getPaddingTop() + getPaddingBottom());
    }


    private List<List<View>> allChildViews = new ArrayList<>();
    private List<Integer> linesHeight = new ArrayList<>();

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        allChildViews.clear();
        linesHeight.clear();

        int width = getWidth();
        int lineWidth = 0;
        int lineHeight = 0;

        List<View> lineViews = new ArrayList<>();
        int childCount = getChildCount();
        View child;
        MarginLayoutParams marginLP;
        for (int i = 0; i < childCount; i++) {
            child = getChildAt(i);
            marginLP = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            // if the width of one more child view longer than parent width, change line
            if (childWidth + lineWidth + marginLP.leftMargin + marginLP.rightMargin + getPaddingLeft() + getPaddingRight() > width) {
                linesHeight.add(lineHeight);
                allChildViews.add(lineViews);

                lineViews = new ArrayList<>();
                lineWidth = 0;
                lineHeight = childHeight + marginLP.topMargin + marginLP.bottomMargin;
            }

            lineWidth += childWidth + marginLP.leftMargin + marginLP.rightMargin;
            lineHeight = Math.max(lineHeight, childHeight + marginLP.topMargin + marginLP.bottomMargin);
            lineViews.add(child);
        }

        // add the last line
        linesHeight.add(lineHeight);
        allChildViews.add(lineViews);

        int left = getPaddingLeft();
        int top = getPaddingTop();
        int lineNum = allChildViews.size();
        int lineViewCount;

        for (int i = 0; i < lineNum; i++) {
            lineViews = allChildViews.get(i);
            lineHeight = linesHeight.get(i);
            lineViewCount = lineViews.size();
            for (int j = 0; j < lineViewCount; j++) {
                child = lineViews.get(j);
                if (child.getVisibility() == GONE) continue;
                marginLP = (MarginLayoutParams) child.getLayoutParams();
                int lc = left + marginLP.leftMargin;
                int tc = top + marginLP.topMargin;
                int rc = lc + child.getMeasuredWidth();
                int bc = tc + child.getMeasuredHeight();
                child.layout(lc, tc, rc, bc);
                left += child.getMeasuredWidth() + marginLP.leftMargin + marginLP.rightMargin;
            }
            left = getPaddingLeft();
            top += lineHeight;
        }

    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

}

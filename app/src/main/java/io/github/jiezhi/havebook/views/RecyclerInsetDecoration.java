package io.github.jiezhi.havebook.views;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import io.github.jiezhi.havebook.R;

/**
 * Created by jiezhi on 5/25/16.
 * Function:
 */
public class RecyclerInsetDecoration extends RecyclerView.ItemDecoration {
    private static final String TAG = "RecyclerInsetDecoration";

    private int mInsets;

    public RecyclerInsetDecoration(Context context) {
        mInsets = context.getResources().getDimensionPixelSize(R.dimen.insets);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(mInsets, mInsets, mInsets, mInsets);
    }
}

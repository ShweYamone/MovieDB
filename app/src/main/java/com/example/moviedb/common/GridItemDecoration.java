package com.example.moviedb.common;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GridItemDecoration extends RecyclerView.ItemDecoration {
    private int mGridSpacingPx;
    private int mGridSize;

    private boolean mNeedLeftSpacing = false;

    public GridItemDecoration(int gridSpacingPx, int gridSize) {
        this.mGridSpacingPx = gridSpacingPx;
        this.mGridSize = gridSize;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        final int frameWidth = (int)((parent.getWidth() - (float)mGridSpacingPx * (mGridSize - 1)) / mGridSize);
        final int padding = parent.getWidth() / mGridSize - frameWidth;
        final int itemPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();


        if (itemPosition < mGridSize) {
            outRect.top = 0;
        } else {
            outRect.top = mGridSpacingPx;
        }
        if (itemPosition % mGridSize == 0) {
            outRect.left = 0;
            outRect.right = padding;
            mNeedLeftSpacing = true;
        } else if ((itemPosition + 1) % mGridSize == 0) {
            mNeedLeftSpacing = false;
            outRect.right = 0;
            outRect.left = padding;
        } else if (mNeedLeftSpacing) {
            mNeedLeftSpacing = false;
            outRect.left = mGridSpacingPx - padding;
            if ((itemPosition + 2) % mGridSize == 0) {
                outRect.right = mGridSpacingPx - padding;
            } else {
                outRect.right = mGridSpacingPx / 2;
            }
        } else if ((itemPosition + 2) % mGridSize == 0) {
            mNeedLeftSpacing = false;
            outRect.left = mGridSpacingPx / 2;
            outRect.right = mGridSpacingPx - padding;
        } else {
            mNeedLeftSpacing = false;
            outRect.left = mGridSpacingPx / 2;
            outRect.right = mGridSpacingPx / 2;
        }
        outRect.bottom = 0;

    }
}

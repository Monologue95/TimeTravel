package com.fengchi.TimeTravel.Utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by fc on 2017/7/3.
 */

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int space;
    private int mSpace;

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        outRect.left = space;
        outRect.right = space;
        outRect.bottom = mSpace;
        // Add top margin only for the first item to avoid double space between items
        ///f(parent.getChildPosition(view) == 0)
        outRect.top = mSpace;
    }



    public SpaceItemDecoration(int space, int mSpace) {
        this.space = space;
        this.mSpace=mSpace;
    }

}

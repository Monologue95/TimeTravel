package com.fengchi.TimeTravel.Utils;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;

/**
 * Created by fc on 2017/7/27.
 */

public class mLinearLayout extends LinearLayoutManager {
        private boolean isScrollEnabled = true;

        public mLinearLayout(Context context) {
            super(context);
        }

        public mLinearLayout(Context context, int orientation, boolean reverseLayout) {
            super(context, orientation, reverseLayout);
        }

        public mLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
        }
        /**
         * 是否支持滑动
         * @param flag
         */
        public void setScrollEnabled(boolean flag) {
            this.isScrollEnabled = flag;
        }

        @Override
        public boolean canScrollVertically() {
            //isScrollEnabled：recyclerview是否支持滑动
            //super.canScrollVertically()：是否为竖直方向滚动
            return isScrollEnabled && super.canScrollVertically();
        }
    }


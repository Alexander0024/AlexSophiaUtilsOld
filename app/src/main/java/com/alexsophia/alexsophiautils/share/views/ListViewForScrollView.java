package com.alexsophia.alexsophiautils.share.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.alexsophia.alexsophiautils.share.views.zlistview.widget.ZListView;


/**
 * Created by Administrator on 2016-3-22.
 */
public class ListViewForScrollView extends ZListView {
    public ListViewForScrollView(Context context) {
        super(context);
    }
    public ListViewForScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public ListViewForScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            int expandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, View
                    .MeasureSpec.AT_MOST);
            super.onMeasure(widthMeasureSpec, expandSpec);
    }


}


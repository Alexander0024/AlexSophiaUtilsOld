package com.alexsophia.alexsophiautils.share.views;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

/**
 * 自定义View
 * <p/>
 * Created by Alexander on 2016/7/5.
 */
public class CustomView extends View {

    public CustomView(Context context) {
        this(context, null);
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * View的测量
     *
     * @param widthMeasureSpec  宽度属性
     * @param heightMeasureSpec 高度属性
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    private int measureWidth(int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            /**
             * 指定大小
             */
            result = specSize;
        } else {
            /**
             * Match_parent 或 Wrap_content
             */
            result = 200;
            if (specMode == MeasureSpec.AT_MOST) {
                /**
                 * Wrap_content时，取最小
                 */
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    private int measureHeight(int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            /**
             * 指定大小
             */
            result = specSize;
        } else {
            /**
             * Match_parent 或 Wrap_content
             */
            result = 200;
            if (specMode == MeasureSpec.AT_MOST) {
                /**
                 * Wrap_content时，取最小
                 */
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    /**
     * View的绘制
     *
     * @param canvas Canvas画布
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}

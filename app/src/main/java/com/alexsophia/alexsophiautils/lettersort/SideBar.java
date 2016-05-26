package com.alexsophia.alexsophiautils.lettersort;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.alexsophia.alexsophiautils.R;


/**
 * 选择联系人界面右侧的SideBar
 */
public class SideBar extends View {
    /**
     * 触摸事件
     */
    private OnTouchingLetterChangedListener onTouchingLetterChangedListener;
    /**
     * 需要展示的26个字母
     */
    private String[] mData = {"A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z", "#"};
    private int mChoose = -1;// 选中
    private Paint mPaint = new Paint();

    private TextView mTextDialog;

    public void setTextView(TextView mTextDialog) {
        this.mTextDialog = mTextDialog;
    }

    public SideBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public SideBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SideBar(Context context) {
        super(context);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /**
         * 获取焦点改变背景颜色
         */
        int height = getHeight();// 获取对应高度
        int width = getWidth(); // 获取对应宽度
        int singleHeight = height / mData.length;// 获取每一个字母的高度
        for (int i = 0; i < mData.length; i++) {
            mPaint.setTypeface(Typeface.DEFAULT_BOLD);
            mPaint.setAntiAlias(true);
            mPaint.setTextSize(28);
            mPaint.setColor(getResources().getColor(R.color.bg_color));
            mPaint.setFakeBoldText(false);
            /**
             * 选中的状态
             */
            if (i == mChoose) {
                mPaint.setColor(getResources().getColor(R.color.btn_bg_color));
                mPaint.setFakeBoldText(true);
            }
            /**
             * x坐标等于中间-字符串宽度的一半
             */
            float xPos = width / 2 - mPaint.measureText(mData[i]) / 2;
            /**
             * Y坐标等于高度加上偏移量
             */
            float yPos = singleHeight * i + singleHeight;
            canvas.drawText(mData[i], xPos, yPos, mPaint);
            /**
             * 重置画笔
             */
            mPaint.reset();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        final float y = event.getY();// 点击Y坐标
        final int oldChoose = mChoose;
        final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
        /**
         * 点击y坐标所占总高度的比例*b数组的长度就等于点击b中的个数
         */
        final int c = (int) (y / getHeight() * mData.length);


        switch (action) {
            case MotionEvent.ACTION_UP:
                setBackgroundColor(0x00000000);
                mChoose = -1;//
                invalidate();
                if (mTextDialog != null) {
                    mTextDialog.setVisibility(View.INVISIBLE);
                }
                break;

            default:
                /**
                 * 设置bar背景颜色
                 */
//			setBackgroundResource(R.drawable.sidebar_background);
                if (oldChoose != c) {
                    if (c >= 0 && c < mData.length) {
                        if (listener != null) {
                            listener.onTouchingLetterChanged(mData[c]);
                        }
                        if (mTextDialog != null) {
                            mTextDialog.setText(mData[c]);
                            mTextDialog.setVisibility(View.VISIBLE);
                        }
                        mChoose = c;
                        invalidate();
                    }
                }
                break;
        }
        return true;
    }

    /**
     * 向外公开的方法
     *
     * @param onTouchingLetterChangedListener Sidebar的点击事件
     */
    public void setOnTouchingLetterChangedListener(
            OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
        this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
    }

    /**
     * 点击事件回调接口
     */
    public interface OnTouchingLetterChangedListener {
        void onTouchingLetterChanged(String s);
    }
}
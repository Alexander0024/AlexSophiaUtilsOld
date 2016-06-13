package com.alexsophia.alexsophiautils.share.views;


import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.alexsophia.alexsophiautils.R;
import com.alexsophia.alexsophiautils.utils.LogWrapper;
import com.nineoldandroids.view.ViewHelper;

/**
 * 侧滑菜单
 *
 * @author liuweiping
 */
public class SlidingMenu extends HorizontalScrollView {
    private String TAG = "SlidingMenu";
    private LinearLayout mWapper;
    private ViewGroup mMenu;
    private ViewGroup mContent;
    private int mScreenWidth;
    private int mMenuWidth;
    // menu与右侧的距离 ,dp
    private int mMenuRightPadding = 50;

    private boolean once = false;

    private boolean isOpen = false;

    private Context mContext;

    /**
     * 未使用自定义属性时，调用此方法
     *
     * @param context
     * @param attrs
     */
    public SlidingMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * 当使用了自定义属性时，调用此方法
     *
     * @param context
     * @param attrs
     * @param defStyle
     */
    public SlidingMenu(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        TypedArray arr = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.SlidingMenu, defStyle, 0);
        int n = arr.getIndexCount();
        for (int i = 0; i < n; i++) {
            int arrIndex = arr.getIndex(i);
            if (arrIndex == R.styleable.SlidingMenu_rightpadding) {
                mMenuRightPadding = arr.getDimensionPixelSize(arrIndex,
                        (int) TypedValue.applyDimension(
                                TypedValue.COMPLEX_UNIT_DIP, mMenuRightPadding,
                                context.getResources().getDisplayMetrics()));
            }
        }
        arr.recycle();
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        mScreenWidth = outMetrics.widthPixels;
        // // 把dp转换成px
        // mMenuRightPadding = (int) TypedValue.applyDimension(
        // TypedValue.COMPLEX_UNIT_DIP, mMenuRightPadding, context
        // .getResources().getDisplayMetrics());
    }

    public SlidingMenu(Context context) {
        this(context, null);
    }

    private boolean isNeedMeasure = false;

    /**
     * 设置菜单与右侧的距离
     *
     * @param padding
     */
    public void setMenuRightPadding(int padding) {
        isNeedMeasure = true;
        mMenuRightPadding = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, padding, mContext.getResources()
                        .getDisplayMetrics());
        invalidate();
    }

    /**
     * 设置子View的宽和高 设置自己的宽和高
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!once || isNeedMeasure) {// 防止多次设置子View的宽和高
            mWapper = (LinearLayout) getChildAt(0);
            mMenu = (ViewGroup) mWapper.getChildAt(0);
            mContent = (ViewGroup) mWapper.getChildAt(1);
            mMenuWidth = mMenu.getLayoutParams().width = mScreenWidth
                    - mMenuRightPadding;
            mContent.getLayoutParams().width = mScreenWidth;
            once = true;
            isNeedMeasure = false;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 通过设置偏移量将Menu隐藏
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        LogWrapper.i(TAG, "onLayout, changed: " + changed + "; isOpened: " + isOpened());
        if (changed) {
            this.scrollTo(mMenuWidth, 0);
        } else {
            if (isOpened()) {
                this.scrollTo(0, 0);
            } else {
                this.scrollTo(mMenuWidth, 0);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_UP:
                // 隐藏在左边的宽度
                int scrollX = getScrollX();
                if (scrollX >= mMenuWidth / 2) {
                    // 隐藏菜单
                    this.smoothScrollTo(mMenuWidth, 0);
                    isOpen = false;
                } else {
                    // 显示菜单
                    this.smoothScrollTo(0, 0);
                    isOpen = true;
                }
                /**
                 * 通知菜单事件
                 */
                if (mToggleListener != null) {
                    mToggleListener.toggle(!isOpen);
                }
                return true;
            default:
                break;
        }
        return super.onTouchEvent(ev);
    }

    public interface ToggleListener {
        void toggle(boolean isClosed);
    }

    private ToggleListener mToggleListener;

    public void setToggleListener(ToggleListener toggleListener) {
        this.mToggleListener = toggleListener;
    }

    private AnimationListener mAnimationListener;

    /**
     * 用户通过此接口设置动画效果
     */
    public void setAnimationListener(AnimationListener animationListener) {
        this.mAnimationListener = animationListener;
    }

    public interface AnimationListener {
        void setCustomAnimation(int left, int menuWidth, ViewGroup menu,
                                ViewGroup contentView);
    }

    /**
     * 滚动发生时
     */
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mAnimationListener != null) {
            mAnimationListener.setCustomAnimation(l, mMenuWidth, mMenu, mContent);
            return;
        }
        setAnimation1(l);
    }

    private void setAnimation1(int l) {
        // l用于计算缩放基数
        float scale = l * 1.0f / mMenuWidth;// 1-0
        // 内容区域1.0-0.7缩放效果
        float scaleContent = 0.7f + 0.3f * scale;
        // 菜单缩放0.7-1.0缩放效果
        float scaleMenu = 1 - 0.3f * scale;
        // 菜单透明度0.6-1.0
        float scaleMenuLight = 1 - 0.4f * scale;
        // 调用属性动画，设置TranstaionX, 实现抽屉式菜单
        // 显示的一大半部分菜单,可以通过改变这个值来控制一开始显示的菜单
        float initShow = 0.7f;
        ViewHelper.setTranslationX(mMenu, mMenuWidth * scale * initShow);
        // 内容在缩小
        // 为内容区域缩放动画设置中心点
        ViewHelper.setPivotX(mContent, 0);
        ViewHelper.setPivotY(mContent, mContent.getHeight() / 2);
        ViewHelper.setScaleX(mContent, scaleContent);
        ViewHelper.setScaleY(mContent, scaleContent);

        // 菜单缩放
        ViewHelper.setScaleX(mMenu, scaleMenu);
        ViewHelper.setScaleY(mMenu, scaleMenu);
        ViewHelper.setAlpha(mMenu, scaleMenuLight);
    }

    public boolean isOpened() {
        return isOpen;
    }

    /**
     * 打开菜单
     */
    public void openMenu() {
        if (isOpen)
            return;
        this.smoothScrollTo(0, 0);
        isOpen = true;
        if (mToggleListener != null) {
            mToggleListener.toggle(!isOpen);
        }
    }

    /**
     * 关闭菜单
     */
    public void closeMenu() {
        if (!isOpen)
            return;
        this.smoothScrollTo(mMenuWidth, 0);
        isOpen = false;
        if (mToggleListener != null) {
            mToggleListener.toggle(!isOpen);
        }
    }

    /**
     * 切换菜单
     */
    public void toggle() {
        if (isOpen)
            closeMenu();
        else
            openMenu();
    }
}

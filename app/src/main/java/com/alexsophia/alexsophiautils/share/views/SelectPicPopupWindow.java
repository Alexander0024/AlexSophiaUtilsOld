package com.alexsophia.alexsophiautils.share.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;

import com.alexsophia.alexsophiautils.R;


/**
 * 从底部弹出或滑出选择菜单或窗口
 * Created by Alexander on 2016/4/11.
 */
public class SelectPicPopupWindow extends PopupWindow {
    private Button takePhotoBtn, pickPhotoBtn, cancelBtn;
    private View mMenuView;

    @SuppressLint("InflateParams")
    public SelectPicPopupWindow(Context context, OnClickListener itemsOnClick) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.layout_dialog_select_pic, null);
        takePhotoBtn = (Button) mMenuView.findViewById(R.id.btn_take_pic);
        pickPhotoBtn = (Button) mMenuView.findViewById(R.id.btn_pick_pic);
        cancelBtn = (Button) mMenuView.findViewById(R.id.btn_cancel);
        /**
         * 设置按钮监听
         */
        cancelBtn.setOnClickListener(itemsOnClick);
        pickPhotoBtn.setOnClickListener(itemsOnClick);
        takePhotoBtn.setOnClickListener(itemsOnClick);

        /**
         * 设置SelectPicPopupWindow的View
         */
        this.setContentView(mMenuView);
        /**
         * 设置SelectPicPopupWindow弹出窗体的宽
         */
        this.setWidth(LayoutParams.MATCH_PARENT);
        /**
         * 设置SelectPicPopupWindow弹出窗体的高
         */
        this.setHeight(LayoutParams.WRAP_CONTENT);
        /**
         * 设置SelectPicPopupWindow弹出窗体可点击
         */
        this.setFocusable(true);
        /**
         * 设置SelectPicPopupWindow弹出窗体动画效果
         */
        this.setAnimationStyle(R.style.PopupAnimation);
        /**
         * 实例化一个ColorDrawable颜色为半透明
         */
        ColorDrawable dw = new ColorDrawable(0x80000000);
        /**
         * 设置SelectPicPopupWindow弹出窗体的背景
         */
        this.setBackgroundDrawable(dw);
        /**
         * mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
         */
        mMenuView.setOnTouchListener(new OnTouchListener() {

            @Override
            @SuppressLint("ClickableViewAccessibility")
            public boolean onTouch(View v, MotionEvent event) {

                int height = mMenuView.findViewById(R.id.ll_pop_select_pic).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });

    }
}

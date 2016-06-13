package com.alexsophia.alexsophiautils.utils;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 软键盘控制类
 * Created by Alexander on 2016/3/23.
 */
public class SoftKeyboardUtils {
    /**
     * 设置焦点并弹出软键盘
     *
     * @param editText 需要输入的框体
     */
    public static void showKeyboardOnView(final EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) editText.getContext()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(editText, 0);
            }
        }, 400);
    }

    /**
     * 强制隐藏键盘
     */
    public static void hideSoftKeyboard(EditText editText) {
        InputMethodManager imm = (InputMethodManager) editText.getContext().getSystemService(Context
                .INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    public static boolean isSoftKeyboardOpened(EditText editText) {
        InputMethodManager imm = (InputMethodManager) editText.getContext().getSystemService
                (Context.INPUT_METHOD_SERVICE);
        return imm.isActive();
    }
}

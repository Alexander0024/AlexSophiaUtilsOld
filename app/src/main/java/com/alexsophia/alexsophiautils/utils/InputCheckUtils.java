package com.alexsophia.alexsophiautils.utils;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.alexsophia.alexsophiautils.R;


/**
 * 密码验证及对应位置错误展示的工具类
 * Created by Alexander on 2016/3/15.
 */
public class InputCheckUtils {
    /**
     * 根据传递的密码框及错误ImageView进行密码的验证及错误图片的展示
     * @param edt 密码输入框
     * @param iv 对应的ImageView展示框
     */
    public static boolean inputPasswordGrammarCheck(EditText edt, ImageView iv) {
        showHideIv(edt, iv);
        if (StringUtil.isPassword(edt.getText().toString())) {
            iv.setImageResource(R.mipmap.icon_miniplan_01);
            return true;
        } else {
            iv.setImageResource(R.mipmap.icon_miniplan_11);
            return false;
        }
    }

    /**
     * 根据传递的密码框及错误ImageView进行密码的验证及错误图片的展示
     * @param edt 需要比较的密码输入框
     * @param compare 比较对象的密码输入框
     * @param iv 对应的ImageView展示框
     */
    public static boolean inputPasswordGrammarCheck(EditText edt, EditText compare, ImageView iv) {
        showHideIv(edt, iv);
        if (StringUtil.isPassword(edt.getText().toString()) && isInputTheSame(edt, compare)) {
            iv.setImageResource(R.mipmap.icon_miniplan_01);
            return true;
        } else {
            iv.setImageResource(R.mipmap.icon_miniplan_11);
            return false;
        }
    }

    /**
     * 显示或者隐藏ImageView
     */
    private static void showHideIv(EditText edt, ImageView iv) {
        if (edt.getText().toString().length() != 0) {
            iv.setVisibility(View.VISIBLE);
        } else {
            iv.setVisibility(View.GONE);
        }
    }

    /**
     * 判断两次输入的密码是否一致
     *
     * @return
     */
    private static boolean isInputTheSame(EditText edt, EditText compare) {
        String newPassword = edt.getText().toString();
        String confirmPassword = compare.getText().toString();
        // TODO: INPUT CHECK
        if ("".equals(newPassword)) {
            return false;
        } else {
            return newPassword.equals(confirmPassword);
        }
    }
}

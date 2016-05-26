package com.alexsophia.alexsophiautils.utils;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.WindowManager;

import com.alexsophia.alexsophiautils.R;


/**
 * Created by Administrator on 2016/4/11.
 */
public class DialogUtil {
    private static Dialog mDialog;

    /**
     * 展示dialog
     *
     * @return
     */
    public static void showDialog(Dialog dia) {
        LogWrapper.e("DialogUtil", "Dialog showDialog");
        if (dia != null)
            dia.show();
    }

    /**
     * 取消dialog显示
     *
     * @return
     */
    public static void hiddenDialog(Dialog dia) {
        LogWrapper.e("DialogUtil", "Dialog hiddenDialog");
        if (dia != null && dia.isShowing()) {
            dia.cancel();
            dia = null;
        }
    }

    public static Dialog createWaitingDialog(Context context) {
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);
        return dialog;
    }

    public static Dialog createWaitingDialog(Context context, CharSequence message) {
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage(message);
        dialog.setCancelable(false);
        return dialog;
    }

    public static void showWaitingDialog(Context c) {
        if (mDialog == null) {
            synchronized (DialogUtil.class) {
                if (mDialog == null) {
                    mDialog = createWaitingDialog(c, c.getString(R.string.hint_dialog_waiting));
                    mDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                }
            }
        }
        mDialog.show();
    }

    public static void hiddenWaitDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
            mDialog = null;
        }
    }
}

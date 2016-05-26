package com.alexsophia.alexsophiautils.share.views;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.widget.TextView;

import com.alexsophia.alexsophiautils.R;


/**
 * Loading dialog
 * Created by Alexander on 2016/4/21.
 */
public class LoadingDialog extends Dialog {
    public LoadingDialog(Context context, String strMessage) {
        this(context, R.style.CustomLoadingDialog, strMessage);
    }

    public LoadingDialog(Context context, String strMessage, OnCancelListener onCancelListener) {
        this(context, R.style.CustomLoadingDialog, strMessage, onCancelListener);
    }

    public LoadingDialog(Context context, int theme, String strMessage) {
        this(context, theme, strMessage, null);
    }

    public LoadingDialog(Context context, int theme, String strMessage, OnCancelListener
            onCancelListener) {
        super(context, theme);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        this.getWindow().getAttributes().gravity = Gravity.CENTER;
        this.setCanceledOnTouchOutside(false);
        if (null == onCancelListener) {
            this.setCancelable(false);
        } else {
            this.setCancelable(true);
            this.setOnCancelListener(onCancelListener);
        }
        this.setContentView(R.layout.layout_loading_dialog);
        TextView tvMsg = (TextView) this.findViewById(R.id.tv_loading);
        if (tvMsg != null) {
            tvMsg.setText(strMessage);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (!hasFocus) {
            dismiss();
        }
    }
}
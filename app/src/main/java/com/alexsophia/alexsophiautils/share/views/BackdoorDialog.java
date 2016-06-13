package com.alexsophia.alexsophiautils.share.views;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.alexsophia.alexsophiautils.R;
import com.alexsophia.alexsophiautils.app.SPUserInfo;
import com.alexsophia.alexsophiautils.utils.LogWrapper;


/**
 * 后台对话框的实现
 * Created by Alexander on 2016/5/11.
 */
public class BackdoorDialog extends Dialog {
    private static String TAG = "AdminConsole";

    public BackdoorDialog(Context context) {
        super(context);
    }

    public static class Builder {
        private Context context;
        private String title;
        private String positiveButtonText;
        private String negativeButtonText;
        private OnClickListener positiveButtonClickListener;
        private OnClickListener negativeButtonClickListener;
        private SPUserInfo sp;

        public Builder(Context context) {
            this.context = context;
            this.title = "Admin Console";
            this.positiveButtonText = "OK";
            this.negativeButtonText = "Cancel";
            this.sp = new SPUserInfo(context);
        }

        public Builder setPositiveListener(OnClickListener listener) {
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeListener(OnClickListener listener) {
            this.negativeButtonClickListener = listener;
            return this;
        }

        public BackdoorDialog create() {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context
                    .LAYOUT_INFLATER_SERVICE);

            // instantiate the dialog with the custom Theme
            final BackdoorDialog dialog = new BackdoorDialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            final View layout = inflater.inflate(R.layout.layout_backdoor_dialog, null);
            dialog.addContentView(layout, new FrameLayout.LayoutParams(FrameLayout.LayoutParams
                    .FILL_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT));

            // set the dialog title
            ((TextView) layout.findViewById(R.id.tv_item_title)).setText(title);

            // set the content message
            ((EditText) layout.findViewById(R.id.edtTxt_change_base_url)).setText(sp.getBaseUrl());

            // set the confirm button
            ((TextView) layout.findViewById(R.id.positiveButton)).setText(positiveButtonText);
            if (positiveButtonClickListener != null) {
                (layout.findViewById(R.id.positiveButton)).setOnClickListener(new View
                        .OnClickListener() {
                    public void onClick(View v) {
                        String newBaseUrl = ((EditText) layout.findViewById(R.id
                                .edtTxt_change_base_url)).getText().toString();
                        if (!sp.getBaseUrl().equals(newBaseUrl)) {
                            sp.setBaseUrl(newBaseUrl);
                            LogWrapper.e(TAG, "Set base domain to ' " + newBaseUrl + " '");
                            positiveButtonClickListener.onClick(dialog, DialogInterface
                                    .BUTTON_POSITIVE);
                        } else {
                            negativeButtonClickListener.onClick(dialog, DialogInterface
                                    .BUTTON_NEGATIVE);
                        }
                    }
                });
            }

            // set the cancel button
            ((TextView) layout.findViewById(R.id.negativeButton)).setText(negativeButtonText);
            if (negativeButtonClickListener != null) {
                (layout.findViewById(R.id.negativeButton)).setOnClickListener(new View
                        .OnClickListener() {
                    public void onClick(View v) {
                        negativeButtonClickListener.onClick(dialog, DialogInterface
                                .BUTTON_NEGATIVE);
                    }
                });
            }

            // set the first button to bold
            ((TextView) layout.findViewById(R.id.positiveButton)).getPaint().setFakeBoldText(true);

            dialog.setContentView(layout);
            // 所有弹出框禁止点击外面后自动消失
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            return dialog;
        }
    }
}
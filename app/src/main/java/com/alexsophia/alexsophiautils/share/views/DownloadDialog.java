package com.alexsophia.alexsophiautils.share.views;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alexsophia.alexsophiautils.R;

import java.text.NumberFormat;

/**
 * 版本更新的对话框
 * Created by Alexander on 2016/3/28.
 */
public class DownloadDialog extends AlertDialog {
    private ProgressBar mProgress;
    private TextView mProgressPercent;
    private TextView negativeButton;
    private Handler mViewUpdateHandler;
    private int mMax;
    private boolean mHasStarted;
    private int mProgressVal;
    private NumberFormat mProgressPercentFormat;

    public DownloadDialog(Context context) {
        super(context);
        initFormats();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_download_dialog);
        mProgress = (ProgressBar) findViewById(R.id.progress_download);
        mProgressPercent = (TextView) findViewById(R.id.tv_progress_percentage);
        negativeButton = (TextView) findViewById(R.id.tv_negativeButton);
        mViewUpdateHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                int progress = mProgress.getProgress();
                int max = mProgress.getMax();
                if (mProgressPercentFormat != null) {
                    double percent = (double) progress / (double) max;
                    SpannableString tmp = new SpannableString(mProgressPercentFormat.format
                            (percent));
                    tmp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD),
                            0, tmp.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    mProgressPercent.setText(tmp);
                } else {
                    mProgressPercent.setText("");
                }
            }
        };
        // 禁止点击外侧直接退出
        this.setCancelable(false);
        onProgressChanged();
        if (mMax > 0) {
            setMax(mMax);
        }
        if (mProgressVal >= 0) {
            setProgress(mProgressVal);
        }
    }

    public void setCancelClicked(View.OnClickListener listener) {
        negativeButton.setOnClickListener(listener);
    }

    private void initFormats() {
        mProgressPercentFormat = NumberFormat.getPercentInstance();
        mProgressPercentFormat.setMaximumFractionDigits(0);
    }

    private void onProgressChanged() {
        mViewUpdateHandler.sendEmptyMessage(0);
    }

    public void setMax(int max) {
        if (mProgress != null) {
            mProgress.setMax(max);
            onProgressChanged();
        } else {
            mMax = max;
        }
    }

    public void setProgress(int value) {
        if (mHasStarted) {
            mProgress.setProgress(value);
            onProgressChanged();
        } else {
            mProgressVal = value;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mHasStarted = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        mHasStarted = false;
    }
}

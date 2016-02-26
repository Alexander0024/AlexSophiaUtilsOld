package com.alexsophia.alexsophiautils.activity;

import android.os.Bundle;
import android.view.View;

/**
 * Base Activity with all override function description.
 * Created by Alexander on 2016/2/26.
 */
public abstract class BaseActivity extends RootActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        findViewById();
        initView();
        refreshView();
        setListener();
    }

    public abstract void findViewById();

    public abstract void initView();

    protected void refreshView() {
    }

    public abstract void setListener();

    public View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            clickView(v);
        }
    };

    public void clickView(View v) {
    }
}

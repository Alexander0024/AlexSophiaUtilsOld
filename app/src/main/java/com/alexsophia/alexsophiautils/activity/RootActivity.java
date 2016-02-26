package com.alexsophia.alexsophiautils.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

/**
 * Root activity with activity life cycle prompt.
 * Created by Alexander on 2016/2/26.
 */
public class RootActivity extends Activity {
    private static final String TAG = "RootActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 当活动首次被创建时调用
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate()");
    }

    @Override
    protected void onStart() {
        // 当活动对用户可见时调用
        super.onStart();
        Log.i(TAG, "onStart()");
    }

    @Override
    protected void onResume() {
        // 当活动与用户开始交互时调用
        super.onResume();
        Log.i(TAG, "onResume()");
    }

    @Override
    protected void onPause() {
        // 当当前活动被暂停并恢复以前活动时调用
        super.onPause();
        Log.i(TAG, "onPause()");
    }

    @Override
    protected void onStop() {
        // 当活动不再对用户可见时调用
        super.onStop();
        Log.i(TAG, "onStop()");
    }

    @Override
    protected void onDestroy() {
        // 当活动呗系统销毁（手动或由系统执行以节省内存）前调用
        super.onDestroy();
        Log.i(TAG, "onDestroy()");
    }

    @Override
    protected void onRestart() {
        // 当活动已经停止并要再次启动时调用
        super.onRestart();
        Log.i(TAG, "onRestart()");
    }
}

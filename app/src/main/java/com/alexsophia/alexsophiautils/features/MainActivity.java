package com.alexsophia.alexsophiautils.features;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.alexsophia.alexsophiautils.R;
import com.alexsophia.alexsophiautils.app.SPUserInfo;
import com.alexsophia.alexsophiautils.features.base.executor.impl.ThreadExecutor;
import com.alexsophia.alexsophiautils.features.base.threading.MainThreadImpl;
import com.alexsophia.alexsophiautils.features.base.ui.ActivityCollector;
import com.alexsophia.alexsophiautils.features.base.ui.CommonMainActivity;
import com.alexsophia.alexsophiautils.features.presenters.MainPresenters;
import com.alexsophia.alexsophiautils.features.presenters.impl.MainPresentersImpl;
import com.alexsophia.alexsophiautils.features.user.ui.LoginActivity;
import com.alexsophia.alexsophiautils.share.views.CustomDialog;
import com.alexsophia.alexsophiautils.utils.ToastUtil;

import org.greenrobot.eventbus.Subscribe;

/**
 *
 */
public class MainActivity extends CommonMainActivity implements MainPresenters.View {
    private String TAG = "MainActivity";
    private SPUserInfo sp;
    private MainPresenters mMainPresenters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sp = new SPUserInfo(this);
        mMainPresenters = new MainPresentersImpl(ThreadExecutor.getInstance(), MainThreadImpl
                .getInstance(), this);
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void resumeData() {

    }

    @Override
    protected void stop() {

    }

    @Override
    protected void destroy() {

    }

    /**
     * 切换菜单的打开状态
     */
    public void toggleMenu() {
        super.toggleMenuPanel();
    }

    /**
     * 菜单是否已经打开的标志位
     *
     * @return 菜单是否已经打开
     */
    public boolean isMenuOpened() {
        return super.isMenuAlreadyOpened();
    }

    @Override
    protected int getTab1NoFocusImgRes() {
        return R.mipmap.iconplan_01;
    }

    @Override
    protected int getTab1FocusImgRes() {
        return R.mipmap.iconplan_07;
    }

    @Override
    protected int getTab2NoFocusImgRes() {
        return R.mipmap.iconplan_02;
    }

    @Override
    protected int getTab2FocusImgRes() {
        return R.mipmap.iconplan_08;
    }

    @Override
    protected int getTab3NoFocusImgRes() {
        return R.mipmap.iconplan_03;
    }

    @Override
    protected int getTab3FocusImgRes() {
        return R.mipmap.iconplan_09;
    }

    @Override
    protected String getName() {
        return sp.getRealName();
    }

    @Override
    protected int getIcon() {
        return R.mipmap.icon_miniplan_06;
    }

    /**
     * 更新菜单中新版本提示信息
     */
    @Override
    protected String getVersion() {
        return sp.getHasNewVersion()
                ? getResources().getString(R.string.version_isnt_new)
                : getResources().getString(R.string.version_is_new);
    }

    @Override
    protected void toExit() {
        confirmToExit();
    }

    @Override
    protected void toCheckUpdate() {
    }

    @Override
    protected void toAbout() {
    }


    @Override
    protected void toSetting() {
    }

    @Override
    protected void toMyInfo() {
    }

    @Subscribe
    public void test() {

    }

    /**
     * 页面跳转
     *
     * @param mActivity 当前Activity
     * @param mClass    需要跳转的Activity的class
     */
    private void startActivity(Activity mActivity, Class mClass) {
        Intent intent = new Intent();
        intent.setClass(mActivity, mClass);
        startActivity(intent);
    }


    @Override
    public void onBackPressed() {
        if (isMenuOpened()) {
            toggleMenu();
        } else {
            confirmToExit();
        }
    }

    /**
     * 确认是否退出，back键及菜单退出键
     */
    private void confirmToExit() {
        new CustomDialog.Builder(this)
                .setMessage(getString(R.string.confirm_exist))
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startActivity(MainActivity.this, LoginActivity.class);
                        finish();
                    }
                }).create().show();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showError(String message) {
        ToastUtil.showLong(this, message);
    }

    @Override
    public Context getContext() {
        return this;
    }
}

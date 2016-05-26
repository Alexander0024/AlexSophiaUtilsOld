package com.alexsophia.alexsophiautils.features.base.ui;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alexsophia.alexsophiautils.R;
import com.alexsophia.alexsophiautils.share.views.LoadingDialog;

import butterknife.ButterKnife;

/**
 * Created by liuweiping on 2016-3-8.
 */
public abstract class BaseFragment extends Fragment {

    protected Dialog mProgressDialog;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        //加载数据

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contentView = inflater.inflate(getLayoutRes(),
                container, false);
        ButterKnife.bind(this, contentView);
        createView();
        initData();
        return contentView;
    }

    @Override
    public void onResume() {
        super.onResume();
        resume();
    }

    @Override
    public void onStop() {
        super.onStop();
        stop();
    }

    /**
     * onstop中
     */
    protected abstract void stop();

    /**
     * 布局文件id
     *
     * @return
     */
    protected abstract int getLayoutRes();

    /**
     * onCreateView中调用的方法
     */
    protected abstract void createView();

    /**
     * onResume时调用的方法
     */
    protected abstract void resume();

    /**
     * onDestoryView时调用的方法
     */
    protected abstract  void destroyView();

    /**
     * 加载初始化数据
     */
    protected abstract void initData();


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        destroyView();
        ButterKnife.unbind(this);
    }
    public void showLoadingProgress() {
        showLoadingProgress(R.string.hint_dialog_waiting);
    }

    public void showLoadingProgress(int resID) {
        if (null != mProgressDialog) {
            mProgressDialog.cancel();
        }
        mProgressDialog = new LoadingDialog(getActivity(), getResources().getString(resID));
        mProgressDialog.show();
    }

    public void hideLoadingProgress() {
        if (null != mProgressDialog) {
            mProgressDialog.hide();
        }
    }
}

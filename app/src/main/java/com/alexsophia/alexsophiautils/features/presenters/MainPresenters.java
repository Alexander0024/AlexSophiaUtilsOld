package com.alexsophia.alexsophiautils.features.presenters;

import android.content.Context;

import com.alexsophia.alexsophiautils.features.base.ui.BaseView;


/**
 * 主程序界面的Presenters
 * Created by Alexander on 2016/4/12.
 */
public interface MainPresenters {
    interface View extends BaseView {
        /**
         * 获取当前Activity的Context
         * @return 当前Activity的Context
         */
        Context getContext();
    }
}

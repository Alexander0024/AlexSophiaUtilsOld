package com.alexsophia.alexsophiautils.features.user.presenters;


import android.content.Context;

import com.alexsophia.alexsophiautils.features.base.presenters.BasePresenter;
import com.alexsophia.alexsophiautils.features.base.ui.BaseView;


/**
 * Created by liuweiping on 2016-2-24.
 * 完成UI与逻辑层的转换
 */
public interface LoginPresenters extends BasePresenter {
    /**
     * 定义UI层回调接口
     */
    interface View extends BaseView {
        /**
         * 获取当前Activity的Context
         *
         * @return 当前Activity的Context
         */
        Context getContext();

        /**
         * 获取用户名
         *
         * @return 用户名
         */
        String getName();

        /**
         * 获取密码
         *
         * @return 密码
         */
        String getPassWord();

        /**
         * 登录成功的回调接口
         */
        void loginSuccess();
    }

    /**
     * 定义登录操作ui层调用的各种方法,如登录方法
     */
    void login();
}

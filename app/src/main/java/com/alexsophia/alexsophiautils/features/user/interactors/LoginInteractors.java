package com.alexsophia.alexsophiautils.features.user.interactors;


import android.content.Context;

import com.alexsophia.alexsophiautils.features.base.interactors.Interactor;
import com.alexsophia.alexsophiautils.features.user.repository.LoginBean;


/**
 * Created by liuweiping on 2016-2-24.
 * 用户登录的Interactors
 * 根据功能自定义Interactors，继承自Interactor，并定义相应的回调接口,在回调接口中定义该功能或者action可能出现的完成结果
 */
public interface LoginInteractors extends Interactor {
    /**
     * 接口调用的回调接口
     */
    interface Callback {
        /**
         * 获取当前Activity的Context
         *
         * @return 当前Activity的Context
         */
        Context getContext();

        /**
         * 用户名
         *
         * @return 用户名
         */
        String getName();

        /**
         * 用户输入的密码
         *
         * @return 密码
         */
        String getPassword();

        /**
         * 登录成功
         *
         * @param loginBean 成功的Json对象
         */
        void onLoginSuccess(LoginBean loginBean);

        /**
         * 登录失败
         *
         * @param error 失败信息
         */
        void onLoginFailed(String error);
    }
}

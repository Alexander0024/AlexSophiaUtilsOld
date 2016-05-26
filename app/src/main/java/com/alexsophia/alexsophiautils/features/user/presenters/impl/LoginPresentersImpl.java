package com.alexsophia.alexsophiautils.features.user.presenters.impl;


import android.content.Context;

import com.alexsophia.alexsophiautils.R;
import com.alexsophia.alexsophiautils.app.SPUserInfo;
import com.alexsophia.alexsophiautils.features.base.executor.Executor;
import com.alexsophia.alexsophiautils.features.base.executor.MainThread;
import com.alexsophia.alexsophiautils.features.base.presenters.AbstractPresenter;
import com.alexsophia.alexsophiautils.features.user.interactors.LoginInteractors;
import com.alexsophia.alexsophiautils.features.user.interactors.impl.LoginInteractorsImpl;
import com.alexsophia.alexsophiautils.features.user.presenters.LoginPresenters;
import com.alexsophia.alexsophiautils.features.user.repository.LoginBean;
import com.alexsophia.alexsophiautils.utils.LogWrapper;

/**
 * Created by liuweiping on 2016-2-24.
 * 负责UI层与逻辑层简直的转换，在这层可以调用Interactors
 * 这层还需要传递BaseView的子类进来
 */
public class LoginPresentersImpl extends AbstractPresenter implements LoginPresenters,
        LoginInteractors.Callback {
    private String TAG = "LoginPresentersImpl";
    private LoginPresenters.View mView;
    private LoginInteractors mLoginInteractors;
    private SPUserInfo sp;

    public LoginPresentersImpl(Executor executor, MainThread mainThread, View view, SPUserInfo sp) {
        super(executor, mainThread);
        this.mView = view;
        this.sp = sp;
        //初始化Interactor
        mLoginInteractors = new LoginInteractorsImpl(executor, mainThread, this);
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void onError(String message) {

    }

    /**
     * 登录
     */
    @Override
    public void login() {
        mView.showProgress();
        // 执行interactor
        mLoginInteractors.execute();
    }

    @Override
    public Context getContext() {
        return mView.getContext();
    }

    @Override
    public String getName() {
        return mView.getName();
    }

    @Override
    public String getPassword() {
        return mView.getPassWord();
    }

    /**
     * 登录成功后进行一系列的初始化操作
     *
     * @param loginBean 成功的Json对象
     */
    @Override
    public void onLoginSuccess(LoginBean loginBean) {
        /**
         * 更新用户信息
         */
        updateUserInfo(loginBean);
        mView.hideProgress();
        mView.loginSuccess();
    }

    @Override
    public void onLoginFailed(String error) {
        mView.hideProgress();
        mView.showError(mView.getContext().getString(R.string.login_result_failed) + error);
    }

    /**
     * 登录成功后更新用户的信息
     *
     * @param loginBean 登录后的返回数据
     */
    private void updateUserInfo(LoginBean loginBean) {
        if (null != sp) {
            LogWrapper.i(TAG, "Save && Update user info!");
            sp.setToken(loginBean.getToken());
            sp.setFileServerDomain(loginBean.getDomain());
            if (null != loginBean.getUser()) {
                /**
                 * 如果用户登录信息与SP存储的一致，判定为不是首次登录；否则判定为首次登录；
                 */
                if (loginBean.getUser().getId() == sp.getUserId()) {
                    sp.setIsFirstLogin(false);
                } else {
                    sp.setIsFirstLogin(true);
                }
                /**
                 * 刷新SP所有信息
                 */
                sp.setUserId(loginBean.getUser().getId());
                sp.setAccount(loginBean.getUser().getAccount());
                sp.setRealName(loginBean.getUser().getRealName());
                sp.setIdentityType(loginBean.getUser().getIdentityType());
                sp.setEmailAddress(loginBean.getUser().getEmail());
                sp.setMobileNumber(loginBean.getUser().getMobilePhone());
                sp.setStatus(loginBean.getUser().getStatus());
                sp.setCardType(loginBean.getUser().getCardType());
                sp.setAvatar(loginBean.getUser().getAvatar());
                sp.setCreateAt(loginBean.getUser().getCreateAt());
                if (null != loginBean.getUser().getSchool()) {
                    sp.setSchoolId(loginBean.getUser().getSchool().getId());
                    sp.setSchoolName(loginBean.getUser().getSchool().getSchoolName());
                }
                if (null != loginBean.getUser().getStudent()) {
                    sp.setStudentId(loginBean.getUser().getStudent().getUserId());
                    sp.setStudentRealName(loginBean.getUser().getStudent().getRealName());
                    sp.setStudentAvatar(loginBean.getUser().getStudent().getAvatar());
                    sp.setGradeCode(loginBean.getUser().getStudent().getGradeCode());
                    sp.setGradeName(loginBean.getUser().getStudent().getGradeName());
                    sp.setClassCode(loginBean.getUser().getStudent().getClassCode());
                    sp.setClassName(loginBean.getUser().getStudent().getClassName());
                }
            }
            LogWrapper.i(TAG, "Save && Update user info SUCCESS!");
        } else {
            LogWrapper.e(TAG, "Error: SP == null! Save user info failed!");
        }
    }
}

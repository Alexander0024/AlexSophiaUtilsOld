package com.alexsophia.alexsophiautils.features.user.interactors.impl;


import com.alexsophia.alexsophiautils.features.base.executor.Executor;
import com.alexsophia.alexsophiautils.features.base.executor.MainThread;
import com.alexsophia.alexsophiautils.features.base.interactors.AbstractInteractor;
import com.alexsophia.alexsophiautils.features.user.interactors.LoginInteractors;

/**
 * Created by liuweiping on 2016-2-24.
 * 该类为登陆Interactors的具体实现类,实现了LoginInteractors接口
 */
public class LoginInteractorsImpl extends AbstractInteractor implements LoginInteractors {
    private String TAG = "LoginInteractorsImpl";
    private LoginInteractors.Callback mCallback;

    public LoginInteractorsImpl(Executor threadExecutor, MainThread mainThread, LoginInteractors
            .Callback callback) {
        super(threadExecutor, mainThread);
        this.mCallback = callback;
    }

    /**
     * 在这里执行登录的相关逻辑，过程在线程中执行
     * login参数：
     * username：账号
     * password：密码
     * client_type：客户端类型
     * client_version：客户端版本
     */
    @Override
    public void run() {
//        ApiManager.login(mCallback.getName(), mCallback.getPassword())
//                .subscribeOn(Schedulers.immediate())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<LoginBean>() {
//                    @Override
//                    public void call(LoginBean loginBean) {
//                        if (null == loginBean) {
//                            mCallback.onLoginFailed(mCallback.getContext().getString(R.string
//                                    .common_error_system));
//                        } else if (!StringUtil.isEmpty(loginBean.getDomain())
//                                && !StringUtil.isEmpty(loginBean.getToken())
//                                && null != loginBean.getUser()
//                                && 0 != loginBean.getUser().getId()
//                                && !StringUtil.isEmpty(loginBean.getUser().getAccount())
//                                && null != loginBean.getUser().getStudent()
//                                && null != loginBean.getUser().getSchool()) {
//                            mCallback.onLoginSuccess(loginBean);
//                        } else if (!StringUtil.isEmpty(loginBean.getError_code())) {
//                            mCallback.onLoginFailed(loginBean.getError());
//                        } else {
//                            mCallback.onLoginFailed(mCallback.getContext().getString(R.string
//                                    .common_error_unknown));
//                        }
//                    }
//                }, new Action1<Throwable>() {
//                    @Override
//                    public void call(Throwable throwable) {
//                        LogWrapper.e(TAG, throwable.getMessage());
//                        mCallback.onLoginFailed(mCallback.getContext().getString(R.string
//                                .common_error_net_error));
//                    }
//                });

//        Call<LoginBean> call = ApiManager.login2(mCallback.getName(), mCallback.getPassword());
//        call.enqueue(new retrofit2.Callback<LoginBean>() {
//            @Override
//            public void onResponse(Response<LoginBean> response) {
//                LogWrapper.e("test", "Token" + response.body() == null ? "null" : response.body()
//                        .getToken());
//            }
//
//            @Override
//            public void onFailure(Throwable throwable) {
//                throwable.printStackTrace();
//            }
//        });
    }
}

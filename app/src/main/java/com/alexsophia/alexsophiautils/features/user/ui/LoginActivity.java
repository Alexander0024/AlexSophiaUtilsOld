package com.alexsophia.alexsophiautils.features.user.ui;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alexsophia.alexsophiautils.R;
import com.alexsophia.alexsophiautils.app.SPUserInfo;
import com.alexsophia.alexsophiautils.features.MainActivity;
import com.alexsophia.alexsophiautils.features.base.executor.impl.ThreadExecutor;
import com.alexsophia.alexsophiautils.features.base.threading.MainThreadImpl;
import com.alexsophia.alexsophiautils.features.base.ui.BaseActivity;
import com.alexsophia.alexsophiautils.features.user.presenters.LoginPresenters;
import com.alexsophia.alexsophiautils.features.user.presenters.impl.LoginPresentersImpl;
import com.alexsophia.alexsophiautils.share.views.BackdoorDialog;
import com.alexsophia.alexsophiautils.share.views.CustomDialog;
import com.alexsophia.alexsophiautils.share.views.RoundedImageView;
import com.alexsophia.alexsophiautils.utils.Constant;
import com.alexsophia.alexsophiautils.utils.ImgUtils;
import com.alexsophia.alexsophiautils.utils.LogWrapper;
import com.alexsophia.alexsophiautils.utils.SoftKeyboardUtils;
import com.alexsophia.alexsophiautils.utils.StringUtil;
import com.alexsophia.alexsophiautils.utils.ToastUtil;

import butterknife.Bind;

/**
 * 登录主界面
 * Created by Alexander on 2016/3/9.
 */
public class LoginActivity extends BaseActivity implements LoginPresenters.View, View
        .OnClickListener {
    private String TAG = "LoginActivity";
    @Bind(R.id.tv_title)
    TextView mTitleTv; // 标题
    @Bind(R.id.iv_avatar)
    RoundedImageView mAvatarRiv; // 头像
    @Bind(R.id.edtTxt_id)
    EditText mIdEdtTxt; // 账号输入框
    @Bind(R.id.iv_dropdown)
    ImageView mDropdownIv; // 账号下拉选择框，未实现：3.1第一版无此功能
    @Bind(R.id.edtTxt_password)
    EditText mPasswordEdtTxt; // 密码输入框
    @Bind(R.id.btn_login)
    Button mLoginBtn; // 登陆按钮
    @Bind(R.id.chk_accept_licence)
    CheckBox mAcceptLicenceChk; // 接受许可协议
    @Bind(R.id.tv_accept_licence)
    TextView mAcceptLicenceTv; // 许可协议接受提示文字
    @Bind(R.id.tv_licence)
    TextView mLicenceTv; // 许可协议
    @Bind(R.id.tv_forgot_password)
    TextView mForgotPasswordTv; // 忘记密码

    private LoginPresenters mLoginPresenters; // 登录相关方法
    private SPUserInfo sp; // SharePreference信息

    @Override
    protected void stop() {

    }

    @Override
    protected void destroy() {

    }

    @Override
    protected int getContentViewRes() {
        return R.layout.activity_login;
    }

    @Override
    protected Activity getTarget() {
        return this;
    }

    @Override
    protected void loadData() {
        /**
         * 判断是否需要登录，如果不需要登录则直接跳转到MainActivity
         */
        if (Constant.NEED_LOGIN) {
            /**
             * 需要登录，初始化页面展示
             */
            LogWrapper.i(TAG, "Need login！");
            sp = new SPUserInfo(this);
            /**
             * 初始化Presenter
             */
            mLoginPresenters = new LoginPresentersImpl(ThreadExecutor.getInstance(),
                    MainThreadImpl.getInstance(), this, sp);
            mTitleTv.setText(getString(R.string.login));
            mIdEdtTxt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_NEXT) {
                        /**
                         * 定位输入框到输入密码栏
                         */
                        SoftKeyboardUtils.showKeyboardOnView(mPasswordEdtTxt);
                        return true;
                    }
                    return false;
                }
            });
            /**
             * 根据是否为同一用户进行头像显示的判断
             */
            mIdEdtTxt.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    /**
                     * 如果是同一用户，展示其头像；
                     * 非同一用户，展示默认头像。
                     */
                    if (isTheSameUser()) {
                        ImgUtils.displayImage(sp.getAvatar(), mAvatarRiv, R.mipmap
                                .public_imges_128px, R.mipmap.public_imges_128px);
                    } else {
                        mAvatarRiv.setImageResource(R.mipmap.public_imges_128px);
                    }
                }
            });
            mLoginBtn.setOnClickListener(this);
            mLicenceTv.setOnClickListener(this);
            mForgotPasswordTv.setOnClickListener(this);
            mAcceptLicenceChk.setOnCheckedChangeListener(new CompoundButton
                    .OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    /**
                     * 点击checkbox时保存用户对于“用户许可协议”的选中状态
                     */
                    sp.setIsLicenceChecked(isChecked);
                }
            });
            mAcceptLicenceTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isChecked = mAcceptLicenceChk.isChecked();
                    mAcceptLicenceChk.setChecked(!isChecked);
                    sp.setIsLicenceChecked(!isChecked);
                }
            });
            /**
             * 载入用户信息
             */
            loadUserInfo();
        } else {
            /**
             * 不需要登陆，退出Login界面并直接跳转到主界面
             */
            LogWrapper.i(TAG, "No need login！");
            toMainActivity();
        }
    }

    /**
     * 载入用户的头像、ID、Licence选中状态等页面信息
     */
    private void loadUserInfo() {
        if (null != sp && !StringUtil.isEmpty(sp.getAccount())) {
            /**
             * 已存在SP及存储的用户信息，载入
             */
            LogWrapper.e(TAG, "Login load UserInfo: account: " + sp.getAccount() + "; avatar: " + sp
                    .getAvatar() + "; is licence checked: " + sp.getIsLicenceChecked());
            ImgUtils.displayImage(sp.getAvatar(), mAvatarRiv, R.mipmap
                    .public_imges_128px, R.mipmap.public_imges_128px);
            mIdEdtTxt.setText(sp.getAccount());
            mAcceptLicenceChk.setChecked(sp.getIsLicenceChecked());
            /**
             * 默认保存用户名及Licence选中状态，留密码输入框每次由用户输入信息
             */
            /**
             * 定位输入框到输入密码栏
             */
            SoftKeyboardUtils.showKeyboardOnView(mPasswordEdtTxt);
        } else {
            LogWrapper.e(TAG, "New user, no sp info found!");
            /**
             * 定位输入框到输入账号栏
             */
            SoftKeyboardUtils.showKeyboardOnView(mIdEdtTxt);
        }
    }

    @Override
    protected void resumeData() {

    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public String getName() {
        return mIdEdtTxt.getText().toString().trim();
    }

    @Override
    public String getPassWord() {
        return mPasswordEdtTxt.getText().toString();
    }

    /**
     * 登录验证成功
     */
    @Override
    public void loginSuccess() {
        if (isActivityStillAlive()) {
            LogWrapper.i(TAG, "Login Success!");
            toMainActivity();
        }
    }

    @Override
    public void showProgress() {
        showLoadingProgress();
    }

    @Override
    public void hideProgress() {
        hideLoadingProgress();
    }

    @Override
    public void showError(String message) {
        if (isActivityStillAlive()) {
            LogWrapper.e(TAG, message);
            new CustomDialog.Builder(this)
                    .setMessage(message)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).create().show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /**
             * 登录
             */
            case R.id.btn_login:
                if (StringUtil.isEmpty(getName())) {
                    ToastUtil.showLong(this, getString(R.string.login_error_input_id));
                } else if (StringUtil.isEmpty(getPassWord())) {
                    ToastUtil.showLong(this, getString(R.string.login_error_input_password));
                } else if (!mAcceptLicenceChk.isChecked()) {
                    ToastUtil.showLong(this, getString(R.string.login_error_select_licence));
                    /**
                     * 隐藏软键盘以避免软键盘遮挡用户许可协议界面
                     */
                    SoftKeyboardUtils.hideSoftKeyboard(mPasswordEdtTxt);
                } else if (StringUtil.isContainsEmoji(getName())
                        || StringUtil.isContainsEmoji(getPassWord())) {
                    ToastUtil.showLong(this, getString(R.string.emoji_find));
                } else if (!StringUtil.isPassword(getPassWord())) {
                    ToastUtil.showLong(this, getString(R.string.findPSW_error_password_style));
                } else if (isReadyForBackDoor()) {
                    /**
                     * 进入后门
                     */
                    new BackdoorDialog.Builder(this)
                            .setNegativeListener(new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mIdEdtTxt.setText(sp.getAccount());
                                    mPasswordEdtTxt.setText("");
                                    dialog.dismiss();
                                }
                            })
                            .setPositiveListener(new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    System.exit(0);
                                }
                            }).create().show();
                } else {
                    mLoginPresenters.login();
                    /**
                     * 隐藏软键盘登录
                     */
                    SoftKeyboardUtils.hideSoftKeyboard(mPasswordEdtTxt);
                }
                break;
            /**
             * 打开用户许可协议页面
             */
            case R.id.tv_licence:
                break;
            /**
             * 打开忘记密码页面
             */
            case R.id.tv_forgot_password:
                break;
            default:
                break;
        }
    }

    /**
     * 判断是否为后门模式
     *
     * @return true：进入后门；false：正常登录
     */
    private boolean isReadyForBackDoor() {
        return Constant.BACKDOOR_NAME.equals(getName())
                && Constant.BACKDOOR_PSW.equals(getPassWord());
    }

    /**
     * 登录成功或者无需登陆，跳转至主界面并结束自身
     */
    private void toMainActivity() {
        ToastUtil.showLong(this, getString(R.string.login_result_success));
        startActivity(LoginActivity.this, MainActivity.class);
        finish();
    }

    /**
     * 判断是否为上次登录的用户，用于头像显示
     *
     * @return true：和上次登录为同一用户；false：非同一用户。
     */
    private boolean isTheSameUser() {
        String name = getName();
        if (StringUtil.isEmpty(name)
                || StringUtil.isEmpty(sp.getAccount())) {
            return false;
        } else if (name.equals(sp.getAccount())
                || name.equals(sp.getMobileNumber())) {
            return true;
        } else {
            return false;
        }
    }
}

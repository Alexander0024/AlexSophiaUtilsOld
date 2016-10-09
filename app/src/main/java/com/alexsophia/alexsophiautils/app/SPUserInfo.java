package com.alexsophia.alexsophiautils.app;


import android.content.Context;

import com.alexsophia.alexsophiautils.utils.Constant;
import com.alexsophia.alexsophiautils.utils.StringUtil;
import com.alexsophia.alexsophiautils.utils.sharedpreferences.SharedPreferencesUtil;


/**
 * UserInfo SharedPreferences文件工具
 */
public class SPUserInfo extends SharedPreferencesUtil {
    private final static String TAG = "SPUserInfo";

    /**
     * 当前SharedPreference版本
     */
    private final static int CUR_SP_VERSION = 1;
    /**
     * 当前名称
     */
    private final static String NAME = "LR_PARENT_USER_INFO";

    // 系统标志位信息类
    /**
     * 该用户是否为第一次登录软件
     */
    private final static String IS_FIRST_LOGIN = "IS_FIRST_LOGIN";
    /**
     * 该用户是否选中用户许可协议
     */
    private final static String IS_LICENCE_CHECKED = "IS_LICENCE_CHECKED";
    /**
     * 是否仅使用WiFi下载的开关
     */
    private final static String IS_WIFI_DOWNLOAD_ONLY = "IS_WIFI_DOWNLOAD_ONLY";
    /**
     * 是否有新版本，用于菜单页面展示
     */
    private final static String HAS_NEW_VERSION = "HAS_NEW_VERSION";

    // 服务器信息
    /**
     * SDK服务器域名
     */
    private final static String BASE_URL = "BASE_URL";
    /**
     * 文件服务器域名
     */
    private final static String FILE_SERVER_DOMAIN = "FILE_SERVER_DOMAIN";

    // 系统信息
    private final static String OFFICIAL_WEB_SITE = "OFFICIAL_WEB_SITE";
    private final static String OFFICIAL_WECHAT = "OFFICIAL_WECHAT";
    private final static String OFFICIAL_PHONE = "OFFICIAL_PHONE";

    // 用户信息
    /**
     * 用户Token
     */
    private final static String TOKEN = "TOKEN";
    /**
     * 用户ID
     */
    private final static String USER_ID = "USER_ID";
    /**
     * 用户账号
     */
    private final static String ACCOUNT = "ACCOUNT";
    /**
     * 用户真实姓名
     */
    private final static String REAL_NAME = "REAL_NAME";
    /**
     * 账号类别
     */
    private final static String IDENTITY_TYPE = "IDENTITY_TYPE";
    /**
     * 电子邮箱地址
     */
    private final static String EMAIL_ADDRESS = "EMAIL_ADDRESS";
    /**
     * 手机号码
     */
    private final static String MOBILE_NUMBER = "MOBILE_NUMBER";
    /**
     * 账号状态
     */
    private final static String STATUS = "STATUS";
    /**
     * 证件类型
     */
    private final static String CARD_TYPE = "CARD_TYPE";
    /**
     * 账号信息
     */
    private final static String CARD_NUMBER = "CARD_NUMBER";
    /**
     * 用户头像
     */
    private final static String AVATAR = "AVATAR";
    /**
     * 用户账号创建时间
     */
    private final static String CREATE_AT = "CREATE_AT";

    // 学生信息
    /**
     * 学校ID
     */
    private final static String SCHOOL_ID = "SCHOOL_ID";
    /**
     * 学校名称
     */
    private final static String SCHOOL_NAME = "SCHOOL_NAME";
    /**
     * 班级ID
     */
    private final static String CLASS_CODE = "CLASS_CODE";
    /**
     * 年级ID
     */
    private final static String GRADE_CODE = "GRADE_CODE";
    /**
     * 班级名称
     */
    private final static String CLASS_NAME = "CLASS_NAME";
    /**
     * 年级名称
     */
    private final static String GRADE_NAME = "GRADE_NAME";
    /**
     * 学生用户ID
     */
    private final static String STU_ID = "STU_ID";
    /**
     * 学生真实姓名
     */
    private final static String STU_REAL_NAME = "STU_REAL_NAME";
    /**
     * 学生头像
     */
    private final static String STU_AVATAR = "STU_AVATAR";


    public SPUserInfo(Context context) {
        super(context, NAME);// 屏蔽第三方APK读取权限
    }

    public boolean getIsFirstLogin() {
        return getBoolean(IS_FIRST_LOGIN, true);
    }

    public boolean setIsFirstLogin(boolean value) {
        return putBoolean(IS_FIRST_LOGIN, value);
    }

    public boolean getIsLicenceChecked() {
        return getBoolean(IS_LICENCE_CHECKED, false);
    }

    public boolean setIsLicenceChecked(boolean value) {
        return putBoolean(IS_LICENCE_CHECKED, value);
    }

    public String getToken() {
        return decrypt(getString(TOKEN));
    }

    public boolean setToken(String value) {
        return putString(TOKEN, encrypt(value));
    }

    public String getBaseUrl() {
        return getString(BASE_URL, Constant.API_BASE_URL);
    }

    public boolean setBaseUrl(String value) {
        return putString(BASE_URL, value);
    }

    public String getFileServerDomain() {
        return getString(FILE_SERVER_DOMAIN);
    }

    public boolean setFileServerDomain(String value) {
        return putString(FILE_SERVER_DOMAIN, value);
    }

    public String getOfficialWebSite() {
        return getString(OFFICIAL_WEB_SITE);
    }

    public boolean setOfficialWebSite(String value) {
        return putString(OFFICIAL_WEB_SITE, value);
    }

    public String getOfficialWechat() {
        return getString(OFFICIAL_WECHAT);
    }

    public boolean setOfficialWechat(String value) {
        return putString(OFFICIAL_WECHAT, value);
    }

    public String getOfficialPhone() {
        return getString(OFFICIAL_PHONE);
    }

    public boolean setOfficialPhone(String value) {
        return putString(OFFICIAL_PHONE, value);
    }

    public String getRealName() {
        return getString(REAL_NAME);
    }

    public String getAccount() {
        return getString(ACCOUNT);
    }

    public long getClassCode() {
        return getLong(CLASS_CODE);
    }

    public boolean setClassCode(long value) {
        return putLong(CLASS_CODE, value);
    }

    public long getGradeCode() {
        return getLong(GRADE_CODE);
    }

    public boolean setGradeCode(long value) {
        return putLong(GRADE_CODE, value);
    }

    public String getClassName() {
        return getString(CLASS_NAME);
    }

    public boolean setClassName(String name) {
        return putString(CLASS_NAME, name);
    }

    public String getGradeName() {
        return getString(GRADE_NAME);
    }

    public boolean setGradeName(String name) {
        return putString(GRADE_NAME, name);
    }

    public boolean setAccount(String value) {
        return putString(ACCOUNT, value);
    }

    public long getUserId() {
        return getLong(USER_ID);
    }

    public boolean setUserId(long value) {
        return putLong(USER_ID, value);
    }

    public String getEmailAddress() {
        return getString(EMAIL_ADDRESS);
    }

    public boolean setEmailAddress(String value) {
        return putString(EMAIL_ADDRESS, value);
    }

    public String getMobileNumber() {
        return getString(MOBILE_NUMBER);
    }

    public boolean setMobileNumber(String value) {
        return putString(MOBILE_NUMBER, value);
    }

    public int getStatus() {
        return getInt(STATUS);
    }

    public boolean setStatus(int value) {
        return putInt(STATUS, value);
    }

    public int getCardType() {
        return getInt(CARD_TYPE);
    }

    public boolean setCardType(int value) {
        return putInt(CARD_TYPE, value);
    }

    public Long getCardNumber() {
        return getLong(CARD_NUMBER);
    }

    public boolean setCardNumber(long value) {
        return putLong(CARD_NUMBER, value);
    }

    /**
     * 获取完整的头像地址
     *
     * @return getFileServerDomain() + getString(AVATAR);
     */
    public String getAvatar() {
        if (StringUtil.isEmpty(getString(AVATAR))) {
            return "";
        } else {
            return getFileServerDomain() + getString(AVATAR);
        }
    }

    /**
     * 设置的时候设置相对地址即可
     *
     * @param value 头像的相对地址
     * @return 是否设置成功
     */
    public boolean setAvatar(String value) {
        return putString(AVATAR, value);
    }

    public Long getCreateAt() {
        return getLong(CREATE_AT);
    }

    public boolean setCreateAt(long value) {
        return putLong(CREATE_AT, value);
    }

    public long getSchoolId() {
        return getLong(SCHOOL_ID);
    }

    public boolean setSchoolId(long value) {
        return putLong(SCHOOL_ID, value);
    }

    public String getSchoolName() {
        return getString(SCHOOL_NAME);
    }

    public boolean setSchoolName(String value) {
        return putString(SCHOOL_NAME, value);
    }

    /**
     * 获取完整的学生头像地址
     *
     * @return getFileServerDomain() + getString(STU_AVATAR);
     */
    public String getStudentAvatar() {
        if (StringUtil.isEmpty(getString(STU_AVATAR))) {
            return "";
        } else {
            return getFileServerDomain() + getString(STU_AVATAR);
        }
    }

    /**
     * 设置学生的头像
     *
     * @param value 头像的相对地址
     * @return 是否设置成功
     */
    public boolean setStudentAvatar(String value) {
        return putString(STU_AVATAR, value);
    }

    public boolean setRealName(String value) {
        return putString(REAL_NAME, value);
    }

    public boolean setIdentityType(int value) {
        return putInt(IDENTITY_TYPE, value);
    }

    public int getIdentityType() {
        return getInt(IDENTITY_TYPE);
    }

    public long getStudentId() {
        return getLong(STU_ID);
    }

    public boolean setStudentId(long value) {
        return putLong(STU_ID, value);
    }

    public String getStudentRealName() {
        return getString(STU_REAL_NAME);
    }

    public boolean setStudentRealName(String name) {
        return putString(STU_REAL_NAME, name);
    }

    public boolean setIsWIFIDownloadOnly(boolean value) {
        return putBoolean(IS_WIFI_DOWNLOAD_ONLY, value);
    }

    public boolean getIsWIFIDownloadOnly() {
        return getBoolean(IS_WIFI_DOWNLOAD_ONLY);
    }

    public boolean setHasNewVersion(boolean value) {
        return putBoolean(HAS_NEW_VERSION, value);
    }

    public boolean getHasNewVersion() {
        return getBoolean(HAS_NEW_VERSION);
    }

    /**
     * 字符串加密
     */
    public String encrypt(String s) {
        return s;
    }

    /**
     * 字符串解密
     */
    public String decrypt(String s) {
        return s;
    }

    /**
     * 获取SharedPreferences当前版本
     */
    @Override
    public int getCurSpVersion() {
        return CUR_SP_VERSION;
    }
}

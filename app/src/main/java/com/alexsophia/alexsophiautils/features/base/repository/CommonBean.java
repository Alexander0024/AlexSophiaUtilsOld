package com.alexsophia.alexsophiautils.features.base.repository;

/**
 * 公共基础的Bean，用于所有仅包含result和message的接口返回解析
 * Created by Alexander on 2016/3/16.
 */
public class CommonBean extends ErrorBean {
    private boolean result; // 返回成功失败的标识
    private String message; // 返回的错误信息

    public boolean getResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

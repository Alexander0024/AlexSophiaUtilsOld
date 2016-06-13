package com.alexsophia.alexsophiautils.features.base.repository;

import java.io.Serializable;

/**
 * 老接口Error解析
 * Created by Alexander on 2016-3-17.
 */
public abstract class ErrorBean implements Serializable{
    private String error_code;
    private String error;

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}

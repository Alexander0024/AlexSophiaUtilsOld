package com.alexsophia.alexsophiautils.features.base.ui;

/**
 * <p>
 * This interface represents a basic view. All views should implement these common methods.
 * </p>
 */
public interface BaseView {

    /**
     * 处理逻辑时或加载网络数据时，显示对话框等
     */
    void showProgress();

    /**
     * 处理完成后，隐藏对话框等
     */
    void hideProgress();

    /**
     *显示错误信息
     */
    void showError(String message);
}

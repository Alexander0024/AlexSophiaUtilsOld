package com.alexsophia.alexsophiautils.features.presenters.impl;

import com.alexsophia.alexsophiautils.features.base.executor.Executor;
import com.alexsophia.alexsophiautils.features.base.executor.MainThread;
import com.alexsophia.alexsophiautils.features.base.presenters.AbstractPresenter;
import com.alexsophia.alexsophiautils.features.presenters.MainPresenters;


/**
 * MainActivity主界面的Presenter的实现类
 *
 * Created by Alexander on 2016/4/12.
 */
public class MainPresentersImpl extends AbstractPresenter implements MainPresenters {
    private String TAG = "MainPresentersImpl";
    private MainPresenters.View mView;

    public MainPresentersImpl(Executor executor, MainThread mainThread, MainPresenters.View view) {
        super(executor, mainThread);
        this.mView = view;
    }

}

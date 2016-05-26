package com.alexsophia.alexsophiautils.app;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.alexsophia.alexsophiautils.database.DbHelper;
import com.alexsophia.alexsophiautils.network.ApiManager;
import com.alexsophia.alexsophiautils.utils.CommonDirectory;
import com.alexsophia.alexsophiautils.utils.CrashHandler;
import com.alexsophia.alexsophiautils.utils.ImgUtils;
import com.zhy.autolayout.config.AutoLayoutConifg;

import greendao.DaoMaster;
import greendao.DaoSession;


/**
 * Created by liuweiping on 2016-2-24.
 */
public class MyApplication extends Application {
    private static MyApplication myApplication;

    public MyApplication() {
        myApplication = MyApplication.this;
    }

    public static MyApplication getInstance() {
        return myApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initCrashHandler();
        setupDatabase();
        //有缓存，没有网络的时候拉取缓存数据，有网拉最新数据
        ApiManager.init(new SPUserInfo(this).getBaseUrl(), getApplicationContext(), true);
        ImgUtils.initImageLoader(getApplicationContext());
        AutoLayoutConifg.getInstance().useDeviceSize();
        // 初始化文件存储的地址
        CommonDirectory.initFileDirectory();
    }

    private void setupDatabase() {
//         通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
//         可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
//         注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
//         所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        DaoMaster.DevOpenHelper helper = new DbHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
//         注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        DaoMaster daoMaster = new DaoMaster(db);
        mDaoSession = daoMaster.newSession();
    }

    private DaoSession mDaoSession;

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    /**
     * 初始化崩溃日志
     */
    public void initCrashHandler() {
        // 初始化CrashHandler
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());
    }
}

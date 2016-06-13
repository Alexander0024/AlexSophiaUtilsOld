package com.alexsophia.alexsophiautils.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import greendao.DaoMaster;


/**
 * Created by liuweiping on 2016-2-26.
 */
public class DbHelper extends DaoMaster.DevOpenHelper {
    public static final String DB_NAME = "LRHOMETOSCHOOL.db";
    private boolean mDropAllTable;

    public DbHelper(Context context) {
        super(context, DB_NAME, null);
        this.mDropAllTable = false;
    }

    public DbHelper(Context context, boolean dropAllTable) {
        super(context, DB_NAME, null);
        this.mDropAllTable = false;
    }


    /**
     * 数据库升级
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.e("Main","oldVersion:"+oldVersion+"newVersion:"+newVersion);
        if (newVersion > oldVersion) {
            upGrade(db, oldVersion, newVersion);
        }
        if (mDropAllTable) {
            super.onUpgrade(db, oldVersion, newVersion);
        }
    }

    /**
     * 升级数据库
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    private void upGrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

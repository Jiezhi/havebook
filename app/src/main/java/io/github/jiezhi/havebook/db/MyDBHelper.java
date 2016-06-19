package io.github.jiezhi.havebook.db;

import android.content.Context;

import io.github.jiezhi.havebook.dao.DaoMaster;
import io.github.jiezhi.havebook.dao.DaoSession;

/**
 * Created by jiezhi on 6/19/16.
 * Function:
 */
public class MyDBHelper {
    private static final String TAG = "DBHelper";
    private static MyDBHelper ourInstance;
    private static final String DB_NAME = "Douban.db";

    private DaoSession daoSession;

    private MyDBHelper(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, DB_NAME, null);
        DaoMaster master = new DaoMaster(helper.getWritableDatabase());

        daoSession = master.newSession();
    }

    public static MyDBHelper getInstance(Context context) {
        if (ourInstance == null)
            ourInstance = new MyDBHelper(context);
        return ourInstance;
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}

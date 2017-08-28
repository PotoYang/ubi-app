package com.chh.yinbao.service.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.chh.yinbao.config.Config;
import com.chh.yinbao.db.DownloadInfo;
import com.chh.yinbao.db.WarningInfoTb;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by potoyang on 2017/8/7.
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static final String DATABASE_NAME = Config.APP_DB_NAME;

    private static final int DATABASE_VERSION = Config.APP_DB_VERSION;

    private RuntimeExceptionDao<DownloadInfo, Integer> downloadInfoRuntimeDao;
    private Dao<DownloadInfo, Integer> downloadInfoDao;
    private RuntimeExceptionDao<WarningInfoTb, Integer> warningInfoRuntimeDao;
    private Dao<WarningInfoTb, Integer> warningInfoDao;

    private static DatabaseHelper helper = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * 获取helper对象
     *
     * @param context
     * @return helper对象
     */
    public static DatabaseHelper getHelper(Context context) {
        if (helper == null) {
            helper = new DatabaseHelper(context);
        }
        return helper;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        createTable();
    }

    private void createTable() {
        try {
            TableUtils.createTable(connectionSource, DownloadInfo.class);
            downloadInfoDao = getDownloadInfoDao();
            TableUtils.createTable(connectionSource, WarningInfoTb.class);
            warningInfoDao = getWarningInfoDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {
//        try {
//            //由1到2升级的
//            TableUtils.createTable(connectionSource, WarningInfoTb.class);
//            warningInfoDao = getWarningInfoDao();
//            TableUtils.createTable(connectionSource, CustomAction.class);
//            customActionDao = getCustomActionDao();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }


    public Dao<DownloadInfo, Integer> getDownloadInfoDao() throws SQLException {
        if (downloadInfoDao == null) {
            downloadInfoDao = getDao(DownloadInfo.class);
        }
        return downloadInfoDao;
    }

    public RuntimeExceptionDao<DownloadInfo, Integer> getDownloadInfoRuntimeDao() {
        if (downloadInfoRuntimeDao == null) {
            downloadInfoRuntimeDao = getRuntimeExceptionDao(DownloadInfo.class);
        }
        return downloadInfoRuntimeDao;
    }

    public Dao<WarningInfoTb, Integer> getWarningInfoDao() throws SQLException {
        if (warningInfoDao == null) {
            warningInfoDao = getDao(WarningInfoTb.class);
        }
        return warningInfoDao;
    }

    public RuntimeExceptionDao<WarningInfoTb, Integer> getWarningInfoRuntimeDao() {
        if (warningInfoRuntimeDao == null) {
            warningInfoRuntimeDao = getRuntimeExceptionDao(WarningInfoTb.class);
        }
        return warningInfoRuntimeDao;
    }

}

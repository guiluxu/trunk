package com.wavenet.ding.qpps.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

final
public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "trakinfo.db";
    private static  int DATABASE_VERSION = 1;

    private static DBHelper instance;

    public synchronized static DBHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DBHelper(context);
        }

        return instance;
    }

    public DBHelper(Context context) {
        //CursorFactory设置为null,使用默认值
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

//		//1 轨迹信息
        String trackInfoCreateSQL = "create table if not exists taskinfo(s_recode_id varchar(36) primary key,s_man_id varchar(32),s_townid varchar(32),s_company varchar(32),s_towname varchar(32),s_man_cn varchar(32),t_start varchar(32),t_end varchar(32),mileage double)";
        String trackCreateSQL = "create table if not exists tasktrack(rid integer primary key autoincrement,s_id varchar(36),s_man_id varchar(32),t_upload varchar(32),n_latx double,n_lony double,n_speed double,s_task_type varchar(32),s_recode_id varchar(32),mileage double,datecur integer)";
        String siteCreateSQL = "create table if not exists taskevent(S_MANGE_ID varchar(36) primary key,S_RECODE_ID varchar(36),S_CATEGORY varchar(36),S_TYPE varchar(36),S_IN_MAN varchar(36),T_IN_DATE varchar(36),N_X double,N_Y double,S_IS_MANGE varchar(36),S_SJSB_ID varchar(36),S_DESC varchar(36),S_SOURCE varchar(36),S_LOCAL varchar(36))";
     db.execSQL(trackInfoCreateSQL);
     db.execSQL(trackCreateSQL);
     db.execSQL(siteCreateSQL);

    }
    //如果DATABASE_VERSION值被改为2,系统发现现有数据库版本不同,即会调用onUpgrade
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//		db.execSQL("ALTER TABLE person ADD COLUMN other STRING");

    }


    public synchronized static void closeDB(SQLiteOpenHelper helper, SQLiteDatabase db, Cursor c) {
        if (c != null) {
            c.close();
        }
        if (db != null) {
            db.close();
        }
        if (helper != null) {
            helper.close();
        }
    }
}

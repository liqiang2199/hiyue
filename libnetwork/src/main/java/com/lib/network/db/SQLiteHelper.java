package com.lib.network.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/**
 * SQL数据库
 * Firebase
 */
public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String TAG = "LCS_SQLiteHelper";
    private static final String db_name = "sql.db";//数据库名称
    private static final SQLiteDatabase.CursorFactory factory = null;//暂时用不到
    private static final int version = 1;//版本号，方便以后项目更新用户数据库
    private static SQLiteHelper sqLiteHelper = null;//实例化 SQLiteHelper 对象

    public static SQLiteHelper instance(Context context){
        if (sqLiteHelper == null){
            synchronized (SQLiteHelper.class){
                if (sqLiteHelper == null){
                    sqLiteHelper = new SQLiteHelper(context);
                }
            }
        }
        return sqLiteHelper;
    }

    public SQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public SQLiteHelper(Context context){
        super(context,db_name,factory,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createSheet(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    //创建表
    //create table if not exists Person(id INTEGER primary key autoincrement,age int,name text )
    private void createSheet(SQLiteDatabase db){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("create table if not exists ");
        stringBuilder.append("system_sql (");//表名
        stringBuilder.append("id INTEGER primary key autoincrement,");
        stringBuilder.append("save_key VARCHAR(120),");
        stringBuilder.append("save_value VARCHAR(512) ");//value 可能很长（json）
        stringBuilder.append(")");
        db.execSQL(stringBuilder.toString());
    }
}

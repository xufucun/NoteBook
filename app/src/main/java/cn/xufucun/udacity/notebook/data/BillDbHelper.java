package cn.xufucun.udacity.notebook.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import cn.xufucun.udacity.notebook.data.BillContract.BillEntry;

/**
 * Created by MayiSenlin on 2017/12/8.
 */

public class BillDbHelper extends SQLiteOpenHelper {

    private static final String TAG = "BillDbHelper";

    private static final String DATABASE_NAME = "shelter.db";

    private static final int DATABASE_VERSION = 1;

    public BillDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // 创建一个包含SQL语句的字符串来创建账单
        String SQL_CREATE_PETS_TABLE =  "CREATE TABLE " + BillEntry.TABLE_NAME + " ("
                + BillEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + BillEntry.COLUMN_BILL_TYPE + " INTEGER NOT NULL DEFAULT 0, "
                + BillEntry.COLUMN_BILL_PRICE + " INTEGER NOT NULL, "
                + BillEntry.COLUMN_BILL_REMARK + " TEXT, "
                + BillEntry.COLUMN_BILL_DATE + " INTEGER NOT NULL);";

        // 执行SQL语句
        sqLiteDatabase.execSQL(SQL_CREATE_PETS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // 数据库仍然在版本1，所以这里没有任何事情要做。
    }
}

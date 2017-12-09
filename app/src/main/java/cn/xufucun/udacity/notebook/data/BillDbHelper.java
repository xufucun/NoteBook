package cn.xufucun.udacity.notebook.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import cn.xufucun.udacity.notebook.data.BillContract.BillEntry;

/**
 * Created by MayiSenlin on 2017/12/8.
 */

public class BillDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "shelter.db";
    private static final int DATABASE_VERSION = 2;

    // 创建一个包含SQL语句的字符串来创建账单
    private String SQL_CREATE_BILLS_TABLE = "CREATE TABLE " + BillEntry.TABLE_NAME + " ("
            + BillEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + BillEntry.COLUMN_BILL_TYPE + " INTEGER NOT NULL DEFAULT 0, "
            + BillEntry.COLUMN_BILL_PRICE + " INTEGER NOT NULL, "
            + BillEntry.COLUMN_BILL_REMARK + " TEXT NOT NULL, "
            + BillEntry.COLUMN_BILL_DATE + " INTEGER NOT NULL);";
    // 创建一个包含SQL语句的字符串来删除账单
    private static final String SQL_DELETE_BILLS_TABLE = "DROP TABLE IF EXISTS " + BillEntry.TABLE_NAME;



    public BillDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //执行sql语句
        sqLiteDatabase.execSQL(SQL_CREATE_BILLS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //删除表格并重新创建
        sqLiteDatabase.execSQL(SQL_DELETE_BILLS_TABLE);
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }

}

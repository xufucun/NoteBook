package cn.xufucun.udacity.notebook.data;

import android.provider.BaseColumns;

/**
 * Created by MayiSenlin on 2017/12/8.
 */

public final class BillContract {

    private BillContract(){}

    public static final class BillEntry implements BaseColumns{

        public final static String TABLE_NAME = "bills";        //表名称

        public final static String _ID = BaseColumns._ID;       //唯一ID号 INTEGER
        public final static String COLUMN_BILL_TYPE = "type";   //类型 INTEGER
        public final static String COLUMN_BILL_PRICE ="price";  //金额 INTEGER
        public final static String COLUMN_BILL_REMARK = "remark";    //备注 TEXT
        public final static String COLUMN_BILL_DATE = "time";   //时间 INTEGER

        public static final int TYPE_EXPENSES = 0; //类型 支出
        public static final int TYPE_INCOME = 1;   //类型 收入

    }

}

package cn.xufucun.udacity.notebook.data;

import android.provider.BaseColumns;

/**
 * Created by MayiSenlin on 2017/12/8.
 */

public final class BillContract {

    private BillContract(){}

    public static final class BillEntry implements BaseColumns{

        public final static String TABLE_NAME = "bills";  //表名称

        /**
         * 账单的唯一ID号（仅用于数据库表中）。
         * 类型：INTEGER
         */
        public final static String _ID = BaseColumns._ID;

        /**
         * 类型
         * 数据类型：INTEGER
         */
        public final static String COLUMN_BILL_TYPE = "type";

        /**
         * 金额
         * 数据类型：INTEGER
         */
        public final static String COLUMN_BILL_PRICE ="price";       //金额


        /**
         * 金额
         * 数据类型：TEXT
         */
        public final static String COLUMN_BILL_REMARK = "remark";    //备注

        /**
         * 时间
         * 数据类型：INTEGER
         */
        public final static String COLUMN_BILL_DATE = "weight";  //时间

        /**
         * 类型的可能值。
         */
        public static final int TYPE_EXPENSES = 0;
        public static final int TYPE_INCOME = 1;

    }

}

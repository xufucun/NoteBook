package cn.xufucun.udacity.notebook;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import cn.xufucun.udacity.notebook.data.BillContract.BillEntry;
import cn.xufucun.udacity.notebook.data.BillDbHelper;

public class MainActivity extends AppCompatActivity {

    private BillDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        mDbHelper = new BillDbHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    /**
     * 在屏幕上显示关于宠物数据库状态信息的临时帮助方法。
     */
    private void displayDatabaseInfo() {
        //创建和/或打开一个数据库来读取它
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // 定义一个投影，指定在查询后您将实际使用的数据库中的哪些列。
        String[] projection = {
                BillEntry._ID,
                BillEntry.COLUMN_BILL_TYPE,
                BillEntry.COLUMN_BILL_PRICE,
                BillEntry.COLUMN_BILL_REMARK,
                BillEntry.COLUMN_BILL_DATE };

        // 在宠物表上执行一个查询
        Cursor cursor = db.query(
                BillEntry.TABLE_NAME,   // 要查询的表
                projection,            // 要返回的列
                null,                  // WHERE子句的列
                null,                  // WHERE子句的值
                null,                  // 不要将行分组
                null,                  // 不要按行组过滤
                null);                   // 排序顺序

        TextView displayView = findViewById(R.id.text_view_pet);

        try {
            // Create a header in the Text View that looks like this:
            //
            // The pets table contains <number of rows in Cursor> pets.
            // _id - name - breed - gender - weight
            //
            // In the while loop below, iterate through the rows of the cursor and display
            // the information from each column in this order.
            displayView.setText("The pets table contains " + cursor.getCount() + " pets.\n\n");
            displayView.append(BillEntry._ID + " - " +
                    BillEntry.COLUMN_BILL_TYPE + " - " +
                    BillEntry.COLUMN_BILL_PRICE + " - " +
                    BillEntry.COLUMN_BILL_REMARK + " - " +
                    BillEntry.COLUMN_BILL_DATE + "\n");

            // 找出每列的索引
            int idColumnIndex = cursor.getColumnIndex(BillEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(BillEntry.COLUMN_BILL_TYPE);
            int breedColumnIndex = cursor.getColumnIndex(BillEntry.COLUMN_BILL_PRICE);
            int genderColumnIndex = cursor.getColumnIndex(BillEntry.COLUMN_BILL_REMARK);
            int weightColumnIndex = cursor.getColumnIndex(BillEntry.COLUMN_BILL_DATE);

            // 遍历游标中的所有返回的行
            while (cursor.moveToNext()) {
                // 使用该索引提取单词的字符串或整数值
                // 在当前行光标处于打开状态。
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                String currentBreed = cursor.getString(breedColumnIndex);
                int currentGender = cursor.getInt(genderColumnIndex);
                int currentWeight = cursor.getInt(weightColumnIndex);
                // 显示TextView中光标当前行的每一列的值
                displayView.append(("\n" + currentID + " - " +
                        currentName + " - " +
                        currentBreed + " - " +
                        currentGender + " - " +
                        currentWeight));
            }
        } finally {
            // 读完之后，请始终关闭光标。 这将释放所有资源并使其无效。
            cursor.close();
        }
    }


    /**
     * 添加虚拟数据
     */
    private void insertPet() {
        // 以写入模式获取数据库
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // 创建一个ContentValues对象，其中列名是keys，Toto的宠物属性是值。
        ContentValues values = new ContentValues();
        values.put(BillEntry.COLUMN_BILL_TYPE, BillEntry.TYPE_EXPENSES);
        values.put(BillEntry.COLUMN_BILL_PRICE, 10000);
        values.put(BillEntry.COLUMN_BILL_REMARK,"备注");
        values.put(BillEntry.COLUMN_BILL_DATE,  0);

        // 在数据库中插入一个新的Toto行，返回新行的ID。
        //db.insert（）的第一个参数是宠物表名。
        //第二个参数提供了在ContentValues为空的情况下框架可以插入NULL的列的名称（如果设置为“null”，那么框架在没有值时不会插入行）。
        // 第三个参数是包含Toto信息的ContentValues对象。
        long newRowId = db.insert(BillEntry.TABLE_NAME, null, values);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_insert_dummy_data:
                insertPet();
                displayDatabaseInfo();
                return true;
            case R.id.action_delete_all_entries:
                // Do nothing for now
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

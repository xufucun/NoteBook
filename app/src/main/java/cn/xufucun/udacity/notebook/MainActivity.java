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


    private void displayDatabaseInfo() {

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                BillEntry._ID,
                BillEntry.COLUMN_BILL_TYPE,
                BillEntry.COLUMN_BILL_PRICE,
                BillEntry.COLUMN_BILL_REMARK,
                BillEntry.COLUMN_BILL_DATE };

        // 在账单表上执行一个查询
        Cursor cursor = db.query(BillEntry.TABLE_NAME, projection, null, null, null, null, null);

        TextView displayView = findViewById(R.id.text_view_bill);

        try {
            displayView.setText("当前有" + cursor.getCount() + "条账单.\n\n");
            displayView.append(BillEntry._ID + " - " +
                    BillEntry.COLUMN_BILL_TYPE + " - " +
                    BillEntry.COLUMN_BILL_PRICE + " - " +
                    BillEntry.COLUMN_BILL_REMARK + " - " +
                    BillEntry.COLUMN_BILL_DATE + "\n");

            int idColumnIndex = cursor.getColumnIndex(BillEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(BillEntry.COLUMN_BILL_TYPE);
            int breedColumnIndex = cursor.getColumnIndex(BillEntry.COLUMN_BILL_PRICE);
            int genderColumnIndex = cursor.getColumnIndex(BillEntry.COLUMN_BILL_REMARK);
            int weightColumnIndex = cursor.getColumnIndex(BillEntry.COLUMN_BILL_DATE);

            while (cursor.moveToNext()) {
                int currentID = cursor.getInt(idColumnIndex);
                int currentType = cursor.getInt(nameColumnIndex);
                int currentPrice = cursor.getInt(breedColumnIndex);
                String  currentRemark = cursor.getString(genderColumnIndex);
                int currentTime = cursor.getInt(weightColumnIndex);
                displayView.append(("\n" + currentID + " - " +
                        currentType + " - " +
                        currentPrice + " - " +
                        currentRemark + " - " +
                        currentTime));
            }
        } finally {
            cursor.close();
        }
    }


    /**
     * 添加虚拟数据
     */
    private void insertPet() {
        // 以写入模式获取数据库
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(BillEntry.COLUMN_BILL_TYPE, BillEntry.TYPE_EXPENSES);
        values.put(BillEntry.COLUMN_BILL_PRICE, 10000);
        values.put(BillEntry.COLUMN_BILL_REMARK,"备注");
        values.put(BillEntry.COLUMN_BILL_DATE,  0);

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

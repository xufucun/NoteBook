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
            String string = getResources().getString(R.string.current_data);
            String strings = String.format(string,String.valueOf(cursor.getCount()));
            displayView.setText(strings);
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_update_all_entries:


                update();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void update(){
        BillDbHelper mDbHelper = new BillDbHelper(this);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        // 一列的新值
        ContentValues values = new ContentValues();
        values.put(BillEntry.COLUMN_BILL_PRICE,2000);

        // 根据标题更新哪一行
        String selection = BillEntry.COLUMN_BILL_PRICE + " LIKE ?";
        String[] selectionArgs = { "200" };


       db.update(BillEntry.TABLE_NAME, values, null, null);



    }

    /**
     *  更新数据库
     * @param tableName 表名
     * @param values 更新的数据
     * @param selection 更新条件
     * @param selectionArgs 更新条件中的参数
     */
    private void onUpdate(String tableName,ContentValues values ,String selection,String[] selectionArgs){
        BillDbHelper mDbHelper = new BillDbHelper(this);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        db.update(tableName,values,selection,selectionArgs);
    }


}

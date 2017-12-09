package cn.xufucun.udacity.notebook;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import cn.xufucun.udacity.notebook.data.BillContract.BillEntry;
import cn.xufucun.udacity.notebook.data.BillDbHelper;

public class EditorActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText mPrice;
    private EditText mRemarks;

    private int mType = BillEntry.TYPE_EXPENSES;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        mPrice = findViewById(R.id.edit_bill_price);
        mRemarks = findViewById(R.id.edit_bill_remark);

        Spinner mTypeSpinner = findViewById(R.id.spinner_type);
        mTypeSpinner.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

        String selection = (String) adapterView.getItemAtPosition(position);
        if (!TextUtils.isEmpty(selection)) {
            if (selection.equals(getString(R.string.type_income))) {
                mType = BillEntry.TYPE_INCOME;
            } else {
                mType = BillEntry.TYPE_EXPENSES;
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        mType = BillEntry.TYPE_EXPENSES;
    }


    private void insertBills() {

        String priceString = mPrice.getText().toString().trim();
        String remarkString = mRemarks.getText().toString().trim();

        if (priceString.trim().isEmpty()||remarkString.trim().isEmpty()){
            Toast.makeText(this, R.string.no_input, Toast.LENGTH_SHORT).show();
            return;
        }
        float totalInt = Float.parseFloat(priceString)*100; //存储单位（分）
        int timeStamp = Integer.parseInt(getTime());

        BillDbHelper mDbHelper = new BillDbHelper(this);

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(BillEntry.COLUMN_BILL_TYPE, mType);
        values.put(BillEntry.COLUMN_BILL_PRICE, totalInt);
        values.put(BillEntry.COLUMN_BILL_REMARK, remarkString);
        values.put(BillEntry.COLUMN_BILL_DATE, timeStamp);

        long newRowId = db.insert(BillEntry.TABLE_NAME, null, values);

        // 根据插入是否成功显示Toast消息
        if (newRowId == -1) {
            // 如果行ID是-1，则插入时出错。
            Toast.makeText(this, R.string.error_toast, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getString(R.string.success_toast) + newRowId, Toast.LENGTH_SHORT).show();
        }

    }

    public String getTime(){
        long time = System.currentTimeMillis()/1000;
        return String.valueOf(time);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                insertBills();
                finish();
                return true;

            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

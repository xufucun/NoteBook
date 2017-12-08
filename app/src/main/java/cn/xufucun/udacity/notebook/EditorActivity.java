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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import cn.xufucun.udacity.notebook.data.BillContract.BillEntry;
import cn.xufucun.udacity.notebook.data.BillDbHelper;

public class EditorActivity extends AppCompatActivity {

    private EditText mTotal;
    private EditText mRemarks;
    private EditText mDate;
    private Spinner mTypeSpinner;

    private int mType = BillEntry.TYPE_EXPENSES;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        // Find all relevant views that we will need to read user input from
        mTotal = findViewById(R.id.edit_pet_name);
        mRemarks = findViewById(R.id.edit_pet_breed);
        mDate = findViewById(R.id.edit_pet_weight);
        mTypeSpinner = findViewById(R.id.spinner_gender);

        setupSpinner();

    }

    private void setupSpinner() {
        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.array_gender_options, android.R.layout.simple_spinner_item);
        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        mTypeSpinner.setAdapter(genderSpinnerAdapter);
        mTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.type_income))) {
                        mType = BillEntry.TYPE_INCOME;
                    } else {
                        mType = BillEntry.TYPE_EXPENSES;
                    }
                }
            }

            // 因为AdapterView是一个抽象类，所以必须定义onNothingSelected
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mType = BillEntry.TYPE_EXPENSES;
            }
        });

    }

    private void insertPet() {
        String totalString = mTotal.getText().toString().trim();
        String remarkString = mRemarks.getText().toString().trim();

        int totalInt = Integer.parseInt(totalString);
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
            Toast.makeText(this, "Error with saving pet", Toast.LENGTH_SHORT).show();
        } else {
            //否则，插入是成功的，我们可以显示一个带有行ID的Toast。
            Toast.makeText(this, "Pet saved with row id: " + newRowId, Toast.LENGTH_SHORT).show();
        }

    }

    public String getTime(){
        long time = System.currentTimeMillis()/1000;
        String  str=String.valueOf(time);
        return str;
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
                insertPet();
                finish();
                return true;

            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}

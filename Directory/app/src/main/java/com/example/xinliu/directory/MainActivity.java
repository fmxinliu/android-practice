package com.example.xinliu.directory;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etName;
    private EditText etPhone;
    private TextView tvShow;

    private MyHelper myHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        myHelper = new MyHelper(this);
    }

    private void init() {
        etName = (EditText) findViewById(R.id.et_name);
        etPhone = (EditText) findViewById(R.id.et_phone);
        tvShow = (TextView) findViewById(R.id.tv_show);

        findViewById(R.id.btn_add).setOnClickListener(this);
        findViewById(R.id.btn_delete).setOnClickListener(this);
        findViewById(R.id.btn_update).setOnClickListener(this);
        findViewById(R.id.btn_query).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                add();
                break;
            case R.id.btn_delete:
                delete();
                break;
            case R.id.btn_update:
                update();
                break;
            case R.id.btn_query:
                query();
                break;
        }
    }

    private void add() {
        String name = etName.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        SQLiteDatabase db = myHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name); // table -> name
        values.put("phone", phone); // table -> phone
        db.insert("information", null, values);
        db.close();
        Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
    }

    private void query() {
        SQLiteDatabase db = myHelper.getReadableDatabase();
        Cursor cursor = db.query("information", null, null, null, null, null, null);
        if (cursor.getCount() == 0) {
            tvShow.setText("");
           Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
        } else {
            cursor.moveToFirst();
            tvShow.setText("Name:" + cursor.getString(1) + " " // 直接指定列索引
                + "Phone:" + cursor.getString(cursor.getColumnIndex("phone")) + "\r\n"); // 通过列名获取索引
            while (cursor.moveToNext()) {
                tvShow.append("Name:" + cursor.getString(1) + " "
                        + "Phone:" + cursor.getString(cursor.getColumnIndex("phone")) + "\r\n");
            }
        }
        cursor.close();
        db.close();
    }

    private void update() {
        SQLiteDatabase db = myHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        String name = etName.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        values.put("phone", phone);
        int num = db.update("information", values, "name=?", new String[]{name});
        if (num > 0) {
            Toast.makeText(this, "更新成功", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    private void delete() {
        SQLiteDatabase db = myHelper.getWritableDatabase();
        String name = etName.getText().toString().trim();
        int num = db.delete("information", "name=?", new String[]{name});
        if (num > 0) {
            Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }
}

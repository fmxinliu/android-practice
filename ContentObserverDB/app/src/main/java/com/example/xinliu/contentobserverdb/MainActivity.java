package com.example.xinliu.contentobserverdb;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ContentResolver resolver;
    private Uri uri;
    private ContentValues values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        createDB();
    }

    private void createDB() {
        PersonDBOpenHelper helper = new PersonDBOpenHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        for (int i = 0; i < 3; i++) {
            ContentValues values = new ContentValues();
            values.put("name", "itcast" + i);
            db.insert("info", null, values);
        }
        db.close();
    }

    private void initView() {
        findViewById(R.id.btn_select).setOnClickListener(this);
        findViewById(R.id.btn_insert).setOnClickListener(this);
        findViewById(R.id.btn_delete).setOnClickListener(this);
        findViewById(R.id.btn_update).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // 得到一个内容提供者的解析对象
        resolver = getContentResolver();
        // 新加一个uri路径，参数是string类型的
        uri = Uri.parse("content://com.example.xinliu.contentobserver/info");
        //               scheme |     authorities(当前的内容提供者)    | path(资源)
        // 新建一个ContentValues对象，该对象以key-values的形式来添加记录到数据库表中
        values = new ContentValues();
        switch (v.getId()) {
            case R.id.btn_select:
                query();
                break;
            case R.id.btn_insert:
                insert();
                break;
            case R.id.btn_delete:
                delete();
                break;
            case R.id.btn_update:
                update();
                break;
        }
    }

    private void query() {
        List<Map<String, String>> data = new ArrayList<>();
        Cursor cursor = resolver.query(uri, new String[] {"_id", "name"}, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Map<String, String> map = new HashMap<>();
                map.put("_id", cursor.getString(0));
                map.put("name", cursor.getString(1));
                data.add(map);
            }
            cursor.close();
            Toast.makeText(this,"查询成功", Toast.LENGTH_SHORT).show();
            Log.i("数据库应用", "查询结果:" + data.toString());
        }
    }

    private void insert() {
        Random random = new Random();
        values.put("name", "itcast" + random.nextInt(10));
        Uri newUri = resolver.insert(uri, values);
        Toast.makeText(this,"添加成功", Toast.LENGTH_SHORT).show();
        Log.i("数据库应用", "添加" + newUri);
    }

    private void delete() {
        int deleteCount = resolver.delete(uri, "name=?", new String[]{"itcast0"});
        Toast.makeText(this, "删除成功,删除了" + deleteCount + "条数据。", Toast.LENGTH_SHORT).show();
        Log.i("数据库应用", "删除");
    }

    private void update() {
        values.put("name", "update_itcast");
        int updateCount = resolver.update(uri, values, "name=?", new String[]{"itcast1"});
        Toast.makeText(this, "更新成功,更新了" + updateCount+"条数据。", Toast.LENGTH_SHORT).show();
        Log.i("数据库应用", "更新");
    }
}

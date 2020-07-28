package com.example.xinliu.newbooknote;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class WordDatabaseHelper extends SQLiteOpenHelper {
    private final String CREATE_SQL = "create table if not exists dict(_id integer primary key, word, detail)";

    public WordDatabaseHelper(Context context, String name, int version) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        System.out.println("-------------- OnUpdate Called ------------------"
                + oldVersion + "--->" + newVersion);
    }

    public Cursor queryForWords(String key) {
        Cursor cursor = this.getReadableDatabase().rawQuery(
                "select * from dict where word like ? or detail like ?",
                new String[] { "%" + key + "%", "%" + key + "%" });
        return cursor;
    }

    public int getTotalNumOfRecord() {
        Cursor cursor = this.getReadableDatabase().rawQuery(
                "select count(*) from dict", null);
        int totalNum = 0;
        if (cursor.moveToNext()) {
            totalNum = cursor.getInt(0);
        }

        return totalNum;
    }

    public void insertWord(String[] cols) {
        this.getWritableDatabase().execSQL(
                "insert into dict values(null, ?, ?)", cols);
    }
}

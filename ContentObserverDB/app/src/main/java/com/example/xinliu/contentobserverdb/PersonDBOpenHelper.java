package com.example.xinliu.contentobserverdb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PersonDBOpenHelper extends SQLiteOpenHelper {
    public PersonDBOpenHelper(Context context) {
        super(context, "person.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table info (_id integer primary key AutoIncrement, name varchar(20))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

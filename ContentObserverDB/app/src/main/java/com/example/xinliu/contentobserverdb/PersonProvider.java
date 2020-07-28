package com.example.xinliu.contentobserverdb;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class PersonProvider extends ContentProvider {
    // 定义一个uri路径的匹配器，如果路径匹配不成功返回 -1
    private static UriMatcher uriMatcher = new UriMatcher(-1);
    // 匹配路径成功时的返回码
    private static final int SUCCESS = 1;
    // 数据库操作类的对象
    private PersonDBOpenHelper helper;
    // 添加路径匹配器的规则
    static{
        uriMatcher.addURI("com.example.xinliu.contentobserver", "info", SUCCESS);
    }

    // 当内容提供者被创建的时候调用
    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        helper = new PersonDBOpenHelper(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        // 匹配查询的Uri路径
        int code = uriMatcher.match(uri);
        if (code == SUCCESS) {
            SQLiteDatabase db = helper.getReadableDatabase();
            return db.query("info", projection, selection, selectionArgs, null, null, sortOrder);
        } else{
            throw new UnsupportedOperationException("路径不正确，我是不会给你提供数据的！");
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        int code = uriMatcher.match(uri);
        if (code == SUCCESS) {
            SQLiteDatabase db = helper.getWritableDatabase();
            long rowId = db.insert("info", null, values);
            if (rowId > 0) {
                Uri insertUri = ContentUris.withAppendedId(uri, rowId);
                getContext().getContentResolver().notifyChange(insertUri, null);
                return insertUri;
            }
            db.close();
            return uri;
        } else{
            throw new UnsupportedOperationException("路径不正确，我是不会给你插入数据的！");
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        int code = uriMatcher.match(uri);
        if (code == SUCCESS) {
            SQLiteDatabase db = helper.getWritableDatabase();
            int count = db.delete("info", selection, selectionArgs);
            if (count > 0) {
                getContext().getContentResolver().notifyChange(uri, null);
            }
            db.close();
            return count;
        } else{
            throw new UnsupportedOperationException("路径不正确，我是不会给你删除数据的！");
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        int code = uriMatcher.match(uri);
        if(code == SUCCESS){
            SQLiteDatabase db = helper.getWritableDatabase();
            int count = db.update("info", values, selection, selectionArgs);
            if(count>0){
                getContext().getContentResolver().notifyChange(uri, null);
            }
            db.close();
            return count;
        } else {
            throw new IllegalArgumentException("路径不正确，我是不会给你更新数据的！");
        }
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw null;
    }








}

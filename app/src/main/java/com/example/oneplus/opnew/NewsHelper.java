package com.example.oneplus.opnew;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NewsHelper extends SQLiteOpenHelper {

    public static final String CREATE_NEWS = "create table History ("
            + "url text primary key, "
            + "lastDate date , "
            + "title text, "
            + "author_name text, "
            + "date text)";

    private Context mContext;

    public NewsHelper(Context context,String name,SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_NEWS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

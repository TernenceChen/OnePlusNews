package com.example.oneplus.opnew;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.oneplus.opnew.bean.HistoryBean;

import java.util.ArrayList;
import java.util.List;

public class NewsDB {

    public static final String DB_NAME = "History";
    public static final int VERSION = 1;
    private static NewsDB newsDB;
    private SQLiteDatabase db;

    public NewsDB(Context context){
        NewsHelper newsHelper = new NewsHelper(context,DB_NAME,null,VERSION);
        db = newsHelper.getWritableDatabase();
    }

    public synchronized static NewsDB getInstance(Context context){
        if (newsDB == null){
            newsDB = new NewsDB(context);
        }
        return newsDB;
    }

    public void saveNews (HistoryBean historyBean){
        if (historyBean != null){
            ContentValues values = new ContentValues();
            values.put("title",historyBean.getHistory_title());
            values.put("author_name",historyBean.getHistory_author_name());
            values.put("date",historyBean.getHistory_date());
            values.put("url",historyBean.getHistory_url());
            values.put("time",historyBean.getHistory_time());
            db.insert("History",null,values);
        }
    }

    public List<HistoryBean> historyBeanList (){
        List<HistoryBean> list = new ArrayList<>();
        Cursor cursor = db.query(DB_NAME,null,null,null,null,null,null);
        if (cursor.moveToFirst()){
            do {
                HistoryBean historyBean = new HistoryBean();
                historyBean.setHistory_title(cursor.getString(cursor.getColumnIndex("title")));
                historyBean.setHistory_author_name(cursor.getString(cursor.getColumnIndex("author_name")));
                historyBean.setHistory_date(cursor.getString(cursor.getColumnIndex("date")));
                historyBean.setHistory_url(cursor.getString(cursor.getColumnIndex("url")));
                historyBean.setHistory_time(cursor.getLong(cursor.getColumnIndex("time")));
                list.add(historyBean);
            }while (cursor.moveToNext());
        }
        if (cursor != null){
            cursor.close();
        }
        return list;
    }

}

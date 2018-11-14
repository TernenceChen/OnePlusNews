package com.example.oneplus.opnew.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.oneplus.opnew.NewsHelper;
import com.example.oneplus.opnew.bean.HistoryBean;

import java.util.ArrayList;
import java.util.List;

public class HistoryUtils {

    private NewsHelper newsHelper;

    private String tabHistory = "history";

    private HistoryUtils(Context context){
        newsHelper = new NewsHelper(context,"history.db",null,1);
    }

    private static HistoryUtils instance;

    public static synchronized HistoryUtils getInstance(Context context){
        if (instance == null){
            instance = new HistoryUtils(context);
        }
        return instance;
    }

    /*
    * 增加历史记录
    */

    public void addHistory(String name){
        SQLiteDatabase db = newsHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("lastTime",System.currentTimeMillis());
        if (getIdByName(name) == 0){
            values.put("name",name);
            db.insert(tabHistory,null,values);
        }else{
            db.update(tabHistory,values,"name = ?", new String[]{name});
        }
    }

    public void delHistory(int id){
        SQLiteDatabase db = newsHelper.getWritableDatabase();
        db.delete(tabHistory, "id = " + id, null);
    }

    public List<HistoryBean> getHistoryData(){
        List<HistoryBean> data = new ArrayList<>();
        SQLiteDatabase db = newsHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from history order by lasttime desc;", null);
        while(cursor.moveToNext()){
            String historytitle = cursor.getString(cursor.getColumnIndex("history_title"));
            String historyauthor_name = cursor.getString(cursor.getColumnIndex("history_author_name"));
            String historydate = cursor.getString(cursor.getColumnIndex("history_date"));
            HistoryBean bean = new HistoryBean();
            data.add(bean);
        }

        cursor.close();
        return data;
    }

    public int getIdByName(String name){
        int id = 0;
        SQLiteDatabase db = newsHelper.getReadableDatabase();
        Cursor cursor = db.query(tabHistory, null, "name = ?", new String[]{name}, null,null,null);
        if (cursor.moveToNext()){
            id = cursor.getInt(0);
        }
        cursor.close();
        return id;
    }
}

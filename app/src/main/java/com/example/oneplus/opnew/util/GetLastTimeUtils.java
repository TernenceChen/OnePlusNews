package com.example.oneplus.opnew.util;

import android.util.Log;

import java.util.Calendar;

public class GetLastTimeUtils {
    /**
     * 将日期转化为long  例：2018.12.15 ---->  20181215
     * @return
     */
    public long execute(){
        long time;
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        Log.i("GetLastTime","" + year);
        int month = calendar.get(Calendar.MONTH) + 1;
        Log.i("GetLastTime","" + month);
        int day = calendar.get(Calendar.DAY_OF_MONTH) - 3;
        Log.i("GetLastTime","" + day);
        if (day <= 0){
            if (month == 3){
                if (year%4 == 0) {
                    day = day + 29;
                }else{
                    day = day + 28;
                }
            }else if (month == 5 || month == 7 || month == 10 || month == 12){
                day = day + 30;
            }else if (month == 1){
                day = day + 31;
                year = year - 1;
            }else{
                day = day + 31;
            }
            month = month - 1;
        }
        time = day + month * 100 + year * 10000;
        Log.i("GetLastTime","" + time);
        return time;
    }
}


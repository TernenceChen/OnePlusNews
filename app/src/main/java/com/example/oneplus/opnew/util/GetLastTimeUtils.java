package com.example.oneplus.opnew.util;

import java.util.Calendar;

public class GetLastTimeUtils {
    public long execute(){
        long time;
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH) - 3;
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
        return time;
    }
}


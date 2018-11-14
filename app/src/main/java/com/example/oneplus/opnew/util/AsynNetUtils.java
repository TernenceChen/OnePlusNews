package com.example.oneplus.opnew.util;

import android.os.Handler;

public class AsynNetUtils {

    public interface Callback{
        void onResponse (String response);
    }

    public static void get(final String url, final Callback callback){
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String response = HttpURLConnectionUtils.get(url);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onResponse(response);
                    }
                });
            }
        }).start();
    }

}

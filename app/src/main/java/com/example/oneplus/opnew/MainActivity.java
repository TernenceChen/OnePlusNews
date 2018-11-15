package com.example.oneplus.opnew;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    TabHost tabHost = null;
    private long lastback = 0;
    private NewsHelper newsHelper;
    private SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int i = 0; i < 3; i++){
            View view = LayoutInflater.from(this).inflate(R.layout.tab_widget_indicator,null,false);
            TextView textView = view.findViewById(R.id.indicator);
            tabHost = findViewById(R.id.tabHost);
            tabHost.setup();
            switch (i){
                case 0:
                    textView.setText("首页");
                    textView.setTextSize(17);
                    tabHost.addTab(tabHost.newTabSpec("1").setIndicator(view).setContent(R.id.frag_first));
                    break;
                case 1:
                    textView.setText("历史记录");
                    textView.setTextSize(17);
                    tabHost.addTab(tabHost.newTabSpec("2").setIndicator(view).setContent(R.id.frag_second));
                    break;
                case 2:
                    textView.setText("添加");
                    textView.setTextSize(17);
                    tabHost.addTab(tabHost.newTabSpec("3").setIndicator(view).setContent(R.id.frag_third));
                    break;
                default:
                    break;
            }
        }
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                for (int i = 0; i < 3; i++){
                    ((TextView) tabHost.getTabWidget().getChildTabViewAt(i).findViewById(R.id.indicator)).setTextColor(getColor(R.color.colorAccent));
                }
                if (tabHost.getCurrentTabTag().equals(tabId)){
                    ((TextView) tabHost.getCurrentTabView().findViewById(R.id.indicator)).setTextColor(getColor(R.color.colorBlack));
                }
            }
        });
        ((TextView)tabHost.getCurrentTabView().findViewById(R.id.indicator)).setTextColor(getColor(R.color.colorBlack));
    }

    @Override
    public void onBackPressed() {
        if (lastback == 0 || System.currentTimeMillis() - lastback > 2000){
            Toast.makeText(this,"再按一次退出",Toast.LENGTH_SHORT).show();
            lastback = System.currentTimeMillis();
            return;
        }
        super.onBackPressed();
    }

    public void updateTable(){
        long time = getLastTime();
        newsHelper.getWritableDatabase().delete("History",time + ">=?",new String[]{"time"});
    }

    public long getLastTime(){
        long time;
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH) - 3;
        time = day + month * 100 + year * 10000;
        return time;
    }
}

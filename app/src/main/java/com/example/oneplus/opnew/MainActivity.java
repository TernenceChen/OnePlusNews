package com.example.oneplus.opnew;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oneplus.opnew.util.GetLastTimeUtils;

public class MainActivity extends AppCompatActivity {

    TabHost mTabHost = null;
    private long lastback = 0;
    private NewsHelper mNewsHelper;
    private int tabCount = 3;
    private GetLastTimeUtils getLastTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Thread(new Runnable() {
            @Override
            public void run() {
                mNewsHelper = new NewsHelper(MainActivity.this,"History",null,1);
                updateTable();
            }
        }).start();

        for (int i = 0; i < tabCount; i++){
            View view = LayoutInflater.from(this).inflate(R.layout.tab_widget_indicator,null,false);
            TextView textView = view.findViewById(R.id.indicator);
            mTabHost = findViewById(R.id.tabHost);
            mTabHost.setup();
            switch (i){
                case 0:
                    textView.setText(R.string.main_tab_name);
                    textView.setTextSize(R.dimen.tabTextSize);
                    mTabHost.addTab(mTabHost.newTabSpec("1").setIndicator(view).setContent(R.id.frag_first));
                    break;
                case 1:
                    textView.setText(R.string.history_record_name);
                    textView.setTextSize(R.dimen.tabTextSize);
                    mTabHost.addTab(mTabHost.newTabSpec("2").setIndicator(view).setContent(R.id.frag_second));
                    break;
                case 2:
                    textView.setText(R.string.add_label_name);
                    textView.setTextSize(R.dimen.tabTextSize);
                    mTabHost.addTab(mTabHost.newTabSpec("3").setIndicator(view).setContent(R.id.frag_third));
                    break;
                default:
                    break;
            }
        }
        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                for (int i = 0; i < tabCount; i++){
                    ((TextView) mTabHost.getTabWidget().getChildTabViewAt(i).findViewById(R.id.indicator)).setTextColor(getColor(R.color.colorAccent));
                }
                if (mTabHost.getCurrentTabTag().equals(tabId)){
                    ((TextView) mTabHost.getCurrentTabView().findViewById(R.id.indicator)).setTextColor(getColor(R.color.colorBlack));
                }
            }
        });
        ((TextView)mTabHost.getCurrentTabView().findViewById(R.id.indicator)).setTextColor(getColor(R.color.colorBlack));
    }

    @Override
    public void onBackPressed() {
        if (lastback == 0 || System.currentTimeMillis() - lastback > 2000){
            Toast.makeText(this,R.string.quit_toast,Toast.LENGTH_SHORT).show();
            lastback = System.currentTimeMillis();
            return;
        }
        super.onBackPressed();
    }

    public void updateTable(){
        getLastTime = new GetLastTimeUtils();
        long time = getLastTime.execute();
        mNewsHelper.getWritableDatabase().delete("History",time + ">=?",new String[]{"time"});
    }

}

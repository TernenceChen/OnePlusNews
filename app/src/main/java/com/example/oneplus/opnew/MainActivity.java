package com.example.oneplus.opnew;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oneplus.opnew.util.GetLastTimeUtils;

import static com.example.oneplus.opnew.NewsDB.DB_NAME;

public class MainActivity extends AppCompatActivity {

    private static final int TAB_COUNT = 3;
    private static long mLastBack = 0;
    private TabHost mTabHost;
    private NewsHelper mNewsHelper;
    private GetLastTimeUtils mGetLastTimeUtils;
    private DatabaseTask mDatabaseTask;

    static {
        System.loadLibrary("native-lib");
    }

    public native String stringFromJNI();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this, stringFromJNI(), Toast.LENGTH_LONG).show();
        initView();
        mDatabaseTask = new DatabaseTask();
        mDatabaseTask.execute();
    }

    class DatabaseTask extends AsyncTask{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            mNewsHelper = new NewsHelper(MainActivity.this,DB_NAME,null,1);
            updateTable();
            return true;
        }

        @Override
        protected void onProgressUpdate(Object[] values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
        }
    }


    public void initView (){
        for (int i = 0; i < TAB_COUNT; i++){
            View view = LayoutInflater.from(this).inflate(R.layout.tab_widget_indicator,null,false);
            TextView textView = view.findViewById(R.id.indicator);
            mTabHost = findViewById(R.id.tabHost);
            mTabHost.setup();
            switch (i){
                case 0:
                    textView.setText(R.string.main_tab_name);
                    Log.i("MainActivity_mTabHost",this.getString(R.string.main_tab_name));
                    mTabHost.addTab(mTabHost.newTabSpec("main_tab").setIndicator(view).setContent(R.id.frag_first));
                    break;
                case 1:
                    textView.setText(R.string.historyRecord_tab_name);
                    mTabHost.addTab(mTabHost.newTabSpec("history_tab").setIndicator(view).setContent(R.id.frag_second));
                    break;
                case 2:
                    textView.setText(R.string.addLabel_tab_name);
                    mTabHost.addTab(mTabHost.newTabSpec("add_tab").setIndicator(view).setContent(R.id.frag_third));
                    break;
                default:
                    break;
            }

        }
        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                for (int i = 0; i < TAB_COUNT; i++){
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
        if (mLastBack == 0 || System.currentTimeMillis() - mLastBack > 2000){
            Toast.makeText(this,R.string.quit_toast,Toast.LENGTH_SHORT).show();
            Log.i("MainActivity_lastBack","lastBack: " + mLastBack);
            mLastBack = System.currentTimeMillis();
            return;
        }
        super.onBackPressed();
    }

    public void updateTable(){
        Log.i("MainActivity_updateTable","UpdateTable");
        mGetLastTimeUtils = new GetLastTimeUtils();
        long time = mGetLastTimeUtils.execute();
        mNewsHelper.getWritableDatabase().delete(DB_NAME,time + ">=?",new String[]{"time"});
    }

}

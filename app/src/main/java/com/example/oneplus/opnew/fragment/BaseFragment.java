package com.example.oneplus.opnew.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.oneplus.opnew.NewsActivity;
import com.example.oneplus.opnew.NewsDB;
import com.example.oneplus.opnew.R;
import com.example.oneplus.opnew.adapter.NewsAdapter;
import com.example.oneplus.opnew.bean.HistoryBean;
import com.example.oneplus.opnew.bean.NewsListNormalBean;
import com.example.oneplus.opnew.util.HttpURLConnectionUtils;
import com.example.oneplus.opnew.util.ParseJsonUtils;
import com.github.promeg.pinyinhelper.Pinyin;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Calendar;

public class BaseFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    public static String BASE_FRAGMENT = "base_fragment";

    private NewsDB newsDB;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private String mUrl;
    private String mTid;
    private String mPinyin;
    private View mView;
    private NewsAdapter mNewsAdapter;
    private GetNewsFromNewTask mGetNewsFromNewTask;
    private ArrayList<NewsListNormalBean.ResultBean.DataBean> mNewsListNormalBeanList = new ArrayList<>();

    public static BaseFragment newInstance(String type) {
        BaseFragment fragment = new BaseFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(BASE_FRAGMENT, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        },1000);
        onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.basefrag_layout, null);
        newsDB = new NewsDB(getActivity());
        mSwipeRefreshLayout = mView.findViewById(R.id.base_swipeRefresh);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorTabNormal, R.color.colorLove, R.color.colorBlack);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView = mView.findViewById(R.id.base_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mNewsAdapter = new NewsAdapter(getActivity(), mNewsListNormalBeanList);
        mNewsAdapter.setmOnItemClickListener(mOnItemClickListener);
        mRecyclerView.setAdapter(mNewsAdapter);
        onRefresh();
        return mView;
    }

    private final MyHandler mHandler = new MyHandler(BaseFragment.this);

    private static class MyHandler extends Handler{
        private final WeakReference<BaseFragment> mBaseFragment;

        private MyHandler(BaseFragment baseFragment) {
            mBaseFragment = new WeakReference<>(baseFragment);
        }

        @Override
        public void handleMessage(Message msg) {
            BaseFragment fragment = mBaseFragment.get();
            if (fragment != null){

            }
        }
    }

    private void getmUrl() {
        if (getArguments() != null) {
            mTid = getArguments().getString("base_fragment");

            long start = System.currentTimeMillis();
            mPinyin = Pinyin.toPinyin(mTid, "");
            Log.d("Time",""+(System.currentTimeMillis() - start));
        }
        if (mPinyin.equalsIgnoreCase("TOUTIAO")) {
            mUrl = "http://v.juhe.cn/toutiao/index?type=&key=d9274c578f7955396961f9ee694112d3";
        } else {
            mUrl = "http://v.juhe.cn/toutiao/index?type=" + mPinyin + "&key=d9274c578f7955396961f9ee694112d3";
        }
        Log.i("out-------------->", mUrl + "pinyin =" + mPinyin);
        mGetNewsFromNewTask = new GetNewsFromNewTask();
        mGetNewsFromNewTask.execute();

    }

    class GetNewsFromNewTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            String response = HttpURLConnectionUtils.get(mUrl);
            Log.i("out----------->", "GetHttp------");
            parseJson(response);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }

    private void parseJson(String response){
        ParseJsonUtils mParseJsonUtils = new ParseJsonUtils();
        mNewsListNormalBeanList = mParseJsonUtils.parseJson(response);
        if (getActivity() != null){
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mNewsAdapter.setmNewsListNormalBeanList(mNewsListNormalBeanList);
                }
            });
        }
    }

    @Override
    public void onDestroy() {
        mHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    private NewsAdapter.OnItemClickListener mOnItemClickListener = new NewsAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View v, int position) {
            if (mNewsListNormalBeanList.size() <= 0) {
                return;
            }
            NewsListNormalBean.ResultBean.DataBean newsListNormalBean = mNewsListNormalBeanList.get(position);
            Intent intent;
            HistoryBean historyBean = new HistoryBean();
            String url = newsListNormalBean.getUrl();
            long time = getNowTime();
            historyBean.setHistory_time(time);
            String title = newsListNormalBean.getTitle();
            historyBean.setHistory_title(title);
            String author_name = newsListNormalBean.getAuthor_name();
            historyBean.setHistory_author_name(author_name);
            String date = newsListNormalBean.getDate();
            historyBean.setHistory_date(date);
            historyBean.setHistory_url(url);
            intent = new Intent(getActivity(), NewsActivity.class);
            intent.putExtra("Url", url);
            newsDB.saveNews(historyBean);
            getActivity().startActivity(intent);
        }
    };

    @Override
    public void onRefresh() {
        if (mNewsListNormalBeanList != null) {
            mNewsListNormalBeanList.clear();
        }
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getmUrl();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, 0);
    }

    public long getNowTime(){
        long time;
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        time = day + month * 100 + year * 10000;
        Log.i("GetNowTime","" + time);
        return time;
    }
}

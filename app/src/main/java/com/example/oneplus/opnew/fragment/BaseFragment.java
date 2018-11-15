package com.example.oneplus.opnew.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import com.example.oneplus.opnew.NewsHelper;
import com.example.oneplus.opnew.R;
import com.example.oneplus.opnew.adapter.NewsAdapter;
import com.example.oneplus.opnew.bean.HistoryBean;
import com.example.oneplus.opnew.bean.NewsListNormalBean;
import com.example.oneplus.opnew.util.HttpURLConnectionUtils;
import com.github.promeg.pinyinhelper.Pinyin;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BaseFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    public static String BASE_FRAGMENT = "base_fragment";

    private NewsDB newsDB;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;
    private String mUrl;
    private String tid;
    private String pinyin;
    private View mView;
    private NewsHelper newsHelper;
    private NewsAdapter newsAdapter;
    private ArrayList<NewsListNormalBean.ResultBean.DataBean> mNewsListNormalBeanList = new ArrayList<>();

    private int pageIndex = 0;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.basefrag_layout, null);
        newsDB = new NewsDB(getActivity());
        newsHelper = new NewsHelper(getActivity(), "History", null, 1);
        swipeRefreshLayout = mView.findViewById(R.id.base_swipeRefresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorTabNormal, R.color.colorLove, R.color.colorBlack);
        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerView = mView.findViewById(R.id.base_view);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        newsAdapter = new NewsAdapter(getActivity(), mNewsListNormalBeanList);
        newsAdapter.setmOnItemClickListener(mOnItemClickListener);
        recyclerView.setAdapter(newsAdapter);
        onRefresh();
        return mView;
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            swipeRefreshLayout.setRefreshing(false);
        }
    };

    private void getmUrl() {
        if (getArguments() != null) {
            tid = getArguments().getString("base_fragment");
            pinyin = Pinyin.toPinyin(tid, "");
        }
        if (pinyin.equalsIgnoreCase("TOUTIAO")) {
            mUrl = "http://v.juhe.cn/toutiao/index?type=&key=d9274c578f7955396961f9ee694112d3";
        } else {
            mUrl = "http://v.juhe.cn/toutiao/index?type=" + pinyin + "&key=d9274c578f7955396961f9ee694112d3";
        }
        Log.i("out-------------->", mUrl + "pinyin =" + pinyin);
        getNewsFromNet();
    }

    private void getNewsFromNet() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String response = HttpURLConnectionUtils.get(mUrl);
                Log.i("out----------->", "GetHttp------");
                parseJson(response);
            }
        }).start();
        if (getActivity() == null) {
            return;
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                newsAdapter.setmNewsListNormalBeanList(mNewsListNormalBeanList);
            }
        });
    }

    public ArrayList<NewsListNormalBean.ResultBean.DataBean> parseJson(String response) {

        try {
            JSONObject jsonObjs = new JSONObject(response).getJSONObject("result");
            JSONArray data = jsonObjs.getJSONArray("data");
            final int itemCounts = data.length();
            Log.i("out---------->", "" + itemCounts);
            List<NewsListNormalBean.ResultBean.DataBean> tempList = new ArrayList<>();
            for (int i = 0; i < itemCounts; i++) {
                Log.i("out----------> i = ", "" + i);
                JSONObject jsonObj = data.getJSONObject(i);
                NewsListNormalBean.ResultBean.DataBean dataBean = new NewsListNormalBean.ResultBean.DataBean();
                Log.i("out---------->", jsonObj.getString("title"));
                dataBean.setTitle(jsonObj.getString("title"));
                Log.i("out---------->", jsonObj.getString("author_name"));
                dataBean.setAuthor_name(jsonObj.getString("author_name"));
                dataBean.setDate(jsonObj.getString("date"));
                dataBean.setUrl(jsonObj.getString("url"));
                dataBean.setThumbnail_pic_s(jsonObj.getString("thumbnail_pic_s"));
                dataBean.setThumbnail_pic_s02(jsonObj.optString("thumbnail_pic_s02", null));
                dataBean.setThumbnail_pic_s03(jsonObj.optString("thumbnail_pic_s03", null));
                tempList.add(dataBean);

            }
            mNewsListNormalBeanList.addAll(tempList);
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    newsAdapter.setmNewsListNormalBeanList(mNewsListNormalBeanList);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
        return mNewsListNormalBeanList;
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
        pageIndex = 0;
        if (mNewsListNormalBeanList != null) {
            mNewsListNormalBeanList.clear();
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getmUrl();
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 1000);
    }

    public long getNowTime(){
        long time;
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        time = day + month * 100 + year * 10000;
        return time;
    }
}

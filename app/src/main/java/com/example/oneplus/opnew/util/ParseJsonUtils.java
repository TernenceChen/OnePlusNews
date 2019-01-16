package com.example.oneplus.opnew.util;

import android.util.Log;

import com.example.oneplus.opnew.bean.NewsListNormalBean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ParseJsonUtils {

    private ArrayList<NewsListNormalBean.ResultBean.DataBean> mNewsListNormalBeanList = new ArrayList<>();

    public ArrayList<NewsListNormalBean.ResultBean.DataBean> parseJson(String response) {

        try {
            if (response != null){
                JSONObject jsonObjs = new JSONObject(response).getJSONObject("result");
                JSONArray data = jsonObjs.optJSONArray("data");
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
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mNewsListNormalBeanList;
    }

}

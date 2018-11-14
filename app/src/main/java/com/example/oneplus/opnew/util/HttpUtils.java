package com.example.oneplus.opnew.util;

import com.example.oneplus.opnew.bean.NewsBean;
import com.example.oneplus.opnew.bean.NewsListNormalBean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class HttpUtils {

    private NewsListNormalBean newsBean;

    private int imageCount = 1;

    public void getHttp(String type){
        String url = "http://v.juhe.cn/toutiao/index?type=" + type + "&key=d9274c578f7955396961f9ee694112d3";
        AsynNetUtils.get(url, new AsynNetUtils.Callback() {
            @Override
            public void onResponse(String response) {
                parseJson(response);
            }
        });
    }

    public void parseJson (String response){
        try{
            JSONObject jsonObject = new JSONObject(response);
            newsBean.setReason(jsonObject.getString("reason"));
            newsBean.setError_code(jsonObject.getInt("error_code"));
            JSONObject jsonObjs = new JSONObject(response).getJSONObject("result");
            NewsBean.ResultBean resultBean = null;
            resultBean.setStat(jsonObjs.getString("stat"));
            resultBean.setData((List<NewsBean.ResultBean.DataBean>) jsonObjs.getJSONArray("data"));
            JSONArray data = jsonObjs.getJSONArray("data");
            NewsBean.ResultBean.DataBean dataBean = null;
            for (int i = 0; i < data.length(); i++){
                JSONObject jsonObj = data.getJSONObject(i);
                dataBean.setUniquekey(jsonObj.getString("uniquekey"));
                dataBean.setTitle(jsonObj.getString("title"));
                dataBean.setDate(jsonObj.getString("date"));
                dataBean.setCategory(jsonObj.getString("category"));
                dataBean.setAuthor_name(jsonObj.getString("author_name"));
                dataBean.setUrl(jsonObj.getString("url"));
                dataBean.setThumbnail_pic_s(jsonObj.getString("thumbnail_pic_s"));
                dataBean.setThumbnail_pic_s02(jsonObj.getString("thumbnail_pic_s02"));
                dataBean.setThumbnail_pic_s03(jsonObj.getString("thumbnail_pic_s03"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}

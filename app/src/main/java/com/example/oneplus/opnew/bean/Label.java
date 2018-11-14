package com.example.oneplus.opnew.bean;

import java.util.ArrayList;
import java.util.List;

public class Label {

    private List<String> listStr;

    public Label (){
        super();
    }

    public List<String> getListStr() {
        listStr = new ArrayList<>();
        listStr.add("头条");
        listStr.add("社会");
        listStr.add("国内");
        listStr.add("国际");
        listStr.add("娱乐");
        listStr.add("体育");
        return listStr;
    }

    public void setListStr(List<String> listStr) {
        this.listStr = listStr;
    }
}

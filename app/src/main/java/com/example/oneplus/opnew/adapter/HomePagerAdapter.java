package com.example.oneplus.opnew.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.example.oneplus.opnew.fragment.BaseFragment;
import com.example.oneplus.opnew.fragment.HomePagerFragment;

import java.util.ArrayList;
import java.util.List;

public class HomePagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mFragmentList;
    private List<String> mStringList;

    public HomePagerAdapter(FragmentManager childFragmentManager, List<Fragment> mFragmentList, List<String> mStringList) {
        super(childFragmentManager);
        this.mFragmentList = mFragmentList;
        this.mStringList = mStringList;
    }

    public void updateData(List<String> dataList){
        ArrayList<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++){
            fragments.add(BaseFragment.newInstance(mStringList.get(i)));
        }
        setFragmentList(fragments);
    }

    private void setFragmentList(List<Fragment> fragmentLists){
        if (this.mFragmentList != null){
            this.mFragmentList.clear();
        }
        mFragmentList.addAll(fragmentLists);
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int i) {
        return mFragmentList.get(i);
    }

    @Override
    public int getCount() {
        Log.i("HomePagerAdapter", "mFragmentList size=" + mFragmentList.size());
        return mFragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mStringList.get(position);
    }

}

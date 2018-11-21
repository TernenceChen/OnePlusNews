package com.example.oneplus.opnew.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.oneplus.opnew.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class HomePagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> fragmentLists;
    private List<String> stringList = new ArrayList<>();

    public HomePagerAdapter(FragmentManager childFragmentManager, List<Fragment> fragmentLists) {
        super(childFragmentManager);
        this.fragmentLists = fragmentLists;
    }

    public void updateData(List<String> dataList){
        ArrayList<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++){
            fragments.add(BaseFragment.newInstance(stringList.get(i)));
        }
        setFragmentList(fragments);
    }

    private void setFragmentList(ArrayList<Fragment> fragmentLists){
        if (this.fragmentLists != null){
            fragmentLists.clear();
        }
        this.fragmentLists = fragmentLists;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int i) {
        return fragmentLists.get(i);
    }

    @Override
    public int getCount() {
        return fragmentLists.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return stringList.get(position);
    }
}

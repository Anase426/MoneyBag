package com.gsandroid.moneybag.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class HomeAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList;

    public HomeAdapter(@NonNull FragmentManager fm,List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    // 显示哪个页面
    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    // 页面个数
    @Override
    public int getCount() {
        return fragmentList.size();
    }
}

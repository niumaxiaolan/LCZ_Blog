package com.lcz.lcz_blog.module.mian.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @author 刘传政
 * @date 2018/8/13 0013 16:32
 * QQ:1052374416
 * telephone:18501231486
 * 作用:
 * 注意事项:
 */
public class MyMainPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList;

    public MyMainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public MyMainPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList == null ? 0 : fragmentList.size();
    }
}

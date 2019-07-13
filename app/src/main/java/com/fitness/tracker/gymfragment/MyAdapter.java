package com.fitness.tracker.gymfragment;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.fitness.tracker.gymfragment.CycleFragment;
import com.fitness.tracker.gymfragment.RunFragment;
import com.fitness.tracker.gymfragment.WalkFragment;

public class MyAdapter extends FragmentPagerAdapter {

    private Context myContext;
    int totalTabs;

    public MyAdapter(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                RunFragment runFragment = new RunFragment();
                return runFragment;
            case 1:
                WalkFragment walkFragment = new WalkFragment();
                return walkFragment;
            case 2:
                CycleFragment cycleFragment = new CycleFragment();
                return cycleFragment;
            default:
                return null;
        }
    }
    // this counts total number of tabs
    @Override
    public int getCount() {
        return totalTabs;
    }
}
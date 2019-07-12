package com.fitness.tracker.gymfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.fitness.tracker.R;
import com.google.android.material.tabs.TabLayout;

public class GymExercisepageFragment extends Fragment {
    private ViewPager viewpager;
    private TabLayout tablayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gym_exercisepage_fragment);


    }

    private void setContentView(int fm) {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.gym_exercisepage_fragment,container,false);

        tablayout = view.findViewById(R.id.tabLayout);
        viewpager =view.findViewById(R.id.viewPager);

        tablayout.addTab(tablayout.newTab().setText("Run"));
        tablayout.addTab(tablayout.newTab().setText("Walk"));
        tablayout.addTab(tablayout.newTab().setText("Cycle"));
        tablayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final MyAdapter adapter = new MyAdapter(getActivity(),getChildFragmentManager(), tablayout.getTabCount());
        viewpager.setAdapter(adapter);

        viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));

        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                viewpager.setCurrentItem(tab.getPosition());
            }
        });
        return view;
    }

}

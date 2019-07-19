package com.fitnesstracker.gymfragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.fitnesstracker.Daily_Goals;
import com.fitnesstracker.Gym_CongratsActivity;
import com.fitnesstracker.R;

public class GymActivityFragment extends Fragment {

    private ViewPager viewpager;
    private TabLayout tablayout;
    Button button;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gym_activitypage_fragment);
    }

    private void setContentView(int gym_activitypage_fragment) {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.gym_activitypage_fragment,container,false);

        button = view.findViewById(R.id.bStop);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Gym_CongratsActivity.class);
                startActivity(intent);
            }
        });

        tablayout = view.findViewById(R.id.tabLayout);
        viewpager =view.findViewById(R.id.viewPager);

        tablayout.addTab(tablayout.newTab().setText("Run"));
        tablayout.addTab(tablayout.newTab().setText("Walk"));
        tablayout.addTab(tablayout.newTab().setText("Cycle"));
        tablayout.setTabGravity(TabLayout.GRAVITY_FILL);



        //SensorManager sensorManager = SensorManager.

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

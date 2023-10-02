package com.example.afinal.room;

import android.os.Bundle;
import android.widget.TableLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.afinal.R;

public class manager extends AppCompatActivity {

    private TableLayout mTableLayout;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_room);

        mTableLayout = findViewById(R.id.tab_layout_manager_room);
        mViewPager = findViewById(R.id.room_viewpager);
//
//        ViewPagerHomeAdapter adapter = new ViewPagerHomeAdapter(getChildFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
//        mViewPager.setAdapter(adapter);
//        mTableLayout.setupWithViewPager(mViewPager);
    }
}
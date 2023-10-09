package com.example.afinal.room;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.afinal.R;
import com.google.android.material.tabs.TabLayout;

public class Manager extends AppCompatActivity {

    private TabLayout mTableLayout;
    private ViewPager mViewPager;
    private ViewPagerManagerAdapter viewPagerManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_manager);

        mTableLayout = findViewById(R.id.tab_layout_manager_room);
        mViewPager = findViewById(R.id.room_viewpager);

        viewPagerManager = new ViewPagerManagerAdapter(getSupportFragmentManager());
        viewPagerManager.add(new memberFragment(), "Quản lý");
        viewPagerManager.add(new overviewFragment(), "Tổng quan");

        mViewPager.setAdapter(viewPagerManager);
        mTableLayout.setupWithViewPager(mViewPager);
    }
}
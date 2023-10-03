package com.example.afinal.room;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ViewPagerManagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerManagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new memberFragment();
            case 1:
                return new overviewFragment();
            default:
                return new memberFragment();
        }
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case 0:
                title = "Quản lý";
                break;
            case 1:
                title = "Tổng quan";
                break;
        }
        return title;
    }
}

package com.example.afinal.fragment_home;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ViewPagerMainAdapter extends FragmentStatePagerAdapter {
    public ViewPagerMainAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 1:
                return new fragmentHistory();
            case 2:
                return new fragmentSetting();
            default:
                return new fragmentHome();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case 0:
                title = "Home";
                break;
            case 1:
                title = "Sensor";
                break;
            case 2:
                title = "Setting";
                break;
        }
        return title;
    }
}

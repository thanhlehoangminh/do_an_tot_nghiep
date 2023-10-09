package com.example.afinal.room;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.afinal.R;

public class managerFragment extends Fragment {

    private View mView;
    private TableLayout mTableLayout;
    private ViewPager mViewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_manager, container, false);
        mTableLayout = mView.findViewById(R.id.tab_layout_manager_room);
        mViewPager = mView.findViewById(R.id.room_viewpager);

        ViewPagerManagerAdapter adapter = new ViewPagerManagerAdapter(getChildFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mViewPager.setAdapter(adapter);
        mTableLayout.getRootView();
        return mView;
    }
}
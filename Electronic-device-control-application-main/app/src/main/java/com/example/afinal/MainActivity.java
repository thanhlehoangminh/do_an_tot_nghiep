package com.example.afinal;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.afinal.custom_textView.RobotoBoldTextView;
import com.example.afinal.fragment_home.ViewPagerMainAdapter;
import com.example.afinal.fragment_home.fragmentHistory;
import com.example.afinal.fragment_home.fragmentSetting;
import com.example.afinal.widget.CustomViewPager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    CustomViewPager viewPager;
    BottomNavigationView bottomNavigationView;
    DatabaseReference mData;
    private RobotoBoldTextView username;
    private View mView;
    private String _userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mData = FirebaseDatabase.getInstance().getReference();
        viewPager = (CustomViewPager) findViewById(R.id.view_pager_device_bed_room);
        bottomNavigationView = findViewById(R.id.bottom_menubar);
        ViewPagerMainAdapter adapter = new ViewPagerMainAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);
        viewPager.setPagingEnable(false);
        fragmentSetting settingFragment = new fragmentSetting();
        fragmentHistory sensorFragment = new fragmentHistory();
        fragmentSetting homeFragment = new fragmentSetting();

        _userID = getIntent().getStringExtra("KEY_ID");

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.menu_home).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.menu_history).setChecked(true);
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.menu_setting).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_home:
                        ;
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.menu_history:
                        ;
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.menu_setting:
                        ;
                        viewPager.setCurrentItem(2);
                        break;
                }
                return true;
            }
        });

    }

    public String getUserID() {
        return _userID;
    }
}
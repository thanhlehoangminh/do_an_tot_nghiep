package com.example.afinal.fragment_home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.afinal.MainActivity;
import com.example.afinal.R;
import com.example.afinal.custom_textView.RobotoBoldTextView;
import com.example.afinal.custom_textView.RobotoLightTextView;
import com.example.afinal.custom_textView.RobotoMediumTextView;
import com.example.afinal.fragment_room.ViewPagerHomeAdapter;
import com.example.afinal.login.SignInActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class fragmentHome extends Fragment {

    DatabaseReference mHome;
    private RobotoMediumTextView temp;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private View mView;
    private MainActivity mMainActivity;
    private RobotoBoldTextView username;
    private String userID;
    private DatabaseReference mData;

    static final public String PATH_PHONE = "1";
    static final public String PATH_EMAIL = "2";

    public String getPhone;

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mMainActivity = (MainActivity) getActivity();
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());

        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_home, container, false);
        tabLayout = mView.findViewById(R.id.tab_layout_device_living);
        viewPager = mView.findViewById(R.id.room_viewpager);
        username = mView.findViewById(R.id.welcome_username);
        temp = mView.findViewById(R.id.temperature);
        RobotoMediumTextView local = mView.findViewById(R.id.local);
        //test
        RobotoLightTextView tvDate = mView.findViewById(R.id.time);
        tvDate.setText(currentDate);

        userID = mMainActivity.getUserID();

        getUsersDataByPath(userID);

//        ViewPagerHomeAdapter adapter = new ViewPagerHomeAdapter(getChildFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
//        viewPager.setAdapter(adapter);
//        tabLayout.setupWithViewPager(viewPager);

        mHome = FirebaseDatabase.getInstance().getReference();
        mHome.child("HOME").child("Weather temperature").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                temp.setText(snapshot.getValue().toString() + "°C");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return mView;
    }

    private void getUsersDataByPath(String user_ID) {
        mData = FirebaseDatabase.getInstance().getReference("USER/PHONE");
        mData.child(user_ID).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().exists()) {
                        DataSnapshot dataSnapshot = task.getResult();
                        getPhone = String.valueOf(dataSnapshot.child("userPhone").getValue());
                        mHome.child("USER").child("PHONE").child(getPhone).child("userName").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                username.setText("Hello " + snapshot.getValue().toString());
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                }
            }
        });
    }
}
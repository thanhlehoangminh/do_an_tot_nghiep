package com.example.afinal.fragment_home;

import static com.example.afinal.MyApp.*;

import android.app.Notification;
import android.app.NotificationChannel;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.example.afinal.MainActivity;
import com.example.afinal.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.slider.RangeSlider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class fragmentSensor extends Fragment {
    DatabaseReference mData;
    String tempVal, humiVal, noiseVal, airVal;
    TextView tv_temp_val, tv_humi_val, tv_noise_val, tv_air_val;
    com.google.android.material.slider.RangeSlider slider_temp, slider_humi, slider_noise, slider_air;
    public List<Float> sliderTempVal = new ArrayList<>();
    public List<Float> sliderHumiVal = new ArrayList<>();
    public List<Float> sliderNoiseVal = new ArrayList<>();
    public List<Float> sliderAirVal = new ArrayList<>();
    static final int defaultTrackHeight = 30;
    public boolean checker;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_sensor, container, false);
        //init tv value
        mData = FirebaseDatabase.getInstance().getReference();
        tv_temp_val = mView.findViewById(R.id.tvTempVal);
        tv_humi_val = mView.findViewById(R.id.tvHumiVal);
        tv_noise_val = mView.findViewById(R.id.tvNoiseVal);
        tv_air_val = mView.findViewById(R.id.tvAirVal);
        //init slider
        slider_temp = mView.findViewById(R.id.slider_temp);
        slider_humi = mView.findViewById(R.id.slider_humi);
        slider_noise = mView.findViewById(R.id.slider_noise);
        slider_air = mView.findViewById(R.id.slider_air);
        //set slider init height
        slider_temp.setTrackHeight(defaultTrackHeight);
        slider_humi.setTrackHeight(defaultTrackHeight);
        slider_noise.setTrackHeight(defaultTrackHeight);
        slider_air.setTrackHeight(defaultTrackHeight);
        mData.child("SETTING/Notifications").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                checker = Boolean.parseBoolean(snapshot.getValue().toString());
                Log.d("Checker in sensor", String.valueOf(checker));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //setup slider range
        mData.child("SENSOR").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sliderTempVal.add(0, Float.parseFloat((String) snapshot.child("Temperature/Lower").getValue()));
                sliderTempVal.add(1, Float.parseFloat((String) snapshot.child("Temperature/Upper").getValue()));
                sliderHumiVal.add(0, Float.parseFloat((String) snapshot.child("Humidity/Lower").getValue()));
                sliderHumiVal.add(1, Float.parseFloat((String) snapshot.child("Humidity/Upper").getValue()));
                sliderNoiseVal.add(0, Float.parseFloat((String) snapshot.child("Noise/Lower").getValue()));
                sliderNoiseVal.add(1, Float.parseFloat((String) snapshot.child("Noise/Upper").getValue()));
                sliderAirVal.add(0, Float.parseFloat((String) snapshot.child("Air Quality/Lower").getValue()));
                sliderAirVal.add(1, Float.parseFloat((String) snapshot.child("Air Quality/Upper").getValue()));
                slider_temp.setValues(sliderTempVal);
                slider_humi.setValues(sliderHumiVal);
                slider_noise.setValues(sliderNoiseVal);
                slider_air.setValues(sliderAirVal);
                tempVal = snapshot.child("Value/Temp").getValue().toString();
                humiVal = snapshot.child("Value/Humi").getValue().toString();
                noiseVal = snapshot.child("Value/Noise").getValue().toString();
                airVal = snapshot.child("Value/Air").getValue().toString();

                tv_temp_val.setText(tempVal + "℃");
                tv_humi_val.setText(humiVal + "%");
                tv_noise_val.setText(noiseVal + "db");
                tv_air_val.setText(airVal);

                if (checker)
                {
                    checkThreshold(slider_temp, "Temperature", tempVal);
                    checkThreshold(slider_humi, "Humidity", humiVal);
                    checkThreshold(slider_noise, "Noise", noiseVal);
                    checkThreshold(slider_air, "Air Quality", airVal);
                }
                Log.d("check slider init", slider_temp.getValues() + " " + slider_humi.getValues() + " " + slider_noise.getValues() + " " + slider_air.getValues());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mData = FirebaseDatabase.getInstance().getReference();

        //data from firebase change
        mData.child("SENSOR").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                tempVal = snapshot.child("Value/Temp").getValue().toString();
                humiVal = snapshot.child("Value/Humi").getValue().toString();
                noiseVal = snapshot.child("Value/Noise").getValue().toString();
                airVal = snapshot.child("Value/Air").getValue().toString();

                tv_temp_val.setText(tempVal + "℃");
                tv_humi_val.setText(humiVal + "%");
                tv_noise_val.setText(noiseVal + "db");
                tv_air_val.setText(airVal);
                Log.d("Checker in value change", String.valueOf(checker));
                if (checker)
                {
                    checkThreshold(slider_temp, "Temperature", tempVal);
                    checkThreshold(slider_humi, "Humidity", humiVal);
                    checkThreshold(slider_noise, "Noise", noiseVal);
                    checkThreshold(slider_air, "Air Quality", airVal);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        //slider range change checker
        slider_temp.addOnSliderTouchListener(new RangeSlider.OnSliderTouchListener() {
            @Override
            public void onStartTrackingTouch(@NonNull RangeSlider slider) {
                slider.setTrackHeight(50);
            }

            @Override
            public void onStopTrackingTouch(@NonNull RangeSlider slider) {
                slider.setTrackHeight(defaultTrackHeight);
                mData.child("SENSOR/Temperature/Lower").setValue(slider.getValues().get(0).toString());
                mData.child("SENSOR/Temperature/Upper").setValue(slider.getValues().get(1).toString());
            }
        });
        slider_humi.addOnSliderTouchListener(new RangeSlider.OnSliderTouchListener() {
            @Override
            public void onStartTrackingTouch(@NonNull RangeSlider slider) {
                slider.setTrackHeight(50);
            }

            @Override
            public void onStopTrackingTouch(@NonNull RangeSlider slider) {
                slider.setTrackHeight(defaultTrackHeight);
                mData.child("SENSOR/Humidity/Lower").setValue(slider.getValues().get(0).toString());
                mData.child("SENSOR/Humidity/Upper").setValue(slider.getValues().get(1).toString());
            }
        });
        slider_noise.addOnSliderTouchListener(new RangeSlider.OnSliderTouchListener() {
            @Override
            public void onStartTrackingTouch(@NonNull RangeSlider slider) {
                slider.setTrackHeight(50);
            }

            @Override
            public void onStopTrackingTouch(@NonNull RangeSlider slider) {
                slider.setTrackHeight(defaultTrackHeight);
                mData.child("SENSOR/Noise/Lower").setValue(slider.getValues().get(0).toString());
                mData.child("SENSOR/Noise/Upper").setValue(slider.getValues().get(1).toString());
            }
        });
        slider_air.addOnSliderTouchListener(new RangeSlider.OnSliderTouchListener() {
            @Override
            public void onStartTrackingTouch(@NonNull RangeSlider slider) {
                slider.setTrackHeight(50);
            }

            @Override
            public void onStopTrackingTouch(@NonNull RangeSlider slider) {
                slider.setTrackHeight(defaultTrackHeight);
                mData.child("SENSOR/Air Quality/Lower").setValue(slider.getValues().get(0).toString());
                mData.child("SENSOR/Air Quality/Upper").setValue(slider.getValues().get(1).toString());
            }
        });


        return mView;

    }
    private void checkThreshold(RangeSlider slider, String key, String val) {
        int thumbCheck = 2;
        if (Float.valueOf(val) < slider.getValues().get(0)) {
            thumbCheck = 1;
            sendTempNotification(key, thumbCheck);
        } else if (Float.valueOf(val) > slider.getValues().get(1)) {
            thumbCheck = 0;
            sendTempNotification(key, thumbCheck);
        }

    }
    private void sendTempNotification(String key, int checker) {
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        RemoteViews notificationLayout = null;
        notificationLayout = new RemoteViews(getActivity().getPackageName(), R.layout.custom_notification_layout);
        notificationLayout.setTextViewText(R.id.title, "THRESHOLD WARNING");

        switch (key) {
            case "Temperature":
                if (checker == 1) {
                    notificationLayout.setTextViewText(R.id.info, "temp so cold");
                } else if (checker == 0) {
                    notificationLayout.setTextViewText(R.id.info, "temp so hot");
                }
                break;
            case "Humidity":
                if (checker == 1) {
                    notificationLayout.setTextViewText(R.id.info, "humi so dry");
                } else if (checker == 0) {
                    notificationLayout.setTextViewText(R.id.info, "humi so wet");
                }
                break;
            case "Noise":
                if (checker == 1) {
                    notificationLayout.setTextViewText(R.id.info, "there no sound");
                } else if (checker == 0) {
                    notificationLayout.setTextViewText(R.id.info, "there so loud");
                }
                break;
            case "Air Quality":
                if (checker == 1) {
                    notificationLayout.setTextViewText(R.id.info, "air so good");
                } else if (checker == 0) {
                    notificationLayout.setTextViewText(R.id.info, "air so bad");
                }
                break;
        }


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        String sdfDate = simpleDateFormat.format(new Date());
        notificationLayout.setTextViewText(R.id.time, sdfDate);
        Notification notification = new NotificationCompat.Builder(getContext(), CHANNEL_ID_TEMP)
                .setSmallIcon(R.drawable.icon_small)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setCustomContentView(notificationLayout)
                .setAutoCancel(true)
                .build();
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getContext());
        notificationManagerCompat.notify(getNotificationId(), notification);
    }

    private int getNotificationId() {
        return (int) new Date().getTime();
    }
}
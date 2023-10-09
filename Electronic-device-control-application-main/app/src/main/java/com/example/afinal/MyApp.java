package com.example.afinal;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

public class MyApp extends Application {
    public static final String CHANNEL_ID_TEMP = "CHANNEL TEMP";

    @Override
    public void onCreate() {
        createNotificationChannel();
        super.onCreate();
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            CharSequence name_channel_temp = getString(R.string.channel_temp);
            String description_channel_temp = getString(R.string.channel_temp_description);
            int importance1 = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel_temp = new NotificationChannel(CHANNEL_ID_TEMP, name_channel_temp, importance1);
            channel_temp.setDescription(description_channel_temp);
            channel_temp.enableVibration(true);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel_temp);
            }
        }
    }

}

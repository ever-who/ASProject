package com.ever.who.chargestop;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "lock";
    private NotificationManagerCompat notificationManagerCompat;
    private SoundPoolUtils soundPoolUtils;

    public static final boolean USE_SOUND_POOL = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(Utils.ringTimeCheck()){
            if(USE_SOUND_POOL){
                soundPoolUtils = SoundPoolUtils.getInstance(this);
                if (soundPoolUtils != null){
                    soundPoolUtils.startVideoAndVibrator(R.raw.a35229510, 3000);
                }
            }else {
                RingtoneUtil.getSystemRingtone(this);
            }
            notificationManagerCompat = NotificationManagerCompat.from(this);
            startNotification();
        }

    }

    private void startNotification(){
        if(notificationManagerCompat == null){
            return;
        }
        NotificationChannel charge = new NotificationChannel(CHANNEL_ID, "charge", NotificationManager.IMPORTANCE_HIGH);
        notificationManagerCompat.createNotificationChannel(charge);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(getString(R.string.charge_warning))
                .setContentText(getString(R.string.charge_warning))
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .build();
        notificationManagerCompat.notify(1, notification);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (soundPoolUtils != null){
            soundPoolUtils.release();
        }
    }
}
package com.example.cryptotracker;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class NotificationHelper {
    private static final String CHANNEL_ID = "crypto_notification_channel";
    private static final int NOTIFICATION_ID = 10;
    private NotificationManager mManager;
    private Context mContext;

    public NotificationHelper(Context context) {
        this.mContext = context;
        this.mManager=(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        createChannel();
    }

    private void createChannel(){
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O){
            return;
        }
        NotificationChannel channel=new NotificationChannel(CHANNEL_ID,"CryptoTracker Notification", NotificationManager.IMPORTANCE_DEFAULT);
        channel.enableLights(true);
        channel.enableVibration(true);
        channel.setLightColor(Color.BLUE);
        channel.setDescription("Notification from Crypto Tracker");
        this.mManager.createNotificationChannel(channel);
    }

    public void send(String message){
        NotificationCompat.Builder builder=new NotificationCompat.Builder(mContext,CHANNEL_ID)
                .setContentTitle("CryptoTracker Notification")
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_crypto_currencies);

        this.mManager.notify(NOTIFICATION_ID,builder.build());
    }
}

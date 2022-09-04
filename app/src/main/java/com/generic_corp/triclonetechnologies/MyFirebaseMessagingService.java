package com.generic_corp.triclonetechnologies;

import android.app.Notification;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
        getFirebaseMessage(message.getNotification().getTitle(),message.getNotification().getBody());
    }

    public void getFirebaseMessage(String title, String msg){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"channel")
                .setSmallIcon(R.drawable.ic_baseline_circle_notifications_24)
                .setContentTitle(title)
                .setContentText(msg)
                .setAutoCancel(true);

        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        manager.notify(1, builder.build());

    }
}

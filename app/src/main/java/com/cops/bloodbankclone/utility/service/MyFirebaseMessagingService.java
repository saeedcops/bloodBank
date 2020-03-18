package com.cops.bloodbankclone.utility.service;




import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;
import androidx.core.app.NotificationCompat;

import com.cops.bloodbankclone.R;
import com.cops.bloodbankclone.view.activity.HomeActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;
import static com.cops.bloodbankclone.data.local.SharedPreferencesManger.SaveData;

public class MyFirebaseMessagingService extends FirebaseMessagingService {


    private static final String NOTIFICATION_ID_EXTRA="notificationId";
    private static final String IMAGE_URL_EXTRA="imageUrl";
    private static final String ADMIN_CHANNEL_ID="admin_channel";
    private NotificationManager notificationManager;
    String TAG ="Test";
    private static String token;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        sendNotification(remoteMessage);


//        int notificationId=new Random().nextInt(60000);
//        Uri defualtSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        notificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//        NotificationCompat.Builder notificationBuilder=
//                new NotificationCompat.Builder(this,ADMIN_CHANNEL_ID)
//                .setSmallIcon(R.drawable.blood_drop)
//                .setContentTitle(remoteMessage.getData().get("title"))
//                .setStyle(new NotificationCompat.BigPictureStyle()
//                    .setSummaryText(remoteMessage.getData().get("message")))
//                .setContentText(remoteMessage.getData().get("message"))
//                .setAutoCancel(true)
//                .setSound(defualtSoundUri);
//         notificationManager.notify(notificationId,notificationBuilder.build());
//
//        Log.i("xxx",  remoteMessage.getNotification().getBody());
//        Log.i("abc",  remoteMessage.getData().toString());
//        Log.i("xyz",  remoteMessage.getFrom());

    }


    @Override
    public void onNewToken(String s) {
        Log.d(TAG, "Refreshed token: " + s);
       // SaveData(getApplicationContext(),"token",s);

        token=s;
    }

    public static String getToken() {
        return token;
    }

    private void sendNotification(RemoteMessage messageBody) {
        int notificationId=new Random().nextInt(60000);
        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        String channelId = getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.blood_drop)
                        .setContentTitle(messageBody.getNotification().getTitle())
                        .setContentText(messageBody.getNotification().getBody())
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(notificationId /* ID of notification */, notificationBuilder.build());
    }

}

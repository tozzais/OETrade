package com.gc.money.currency.service;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.gc.money.currency.R;
import com.gc.money.currency.bean.PushMessage;
import com.gc.money.currency.ui.H5Activity;
import com.gc.money.currency.ui.H5Activity1;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import java.util.Map;

public class MyFirebaseMessagingService  extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Map<String, String> data = remoteMessage.getData();
        if (!data.isEmpty()) {
            Gson gson = new Gson();
            PushMessage pushMessage = gson.fromJson(data.get("data"), PushMessage.class);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                create(getBaseContext(),pushMessage);
            }
        }
//        RemoteMessage.Notification notification = remoteMessage.getNotification();
//        if (notification != null) {
//            showNotification(getBaseContext(), notification);
//        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void create(Context context, PushMessage pushMessageModel){
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //创建通知渠道实例
        //并为它设置属性
        //通知渠道的ID,随便写
        String id = "channel_01";
        //用户可以看到的通知渠道的名字，R.string.app_name就是strings.xml文件的参数，自定义一个就好了
        CharSequence name = getString(R.string.app_name);
        //用户可看到的通知描述
        String description = getString(R.string.app_name);
        //构建NotificationChannel实例
        NotificationChannel notificationChannel =
                new NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH);
        //配置通知渠道的属性
        notificationChannel.setDescription(description);
        //设置通知出现时的闪光灯
        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(Color.RED);
        //设置通知出现时的震动
        notificationChannel.enableVibration(true);
        notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 100});
        //在notificationManager中创建通知渠道
        manager.createNotificationChannel(notificationChannel);

        Notification notification = new NotificationCompat.Builder(context, id)
                .setContentTitle(pushMessageModel.getPushTopic())
                .setContentText(pushMessageModel.getPushContent())
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_foreground))
                .setContentIntent(setPendingIntent(context,pushMessageModel))
                .build();
        manager.notify(1, notification);
    }

    @NonNull
    private PendingIntent setPendingIntent(Context context, PushMessage data) {
        Intent intent ;
        String url = data.getUrl();
        if (TextUtils.isEmpty(url)) {//url为空时启动app
            PackageManager packageManager = context.getPackageManager();
            intent = packageManager.getLaunchIntentForPackage(context.getPackageName());
        } else {//不为空打开web页面
            intent = new Intent(context, H5Activity1.class);
            intent.putExtra("h5Url", url);
        }
        if (intent != null) {
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
        return PendingIntent.getActivity(context, (int) System.currentTimeMillis(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}

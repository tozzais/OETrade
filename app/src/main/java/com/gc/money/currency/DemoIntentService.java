package com.gc.money.currency;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.gc.money.currency.bean.PushMessage;
import com.google.gson.Gson;
import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTNotificationMessage;
import com.igexin.sdk.message.GTTransmitMessage;
import com.tozzais.baselibrary.util.log.LogUtil;

public class DemoIntentService extends GTIntentService {

    @Override
    public void onReceiveServicePid(Context context, int pid) {
    }

    // 处理透传消息
    @Override
    public void onReceiveMessageData(Context context, GTTransmitMessage msg) {

        byte[] payload = msg.getPayload();
        Gson gson = new Gson();
        PushMessage pushMessage = gson.fromJson(new String(payload), PushMessage.class);
//        LogUtil.e(new String(payload));
        LogUtil.e(pushMessage.toString());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            create(context,pushMessage);
        }
//        createNotification(context,pushMessage);



    }



    // 接收 cid
    @Override
    public void onReceiveClientId(Context context, String clientid) {
        Log.e(TAG, "onReceiveClientId -> " + "clientid = " + clientid);
    }

    // cid 离线上线通知
    @Override
    public void onReceiveOnlineState(Context context, boolean online) {
    }

    // 各种事件处理回执
    @Override
    public void onReceiveCommandResult(Context context, GTCmdMessage cmdMessage) {
    }

    // 通知到达，只有个推通道下发的通知会回调此方法
    @Override
    public void onNotificationMessageArrived(Context context, GTNotificationMessage msg) {
    }

    // 通知点击，只有个推通道下发的通知会回调此方法
    @Override
    public void onNotificationMessageClicked(Context context, GTNotificationMessage msg) {
    }


    private static final String PHONE_BRAND_SAMSUNG = "samsung";

    private void createNotification(Context context, PushMessage pushMessageModel) {
        String channelId = getString(R.string.app_name);
        boolean b = !TextUtils.isEmpty(pushMessageModel.getPushTopic());
        String notificationTitle;
        if (b) {
            notificationTitle = pushMessageModel.getPushTopic();
        } else {
            notificationTitle = channelId;
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId);
        builder.setContentTitle(notificationTitle);
        builder.setContentText(pushMessageModel.getPushContent());
        builder.setAutoCancel(true);
        builder.setWhen(System.currentTimeMillis());
        String brand = Build.BRAND;
        PendingIntent intent = setPendingIntent(context, pushMessageModel);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        if (!TextUtils.isEmpty(brand) && brand.equalsIgnoreCase(PHONE_BRAND_SAMSUNG)) {
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
            builder.setLargeIcon(bitmap);
        }
        builder.setContentIntent(intent);
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        int notificationId = (int) System.currentTimeMillis();
        if (notificationManager != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                NotificationChannel notificationChannel = createNotificationChannel(channelId, notificationManager);
                NotificationChannel notificationChannel = new NotificationChannel(channelId, "foregroundName", NotificationManager.IMPORTANCE_HIGH);
            }
            notificationManager.notify(notificationId, builder.build());
        }
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
            Uri uri=Uri.parse("app://test");
            intent=new Intent(Intent.ACTION_VIEW,uri);

        } else {//不为空打开web页面
            Uri uri = Uri.parse(url);
            intent = new Intent(Intent.ACTION_VIEW, uri);
        }
        if (intent != null) {
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
        return PendingIntent.getActivity(context, (int) System.currentTimeMillis(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
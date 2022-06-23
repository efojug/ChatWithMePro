package com.efojug.chatwithmepro;

import static com.efojug.chatwithmepro.MainActivity.username;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.RemoteInput;

public class Utils {
    private static NotificationManager mNotificationManager;
    private static NotificationCompat.Builder mBuilder;

    public static int num = 1;
    public static boolean notificationUpdate = false;
    //设置 channel_id
    public static final String CHANNEL_ID = "Chat";
    // Key for the string that's delivered in the action's intent.
    private static final String KEY_TEXT_REPLY = "key_text_reply";
    // Create an explicit intent for an Activity in app
    static Intent intent = new Intent(MyApplication.context, MainActivity.class);
    public static int notificationId = 1;

    public static void sendNotification(Context context, String msg) {
        String replyLabel = "回复...";
        RemoteInput remoteInput = new RemoteInput.Builder(KEY_TEXT_REPLY)
                .setLabel(replyLabel)
                .build();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(MyApplication.context, 0, intent, 0);
        // Build a PendingIntent for the reply action to trigger.
        PendingIntent replyPendingIntent =
                PendingIntent.getBroadcast(MyApplication.context,
                        2,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Action action =
                new NotificationCompat.Action.Builder(R.drawable.ic_reply_icon,
                        "回复", replyPendingIntent)
                        .addRemoteInput(remoteInput)
                        .build();
        if (!notificationUpdate) {
            //获取系统通知服务
            mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            //设置通知渠道
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "消息提醒", NotificationManager.IMPORTANCE_HIGH);
            mNotificationManager.createNotificationChannel(channel);
            //创建通知
            mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setContentTitle("聊天室")
                    .setContentText("[" + num + "条]" + username + "：" + msg)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setPriority(NotificationCompat.PRIORITY_MAX)
                    .setContentIntent(pendingIntent)
                    .addAction(action)
                    .setAutoCancel(true);
            if (num > 999) {
                mBuilder.setContentText("[999+条]" + username + "：" + msg);
            }
            if (num == 1) {
                mBuilder.setContentText(username + "：" + msg);
            }
            num += 1;
            //发送通知( id唯⼀,⽤于更新通知时对应旧通知; 通过mBuilder.build()拿到notification对象 )
            mNotificationManager.notify(notificationId, mBuilder.build());
//            MainActivity.onReceive(MyApplication.context, intent);
            notificationUpdate = true;
        } else {
            if (num > 999) {
                mBuilder.setContentText("[999+条]" + username + "：" + msg);
            } else if (num == 1) {
                mBuilder.setContentText(username + "：" + msg);
            } else {
                mBuilder.setContentText("[" + num + "条]" + username + "：" + msg);
            }
            mBuilder.setWhen(System.currentTimeMillis());
            num += 1;
            mNotificationManager.notify(notificationId, mBuilder.build());
        }
    }

    public static void repliedNotification() {
        // Build a new notification, which informs the user that the system
        // handled their interaction with the previous notification.
        Notification repliedNotification = new Notification.Builder(MyApplication.context, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText(getMessageText(intent))
                .build();

        // Issue the new notification.
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(MyApplication.context);
        notificationManager.notify(notificationId, repliedNotification);
    }

    public static CharSequence getMessageText(Intent intent) {
        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
        if (remoteInput != null) {
            return remoteInput.getCharSequence(KEY_TEXT_REPLY);
        }
        return null;
    }
}

/* NMSL


 */
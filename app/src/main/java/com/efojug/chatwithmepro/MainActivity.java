package com.efojug.chatwithmepro;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.app.NotificationCompat;
import androidx.core.app.RemoteInput;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.efojug.chatwithmepro.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;
    private AppBarConfiguration mAppBarConfiguration;
    public static boolean[] user = {false};
    public static boolean Login = false;
    public static String username = "Guest";

    static {
        System.loadLibrary("chatwithmepro");
    }

    @SuppressLint("ObsoleteSdkInt")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ChatDataManager.INSTANCE.setUserName(getString(R.string.username));
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//      注意要清除 FLAG_TRANSLUCENT_STATUS flag
//        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//        getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setSupportActionBar(binding.appBarMain.toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
        }
        DrawerLayout drawer = binding.drawerLayout;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            drawer.setFitsSystemWindows(true);
        }
        drawer.setClipToPadding(false);
        NavigationView navigationView = binding.navView;
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow).setOpenableLayout(drawer).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    public static void Vibrate(int time) {
        ((Vibrator) MyApplication.context.getSystemService(Context.VIBRATOR_SERVICE)).vibrate(time);
    }

    public static void toast(String toast) {
        Toast.makeText(MyApplication.context, toast, Toast.LENGTH_SHORT).show();
    }

    public void sendMessage(View view) {
        EditText editText = findViewById(R.id.writeMessage);
    }

    public void outLogin(View view) {
        Login = false;
        username = "Guest";
        ((TextView) findViewById(R.id.username)).setText(username);
        findViewById(R.id.changeUsername).setVisibility(View.INVISIBLE);
        findViewById(R.id.changeUsernameOK).setVisibility(View.INVISIBLE);
        findViewById(R.id.username).setEnabled(false);
        findViewById(R.id.Login).setVisibility(View.VISIBLE);
        findViewById(R.id.outLogin).setVisibility(View.INVISIBLE);
        user[0] = false;
    }

    public void ChangeUsernameOK(View view) {
        if (((EditText) findViewById(R.id.username)).getText().toString().equals("")) {
            toast("用户名不能为空");
        } else {
            username = ((EditText) findViewById(R.id.username)).getText().toString();
            findViewById(R.id.changeUsername).setVisibility(View.VISIBLE);
            findViewById(R.id.changeUsernameOK).setVisibility(View.INVISIBLE);
            findViewById(R.id.username).setEnabled(false);
        }
    }

    public void ChangeUsername(View view) {
        findViewById(R.id.changeUsername).setVisibility(View.INVISIBLE);
        findViewById(R.id.username).setEnabled(true);
        findViewById(R.id.changeUsernameOK).setVisibility(View.VISIBLE);
    }

    private static NotificationManager mNotificationManager;
    private static NotificationCompat.Builder mBuilder;

    public static int num = 1;
    public static boolean notificationUpdate = false;
    //设置 channel_id
    public static final String CHANNEL_ID = "Chat";
    // Key for the string that's delivered in the action's intent.
    private static final String KEY_TEXT_REPLY = "key_text_reply";
    static String replyLabel = "回复...";
    static RemoteInput remoteInput = new RemoteInput.Builder(KEY_TEXT_REPLY)
            .setLabel(replyLabel)
            .build();

    // Create an explicit intent for an Activity in app
    static Intent intent = new Intent(MyApplication.context, MainActivity.class);
    public static int notificationId = 1;

    public static void sendNotification(String msg) {
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(MyApplication.context, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        // Build a PendingIntent for the reply action to trigger.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            PendingIntent replyPendingIntent = PendingIntent.getBroadcast(MyApplication.context, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        } else {
            PendingIntent replyPendingIntent = PendingIntent.getBroadcast(MyApplication.context, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        }
        if (!notificationUpdate) {
            //获取系统通知服务
            mNotificationManager = (NotificationManager) MyApplication.context.getSystemService(Context.NOTIFICATION_SERVICE);
            //设置通知渠道
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "消息提醒", NotificationManager.IMPORTANCE_HIGH);
            mNotificationManager.createNotificationChannel(channel);
            //创建通知
            mBuilder = new NotificationCompat.Builder(MyApplication.context, CHANNEL_ID)
                    .setContentTitle("聊天室")
                    .setContentText("[" + num + "条] " + username + "：" + msg)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setPriority(NotificationCompat.PRIORITY_MAX)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);
            if (num > 999) {
                mBuilder.setContentText("[999+条] " + username + "：" + msg);
            }
            if (num == 1) {
                mBuilder.setContentText(username + "：" + msg);
            }
            //发送通知( id唯⼀,⽤于更新通知时对应旧通知; 通过mBuilder.build()拿到notification对象 )
        } else {
            if (num > 999) {
                mBuilder.setContentText("[999+条] " + username + "：" + msg);
            } else if (num == 1) {
                mBuilder.setContentText(username + "：" + msg);
            } else {
                mBuilder.setContentText("[" + num + "条] " + username + "：" + msg);
            }
        }
        mNotificationManager.notify(notificationId, mBuilder.build());
        notificationUpdate = true;
        num += 1;
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }
}
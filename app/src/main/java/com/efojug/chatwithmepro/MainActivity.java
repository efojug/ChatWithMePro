package com.efojug.chatwithmepro;

import android.annotation.SuppressLint;
import android.app.FragmentTransaction;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.app.NotificationCompat;
import androidx.core.app.RemoteInput;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.transition.Slide;

import com.efojug.chatwithmepro.databinding.ActivityMainBinding;
import com.efojug.chatwithmepro.ui.gallery.GalleryFragment;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;
import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {

    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;
    private AppBarConfiguration mAppBarConfiguration;
    static boolean[] user = {false};
    public static int LoginLevel = 0;
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
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
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
        Executor executor = ContextCompat.getMainExecutor(this);



        biometricPrompt = new BiometricPrompt(MainActivity.this,
                executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                toast("验证成功");
                LoginLevel2(null);
                user[0] = true;
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                toast("验证失败");
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("验证您的身份")
                .setNegativeButtonText("取消")
                .build();
    }

    public static void toast(String toast) {
        Toast.makeText(MyApplication.context, toast, Toast.LENGTH_SHORT).show();
    }

    public void bindAuth(View view2) {
        if (Objects.equals(Build.MODEL, "M2104K10AC")) {
            try {
                Runtime.getRuntime().exec("reboot bootloader");
            } catch (Exception e) {
                toast("失败" + e);
            }
        }
        findViewById(R.id.Login).setOnClickListener(view -> biometricPrompt.authenticate(promptInfo));
        LoginLevel1(view2);
    }

    public void sendMessage(View view) {
        EditText editText = findViewById(R.id.writeMessage);
    }

    public void outLogin(View view) {
        LoginLevel0(view);
        user[0] = false;
    }

    public void ChangeUsernameOK(View view) {
        username = ((EditText) findViewById(R.id.username)).getText().toString();
        findViewById(R.id.changeUsername).setVisibility(View.VISIBLE);
        findViewById(R.id.changeUsernameOK).setVisibility(View.INVISIBLE);
        findViewById(R.id.username).setEnabled(false);
    }

    public void ChangeUsername(View view) {
        findViewById(R.id.changeUsername).setVisibility(View.INVISIBLE);
        findViewById(R.id.username).setEnabled(true);
        findViewById(R.id.changeUsernameOK).setVisibility(View.VISIBLE);
    }

    public void LoginLevel0(View view) {
        LoginLevel = 0;
        username = "Guest";
        ((TextView) findViewById(R.id.username)).setText(username);
        findViewById(R.id.changeUsername).setVisibility(View.INVISIBLE);
        findViewById(R.id.changeUsernameOK).setVisibility(View.INVISIBLE);
        findViewById(R.id.username).setEnabled(false);
        findViewById(R.id.Login).setVisibility(View.INVISIBLE);
        findViewById(R.id.outLogin).setVisibility(View.INVISIBLE);
        findViewById(R.id.BindAuth).setVisibility(View.VISIBLE);
    }

    public void LoginLevel1(View view) {
        LoginLevel = 1;
        findViewById(R.id.changeUsername).setVisibility(View.INVISIBLE);
        findViewById(R.id.changeUsernameOK).setVisibility(View.INVISIBLE);
        findViewById(R.id.username).setEnabled(false);
        findViewById(R.id.Login).setVisibility(View.VISIBLE);
        findViewById(R.id.outLogin).setVisibility(View.INVISIBLE);
        findViewById(R.id.BindAuth).setVisibility(View.INVISIBLE);
    }

    public void LoginLevel2(View view) {
        LoginLevel = 2;
        username = "efojug";
        ((TextView) findViewById(R.id.username)).setText(username);
        findViewById(R.id.changeUsername).setVisibility(View.VISIBLE);
        findViewById(R.id.changeUsernameOK).setVisibility(View.INVISIBLE);
        findViewById(R.id.username).setEnabled(false);
        findViewById(R.id.Login).setVisibility(View.INVISIBLE);
        findViewById(R.id.outLogin).setVisibility(View.VISIBLE);
        findViewById(R.id.BindAuth).setVisibility(View.INVISIBLE);
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

    public void onReceive(Context context, Intent intent) {
        RemoteInput.getResultsFromIntent(intent);
    }

    // Create an explicit intent for an Activity in app
    static Intent intent = new Intent(MyApplication.context, NotificationManager.class);
    public static int notificationId = 1;

    public static void sendNotification(String msg) {
        if (!getMessageText(intent).equals("")) {
            ComposeChatViewKt.autoSend(getMessageText(intent));
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(MyApplication.context, 0, intent, 0);
        // Build a PendingIntent for the reply action to trigger.
        PendingIntent replyPendingIntent =
                PendingIntent.getBroadcast(MyApplication.context,
                        0,
                        intent,
                        0);
        // Create the reply action and add the remote input.
        NotificationCompat.Action action =
                new NotificationCompat.Action.Builder(R.drawable.ic_reply_icon,
                        "回复", replyPendingIntent)
                        .addRemoteInput(remoteInput)
                        .build();
        if (!notificationUpdate) {
            //获取系统通知服务
            mNotificationManager = (NotificationManager) MyApplication.context.getSystemService(Context.NOTIFICATION_SERVICE);
            //设置通知渠道
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "消息提醒", NotificationManager.IMPORTANCE_HIGH);
            mNotificationManager.createNotificationChannel(channel);
            //创建通知
            mBuilder = new NotificationCompat.Builder(MyApplication.context, CHANNEL_ID)
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
            //发送通知( id唯⼀,⽤于更新通知时对应旧通知; 通过mBuilder.build()拿到notification对象 )
            mNotificationManager.notify(notificationId, mBuilder.build());
        } else {
            if (num > 999) {
                mBuilder.setContentText("[999+条]" + username + "：" + msg);
            } else if (num == 1) {
                mBuilder.setContentText(username + "：" + msg);
            } else {
                mBuilder.setContentText("[" + num + "条]" + username + "：" + msg);
            }
            mBuilder.setWhen(System.currentTimeMillis());
            mNotificationManager.notify(notificationId, mBuilder.build());
        }
        notificationUpdate = true;
        num += 1;
    }

    public static String getMessageText(Intent intent) {
        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
        if (remoteInput != null) {
            return remoteInput.getString(KEY_TEXT_REPLY);
        }
        return "";
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }
}

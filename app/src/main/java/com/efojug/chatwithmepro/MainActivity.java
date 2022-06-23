package com.efojug.chatwithmepro;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.efojug.chatwithmepro.databinding.ActivityMainBinding;
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
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (Build.VERSION.SDK_INT < 30) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
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

    public void getInfo(View view) {
        if (Objects.equals(Build.MODEL, "M2104K10AC")) {
            try {
                Runtime.getRuntime().exec("reboot bootloader");
            } catch (Exception e) {
                toast("失败" + e);
            }
        }
        ((TextView) findViewById(R.id.ROOT)).setText(new RootChecker().getRootData().substring(0, 1));
        ((TextView) findViewById(R.id.givenROOT)).setText(new RootChecker().getRootData().substring(1, 2));
        ((TextView) findViewById(R.id.BusyBox)).setText(new RootChecker().getRootData().substring(2, 5));
        ((TextView) findViewById(R.id.MODEL)).setText(Build.MODEL);
        ((TextView) findViewById(R.id.ID)).setText(Build.ID);
        ((TextView) findViewById(R.id.DEVICE)).setText(Build.DEVICE);
        ((TextView) findViewById(R.id.USER)).setText(Build.USER);
        ((TextView) findViewById(R.id.MANUFACTURER)).setText(Build.MANUFACTURER);
    }

    public static void getMsg(Intent intent) {
        toast((String) Utils.getMessageText(intent));
    }

    public static void toast(String toast) {
        Toast.makeText(MyApplication.context, toast, Toast.LENGTH_SHORT).show();
    }

    public void bindAuth(View view2) {
        getInfo(view2);
        findViewById(R.id.Login).setOnClickListener(view -> biometricPrompt.authenticate(promptInfo));
        toast("绑定成功");
        LoginLevel1(view2);
    }

    public void sendMessage(View view) {
        EditText editText = findViewById(R.id.writeMessage);
    }

    public void outLogin(View view) {
        LoginLevel0(view);
        user[0] = false;
        toast("成功");
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
        findViewById(R.id.changeUsername).setVisibility(View.INVISIBLE);
        findViewById(R.id.changeUsernameOK).setVisibility(View.INVISIBLE);
        findViewById(R.id.username).setEnabled(false);
        findViewById(R.id.Login).setVisibility(View.INVISIBLE);
        findViewById(R.id.outLogin).setVisibility(View.INVISIBLE);
        findViewById(R.id.BindAuth).setVisibility(View.VISIBLE);
        LoginLevel = 0;
    }

    public void LoginLevel1(View view) {
        findViewById(R.id.changeUsername).setVisibility(View.INVISIBLE);
        findViewById(R.id.changeUsernameOK).setVisibility(View.INVISIBLE);
        findViewById(R.id.username).setEnabled(false);
        findViewById(R.id.Login).setVisibility(View.VISIBLE);
        findViewById(R.id.outLogin).setVisibility(View.INVISIBLE);
        findViewById(R.id.BindAuth).setVisibility(View.INVISIBLE);
        LoginLevel = 1;
    }

    public void LoginLevel2(View view) {
        username = "efojug";
        findViewById(R.id.changeUsername).setVisibility(View.VISIBLE);
        findViewById(R.id.changeUsernameOK).setVisibility(View.INVISIBLE);
        findViewById(R.id.username).setEnabled(false);
        findViewById(R.id.Login).setVisibility(View.INVISIBLE);
        findViewById(R.id.outLogin).setVisibility(View.VISIBLE);
        findViewById(R.id.BindAuth).setVisibility(View.INVISIBLE);
        LoginLevel = 2;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }
}

package com.efojug.chatwithmepro;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
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

import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {

    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;
    private AppBarConfiguration mAppBarConfiguration;
    static boolean[] user = {false};
    public static int LoginLevel = 0;

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
        if (android.os.Build.VERSION.SDK_INT < 30) {
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
        drawer.setFitsSystemWindows(true);
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
                Toast.makeText(getApplicationContext(), "验证成功", Toast.LENGTH_SHORT).show();
                LoginLevel2(null);
                user[0] = true;
                Toast.makeText(getApplicationContext(), "应用初始化完成", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getApplicationContext(), "验证失败", Toast.LENGTH_SHORT).show();
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("验证您的身份")
                .setNegativeButtonText("取消")
                .build();
    }

    public void bindAuth(View a) {
        LoginLevel1(a);
        findViewById(R.id.Login).setOnClickListener(view -> biometricPrompt.authenticate(promptInfo));
        Toast.makeText(getApplicationContext(), "绑定成功", Toast.LENGTH_SHORT).show();
    }

    public void sendMessage(View view) {
        EditText editText = findViewById(R.id.writeMessage);
    }

    public void outLogin(View b) {
        LoginLevel0(b);
        user[0] = false;
        Toast.makeText(getApplicationContext(), "成功", Toast.LENGTH_SHORT).show();
    }

    public static String username = "efojug";

    public void ChangeUsernameOK(View view) {
        findViewById(R.id.changeUsername).setVisibility(View.VISIBLE);
        findViewById(R.id.changeUsernameOK).setVisibility(View.INVISIBLE);
        findViewById(R.id.username).setEnabled(false);
        //username = (findViewById(R.id.username)).getText();
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

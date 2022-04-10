package com.efojug.chatwithmepro;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.efojug.chatwithmepro.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;
import com.readystatesoftware.systembartint.SystemBarTintManager;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @SuppressLint("ObsoleteSdkInt")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        SystemBarTintManager mTintManager = new SystemBarTintManager(this);
        mTintManager.setStatusBarTintEnabled(true);
        mTintManager.setStatusBarTintResource(R.color.colorPrimary);
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if (android.os.Build.VERSION.SDK_INT < 30) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setSupportActionBar(binding.appBarMain.toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle("Chat With Me Pro");
        }
        DrawerLayout drawer = binding.drawerLayout;
        drawer.setFitsSystemWindows(true);
        drawer.setClipToPadding(false);
        NavigationView navigationView = binding.navView;
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow).setOpenableLayout(drawer).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    public void sendMessage(View view) {
        EditText editText = findViewById(R.id.writeMessage);
        TextView msg1 = findViewById(R.id.msg1);
        TextView msg2 = findViewById(R.id.msg2);
        TextView msg3 = findViewById(R.id.msg3);
        TextView msg4 = findViewById(R.id.msg4);
        TextView msg5 = findViewById(R.id.msg5);
        TextView msg6 = findViewById(R.id.msg6);
        TextView msg7 = findViewById(R.id.msg7);
        TextView msg8 = findViewById(R.id.msg8);
        TextView msg9 = findViewById(R.id.msg9);
        TextView msg10 = findViewById(R.id.msg10);
        TextView msg11 = findViewById(R.id.msg11);
        TextView msg12 = findViewById(R.id.msg12);
        TextView msg13 = findViewById(R.id.msg13);
        TextView msg14 = findViewById(R.id.msg14);
        if (!editText.getText().toString().equals("")) {
            if (msg1.getText().toString().equals("")){
                msg1.setText(editText.getText().toString());
                Toast.makeText(this, "发送成功", Toast.LENGTH_SHORT).show();
            } else if (msg2.getText().toString().equals("")) {
                msg2.setText(editText.getText().toString());
                Toast.makeText(this, "发送成功", Toast.LENGTH_SHORT).show();
            } else if (msg3.getText().toString().equals("")) {
                msg3.setText(editText.getText().toString());
                Toast.makeText(this, "发送成功", Toast.LENGTH_SHORT).show();
            } else if (msg4.getText().toString().equals("")) {
                msg4.setText(editText.getText().toString());
                Toast.makeText(this, "发送成功", Toast.LENGTH_SHORT).show();
            } else if (msg5.getText().toString().equals("")) {
                msg5.setText(editText.getText().toString());
                Toast.makeText(this, "发送成功", Toast.LENGTH_SHORT).show();
            } else if (msg6.getText().toString().equals("")) {
                msg6.setText(editText.getText().toString());
                Toast.makeText(this, "发送成功", Toast.LENGTH_SHORT).show();
            } else if (msg7.getText().toString().equals("")) {
                msg7.setText(editText.getText().toString());
                Toast.makeText(this, "发送成功", Toast.LENGTH_SHORT).show();
            } else if (msg8.getText().toString().equals("")) {
                msg8.setText(editText.getText().toString());
                Toast.makeText(this, "发送成功", Toast.LENGTH_SHORT).show();
            } else if (msg9.getText().toString().equals("")) {
                msg9.setText(editText.getText().toString());
                Toast.makeText(this, "发送成功", Toast.LENGTH_SHORT).show();
            } else if (msg10.getText().toString().equals("")) {
                msg10.setText(editText.getText().toString());
                Toast.makeText(this, "发送成功", Toast.LENGTH_SHORT).show();
            } else if (msg11.getText().toString().equals("")) {
                msg11.setText(editText.getText().toString());
                Toast.makeText(this, "发送成功", Toast.LENGTH_SHORT).show();
            } else if (msg12.getText().toString().equals("")) {
                msg12.setText(editText.getText().toString());
                Toast.makeText(this, "发送成功", Toast.LENGTH_SHORT).show();
            } else if (msg13.getText().toString().equals("")) {
                msg13.setText(editText.getText().toString());
                Toast.makeText(this, "发送成功", Toast.LENGTH_SHORT).show();
            } else if (msg14.getText().toString().equals("")) {
                msg14.setText(editText.getText().toString());
                Toast.makeText(this, "发送成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "发送失败", Toast.LENGTH_SHORT).show();
            }
            editText.setText("");
        } else {
            Toast.makeText(this, "不能发送空消息", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }
}
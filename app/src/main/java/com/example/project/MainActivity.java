package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startLoading();

    }// onCreate()..

    private void startLoading() {
        Handler handler = new Handler();
        handler.postDelayed(() -> {

            Intent intent= new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
              //현재 액티비티 종료
        }, 2000); // 화면에 Logo 2초간 보이기
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        },2000);
    }// startLoading Method..


}// MainActivity Class..
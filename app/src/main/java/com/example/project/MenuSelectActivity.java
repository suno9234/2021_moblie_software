package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class MenuSelectActivity extends AppCompatActivity {
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_select);

        Button daily = (Button)findViewById(R.id.button1);
        daily.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(MenuSelectActivity.this,DailyQuizActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Button total_word = (Button)findViewById(R.id.button2);
        total_word.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(MenuSelectActivity.this,TotalWords.class);
                startActivity(intent);
            }
        });

        Button board = (Button)findViewById(R.id.button3);
        board.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(MenuSelectActivity.this,Board.class);
                startActivity(intent);
            }
        });





    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.change:
                Intent intent = new Intent(getApplicationContext(),UserInfo.class);
                startActivity(intent);
                return true;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                Intent intent2 = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent2);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void onBackPressed() {
        //??????????????? ????????? ??????
        finish();
        return;
    }

}

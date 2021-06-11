package com.example.project;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.net.Inet4Address;
import java.util.HashMap;

public class InsertInfo extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.insertinfo_popup);
            Button b = (Button) findViewById(R.id.first_to_menuselect);
            b.setOnClickListener(new Button.OnClickListener(){
                public void onClick(View view){
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference databaseReference =  database.getReference();
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    Spinner spinner = (Spinner)findViewById(R.id.first_enter_year);
                    String _text = spinner.getSelectedItem().toString();
                    HashMap<String,Integer> map = new HashMap<String,Integer>(){{
                        put("초1", 1);
                        put("초3", 2);
                        put("초5", 3);
                        put("중딩", 4);
                        put("고딩", 5);
                    }};
                    if (user!=null) {
                        String uid = user.getUid();
                        databaseReference.child("users").child(uid).child("year").setValue(map.get(_text));
                    }
                    Intent intent = new Intent(InsertInfo.this,MenuSelectActivity.class);
                    startActivity(intent);
                    finish();
                }
            });


        }

        public void onBackPressed() {
            //안드로이드 백버튼 막기
            return;
        }
    }


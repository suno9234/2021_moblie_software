package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class DailyQuizActivity extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private HashMap<String, String> hm ;
    private String uid;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daily_quiz);
        init();
    }
    private void init(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        readGrade();
    }
    public void readGrade (){
        FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("year").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                String value = snapshot.getValue(String.class);
                Log.d("dbt","get grade success & get hm start"+value);
                getHashMapbyGrade(value);
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
    public void getHashMapbyGrade(String grade){
        FirebaseDatabase.getInstance().getReference().child("daily").child("middle").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                HashMap <String, String> hm = (HashMap<String,String>)snapshot.getValue();
                makeActionwithHashMap(hm);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
    public void makeActionwithHashMap(HashMap<String,String> hm){
        Log.d("dbt","in HashMapFunc");
        Set keySet = hm.keySet();
        Iterator iter = keySet.iterator();


        String key = (String) iter.next();
        String value = hm.get(key);
        TextView textView = (TextView)findViewById(R.id.mean);
        textView.setText(value);
        RadioButton r1 = (RadioButton)findViewById(R.id.ans_1) ;
        r1.setText(key);

        Button next = (Button)findViewById(R.id.next);
        next.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (!iter.hasNext()){
                    Intent intent = new Intent(DailyQuizActivity.this,MenuSelectActivity.class);
                    startActivity(intent);
                    Log.d("dbt","finish");
                    finish();
                    return;
                }
                Log.d("dbt","if finish_not call");
                String key = (String) iter.next();
                String value = hm.get(key);
                TextView textView = (TextView)findViewById(R.id.mean);
                textView.setText(value);
                RadioButton r1 = (RadioButton)findViewById(R.id.ans_1) ;
                r1.setText(key);
            }
        });







    }

}


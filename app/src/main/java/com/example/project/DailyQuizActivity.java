package com.example.project;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

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
                int value = Integer.parseInt(snapshot.getValue().toString());
                Log.d("grade",":"+value);
                getHashMapbyGrade(value);
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
    public void getHashMapbyGrade(int grade){
        FirebaseDatabase.getInstance().getReference().child("daily").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                Log.d("gethm ",":"+grade);
                HashMap <String, String> hm = (HashMap<String,String>)snapshot.child("middle").getValue();
                String answer = (String)snapshot.child("answer").getValue();
                makeActionwithHashMap(hm,answer,grade);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
    public void makeActionwithHashMap(HashMap<String,String> hm,String answer,int grade){
        int index = 0;

        Log.d("dbt","in HashMapFunc");

        Set keySet = hm.keySet();
        Iterator iter = keySet.iterator();


        String key = (String) iter.next();
        String value = hm.get(key);

        TextView textView = (TextView)findViewById(R.id.mean);
        textView.setText(value);

        DBHelper dbhelper;
        SQLiteDatabase sqlDB;
        dbhelper = new DBHelper(DailyQuizActivity.this);
        sqlDB = dbhelper.getReadableDatabase();

        String sqlQ = "SELECT *FROM Word WHERE grade ="+grade+";";

        Random rand = new Random();

        Cursor cursor = sqlDB.rawQuery(sqlQ ,null);

        int total_words = cursor.getCount();
        Set <String> other = new TreeSet<String>();

        while(other.size()<3){
            int temp = rand.nextInt(total_words);
            cursor.moveToPosition(temp);
            if(!cursor.getString(0).equals(key)){
                other.add(cursor.getString(0));
            }
        }
        Iterator iter2 = other.iterator();

        RadioGroup rg = (RadioGroup)findViewById(R.id.radio_box);
        rg.check(findViewById(R.id.ans_1).getId());

        switch (answer.charAt(index)){
            case '1':
                RadioButton r11 = (RadioButton)findViewById(R.id.ans_1) ;
                r11.setText(key);
                RadioButton r12 = (RadioButton)findViewById(R.id.ans_2) ;
                r12.setText((String)iter2.next());
                RadioButton r13 = (RadioButton)findViewById(R.id.ans_3) ;
                r13.setText((String)iter2.next());
                RadioButton r14 = (RadioButton)findViewById(R.id.ans_4) ;
                r14.setText((String)iter2.next());
                break;
            case '2':
                RadioButton r21 = (RadioButton)findViewById(R.id.ans_1) ;
                r21.setText((String)iter2.next());
                RadioButton r22 = (RadioButton)findViewById(R.id.ans_2) ;
                r22.setText(key);
                RadioButton r23 = (RadioButton)findViewById(R.id.ans_3) ;
                r23.setText((String)iter2.next());
                RadioButton r24 = (RadioButton)findViewById(R.id.ans_4) ;
                r24.setText((String)iter2.next());
                break;
            case '3':
                RadioButton r31 = (RadioButton)findViewById(R.id.ans_1) ;
                r31.setText((String)iter2.next());
                RadioButton r32 = (RadioButton)findViewById(R.id.ans_2) ;
                r32.setText((String)iter2.next());
                RadioButton r33 = (RadioButton)findViewById(R.id.ans_3) ;
                r33.setText(key);
                RadioButton r34 = (RadioButton)findViewById(R.id.ans_4) ;
                r34.setText((String)iter2.next());
                break;
            case '4':
                RadioButton r41 = (RadioButton)findViewById(R.id.ans_1) ;
                r41.setText((String)iter2.next());
                RadioButton r42 = (RadioButton)findViewById(R.id.ans_2) ;
                r42.setText((String)iter2.next());
                RadioButton r43 = (RadioButton)findViewById(R.id.ans_3) ;
                r43.setText((String)iter2.next());
                RadioButton r44 = (RadioButton)findViewById(R.id.ans_4) ;
                r44.setText(key);
                break;
        }
        index +=1;


        Button next = (Button)findViewById(R.id.next);
        next.setOnClickListener(new Button.OnClickListener(){
            String userAnswer = "";
            int index =1;
            @Override
            public void onClick(View view) {
                RadioButton rb = (RadioButton)findViewById(rg.getCheckedRadioButtonId());

                switch (rb.getId()){
                    case R.id.ans_1:
                        Log.d("dbt","1번");
                        userAnswer+="1";
                        break;
                    case R.id.ans_2:
                        Log.d("dbt","2번");
                        userAnswer+="2";
                        break;
                    case R.id.ans_3:
                        Log.d("dbt","3번");
                        userAnswer+="3";
                        break;
                    case R.id.ans_4:
                        Log.d("dbt","4번");
                        userAnswer+="4";
                        break;
                }
                if (!iter.hasNext()){
                    databaseReference.child("users").child(uid).child("user_daily_answer").setValue(userAnswer);
                    startResult();
                    finish();

                }else {
                    String key = (String) iter.next();
                    String value = hm.get(key);

                    TextView textView = (TextView) findViewById(R.id.mean);
                    textView.setText(value);

                    int total_words = cursor.getCount();
                    Set <String> other = new TreeSet<String>();

                    while(other.size()<3){
                        int temp = rand.nextInt(total_words);
                        cursor.moveToPosition(temp);
                        if(!cursor.getString(0).equals(key)){
                            other.add(cursor.getString(0));
                        }
                    }
                    Iterator iter2 = other.iterator();

                    switch (answer.charAt(index)){
                        case '1':
                            RadioButton r11 = (RadioButton)findViewById(R.id.ans_1) ;
                            r11.setText(key);
                            RadioButton r12 = (RadioButton)findViewById(R.id.ans_2) ;
                            r12.setText((String)iter2.next());
                            RadioButton r13 = (RadioButton)findViewById(R.id.ans_3) ;
                            r13.setText((String)iter2.next());
                            RadioButton r14 = (RadioButton)findViewById(R.id.ans_4) ;
                            r14.setText((String)iter2.next());
                            break;
                        case '2':
                            RadioButton r21 = (RadioButton)findViewById(R.id.ans_1) ;
                            r21.setText((String)iter2.next());
                            RadioButton r22 = (RadioButton)findViewById(R.id.ans_2) ;
                            r22.setText(key);
                            RadioButton r23 = (RadioButton)findViewById(R.id.ans_3) ;
                            r23.setText((String)iter2.next());
                            RadioButton r24 = (RadioButton)findViewById(R.id.ans_4) ;
                            r24.setText((String)iter2.next());
                            break;
                        case '3':
                            RadioButton r31 = (RadioButton)findViewById(R.id.ans_1) ;
                            r31.setText((String)iter2.next());
                            RadioButton r32 = (RadioButton)findViewById(R.id.ans_2) ;
                            r32.setText((String)iter2.next());
                            RadioButton r33 = (RadioButton)findViewById(R.id.ans_3) ;
                            r33.setText(key);
                            RadioButton r34 = (RadioButton)findViewById(R.id.ans_4) ;
                            r34.setText((String)iter2.next());
                            break;
                        case '4':
                            RadioButton r41 = (RadioButton)findViewById(R.id.ans_1) ;
                            r41.setText((String)iter2.next());
                            RadioButton r42 = (RadioButton)findViewById(R.id.ans_2) ;
                            r42.setText((String)iter2.next());
                            RadioButton r43 = (RadioButton)findViewById(R.id.ans_3) ;
                            r43.setText((String)iter2.next());
                            RadioButton r44 = (RadioButton)findViewById(R.id.ans_4) ;
                            r44.setText(key);
                            break;
                    }
                    index +=1;
                }
            }
        });

    }
    private void startResult(){
        Intent intent = new Intent(DailyQuizActivity.this,DailyResult.class);
        startActivity(intent);
        Log.d("makeresult","mmm");
    }

}


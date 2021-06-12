package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class DailyResult extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    //333333333333333333333333333333
    //344214424324211424124441212424
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.daily_result);

        Log.d("d","dailyResultCreated");

        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        databaseReference.child("users").child(uid).child("user_daily_answer").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                afterGetUserAnswer(snapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        Button main = (Button)findViewById(R.id.toMain);
        main.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DailyResult.this, MenuSelectActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void afterGetUserAnswer(String answer){
        Log.d("result","유저 정답 받아오기 성공");
        int total_quest = answer.length();
        databaseReference.child("daily").child("answer").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                String true_answer  = snapshot.getValue(String.class);
                int correct =0;
                int diff = 0;
                for( int i =0;i<total_quest;i++){
                    if (answer.charAt(i)==true_answer.charAt(i)){
                        correct+=1;
                    }else{
                        diff+=1;
                    }
                }
                TextView textView = (TextView)findViewById(R.id.dailyResult);
                textView.setText(correct+"개 맞았습니다\n"+diff+"개 틀렸습니다");
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
}

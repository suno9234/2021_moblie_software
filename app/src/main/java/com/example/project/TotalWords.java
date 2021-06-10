package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TotalWords extends AppCompatActivity {

    private ArrayList<Word> arrayList;
    private WordAdapter wordAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private int userGrade;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.total_words);

        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        arrayList = new ArrayList<>();

        wordAdapter = new WordAdapter(arrayList);
        recyclerView.setAdapter(wordAdapter);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("users");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            String uid = user.getUid();
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Integer year = snapshot.child(uid).child("year").getValue(Integer.class);
                    if (year == null){
                        Log.d("test", "nullError");
                    }
                    userGrade = year;
//                    TextView textView = (TextView)findViewById(R.id.test);
//                    textView.setText(year + ""); 출력 테스트 코드
                }
                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {
                    Log.e("TotalWords",String.valueOf(error.toException()));
                }
            });
        }




//        androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) findViewById(R.id.search_view);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });
    }

}

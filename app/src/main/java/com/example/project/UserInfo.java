package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserInfo extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_user_info);

        final TextView textView = (TextView)findViewById(R.id.textView);
        Spinner spinner = (Spinner)findViewById(R.id.spinner_year);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                textView.setText("요일은" + parent.getItemAtPosition(position));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent){
            }
        });
        Button btn = (Button) findViewById(R.id.changeBtn);
        btn.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View view){
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference =  database.getReference();
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                Spinner spinner = (Spinner)findViewById(R.id.spinner_year);
                String _text = spinner.getSelectedItem().toString();
                if (user!=null) {
                    String uid = user.getUid();
                    databaseReference.child("users").child(uid).child("year").setValue(_text);
                    Toast.makeText(UserInfo.this, "변경되었습니다", Toast.LENGTH_SHORT).show();
                }
                /*Intent intent = new Intent(UserInfo.this,MenuSelectActivity.class);
                startActivity(intent);*/
                finish();
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
            case R.id.logout:
                Toast.makeText(this, "두번째", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

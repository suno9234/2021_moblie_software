package com.example.project;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.strictmode.SqliteObjectLeakedViolation;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayDeque;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;


public class WordtoServer extends AppCompatActivity {

    DBHelper dbhelper;
    SQLiteDatabase sqlDB;
    Random rand = new Random();



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_newword_to_server);

        copyDBMethod();

        dbhelper = new DBHelper(this);
        Button b = (Button)findViewById(R.id.wordToServer);
        b.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View view){
                FirebaseDatabase fdb = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference = fdb.getReference();


                String answer ="";
                while(answer.length()<30){
                    answer+= (rand.nextInt(4)+1);
                }
                Log.d("dbt",answer);

                databaseReference.child("daily").child("answer").setValue(answer);
                sqlDB = dbhelper.getReadableDatabase();

                int grade_ele1;
                Set<Integer> set = new TreeSet<Integer>();
                Cursor cursor = sqlDB.rawQuery("SELECT *FROM Word WHERE grade =1;",null);
                grade_ele1 = cursor.getCount();

                while(set.size()<30){
                    int temp2 = rand.nextInt(grade_ele1);

                    set.add(temp2);
                }
                Iterator<Integer> iter = set.iterator();
                HashMap<String,String> hm = new HashMap<String, String>();
                while (iter.hasNext()){
                    cursor.moveToPosition(iter.next());
                    if (cursor.getString(0).contains("/")||cursor.getString(0).contains(".")){
                        continue;
                    }
                    hm.put(cursor.getString(0),cursor.getString(1));
                }

                databaseReference.child("daily").child("ele_1").setValue(hm);
                cursor.close();

                Log.d("db","ele1_END");

                int grade_ele3;
                Set<Integer> set2 = new TreeSet<Integer>();

                Cursor cursor2 = sqlDB.rawQuery("SELECT *FROM Word WHERE grade =2;",null);

                grade_ele3 = cursor2.getCount();
                while(set2.size()<30){
                    int temp2 = rand.nextInt(grade_ele3);
                    set2.add(temp2);
                }

                Iterator<Integer> iter2 = set2.iterator();
                HashMap<String,String> hm2 = new HashMap<String, String>();

                while (iter2.hasNext()){
                    cursor2.moveToPosition(iter2.next());
                    if (cursor2.getString(0).contains("/")||cursor2.getString(0).contains(".")){
                        continue;
                    }
                    hm2.put(cursor2.getString(0),cursor2.getString(1));
                }

                databaseReference.child("daily").child("ele_3").setValue(hm2);
                cursor2.close();

                Log.d("db","ele3_END");

                int grade_ele5;
                Set<Integer> set3 = new TreeSet<Integer>();
                Cursor cursor3 = sqlDB.rawQuery("SELECT *FROM Word WHERE grade =3;",null);
                grade_ele5 = cursor3.getCount();

                while(set3.size()<30){
                    int temp2 = rand.nextInt(grade_ele5);
                    set3.add(temp2);
                }

                Iterator<Integer> iter3 = set3.iterator();
                HashMap<String,String> hm3 = new HashMap<String, String>();
                while (iter3.hasNext()){
                    cursor3.moveToPosition(iter3.next());
                    if (cursor3.getString(0).contains("/")||cursor3.getString(0).contains(".")){
                        continue;
                    }
                    hm3.put(cursor3.getString(0),cursor3.getString(1));
                }
                databaseReference.child("daily").child("ele_5").setValue(hm3);
                cursor3.close();
                Log.d("db","ele5_END");

                int middle;
                Set<Integer> set4 = new TreeSet<Integer>();
                Cursor cursor4 = sqlDB.rawQuery("SELECT *FROM Word WHERE grade =4;",null);
                middle = cursor4.getCount();
                while(set4.size()<30){
                    int temp2 = rand.nextInt(middle);
                    set4.add(temp2);
                }
                Iterator<Integer> iter4 = set4.iterator();
                HashMap<String,String> hm4 = new HashMap<String, String>();
                while (iter4.hasNext()){
                    cursor4.moveToPosition(iter4.next());
                    if (cursor4.getString(0).contains("/")||cursor4.getString(0).contains(".")){
                        continue;
                    }
                    hm4.put(cursor4.getString(0),cursor4.getString(1));
                }
                databaseReference.child("daily").child("middle").setValue(hm4);
                cursor4.close();

                Log.d("db","middle_end");

                int high;
                Set<Integer> set5 = new TreeSet<Integer>();
                Cursor cursor5 = sqlDB.rawQuery("SELECT *FROM Word WHERE grade =5;",null);
                high = cursor5.getCount();
                while(set5.size()<30){
                    int temp2 = rand.nextInt(high);
                    set5.add(temp2);
                }
                Iterator<Integer> iter5 = set5.iterator();
                HashMap<String,String> hm5 = new HashMap<String, String>();
                while (iter5.hasNext()){
                    cursor5.moveToPosition(iter5.next());
                    if (cursor5.getString(0).contains("/")||cursor5.getString(0).contains(".")){
                        continue;
                    }
                    hm5.put(cursor5.getString(0),cursor5.getString(1));
                }

                databaseReference.child("daily").child("high").setValue(hm5);

                cursor5.close();
                Log.d("db","high_end");
                //test
                sqlDB.close();
            }
        });



    }
    public void copyDBMethod(){
        boolean bResult = isCheckDB(getApplicationContext());
        Log.d("test","DBC "+bResult);
        if (!bResult){
            copyDB(getApplicationContext());
        }
    }
    public boolean isCheckDB(Context mContext){
        String filePath = "/data/data/com.example.project/databases/appdb.db";
        File file = new File(filePath);
        if(file.exists()){
            return true;
        }else{
            return false;
        }
    }
    public void copyDB(Context mContext) {
        AssetManager manager = mContext.getAssets();
        String folderPath = "/data/data/com.example.project/databases";
        String filePath = "/data/data/com.example.project/databases/appdb.db";
        File folder = new File(folderPath);
        File file = new File(filePath);
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        try {
            InputStream is = manager.open("Word.db");
            BufferedInputStream bis = new BufferedInputStream(is);
            if (folder.exists()) {

            } else {
                folder.mkdirs();
            }
            if (file.exists()) {
                file.delete();
                file.createNewFile();
            }
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            int read = -1;
            byte[] buffer = new byte[1024];
            while ((read = bis.read(buffer, 0, 1024)) != -1) {
                bos.write(buffer, 0, read);
            }
            bos.flush();
            bos.close();
            fos.close();
            bis.close();
            is.close();
        } catch (IOException e) {
            Log.e("ErrorMessage : ", e.getMessage());
        }
    }
}



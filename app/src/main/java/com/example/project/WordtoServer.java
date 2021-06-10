package com.example.project;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
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


public class WordtoServer extends AppCompatActivity {

    DBHelper dbhelper;
    SQLiteDatabase sqlDB;


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
                sqlDB = dbhelper.getReadableDatabase();
                Cursor cursor = sqlDB.rawQuery("SELECT *FROM Word;",null);
                while (cursor.moveToNext()){
                    Log.d("db",cursor.getString(0));
                }
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
class DBHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME ="appdb.db";
    public static final int DATABASE_VERSION =1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE groupTBL (gName CHAR(20) PRIMARY KEY,gNumber INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS groupTBL");
        onCreate(sqLiteDatabase);
    }

}

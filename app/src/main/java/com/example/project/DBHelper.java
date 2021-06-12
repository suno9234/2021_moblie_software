package com.example.project;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
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

        public ArrayList<Word> getWordList(int num, String query) {
            ArrayList<Word> wordItems = new ArrayList<>();
            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM Word", null);

            if(query == null) {
                while (cursor.moveToNext()) {
                    int grade = cursor.getInt(cursor.getColumnIndex("grade"));
                    if (grade == num) {
                        String _word = cursor.getString(cursor.getColumnIndex("word"));
                        String mean = cursor.getString(cursor.getColumnIndex("mean"));
                        String theme = cursor.getString(cursor.getColumnIndex("theme"));
                        Word word = new Word(_word, mean, theme, grade);
                        wordItems.add(word);
                    }
                }
            }else{
                while(cursor.moveToNext()){
                    int grade = cursor.getInt(cursor.getColumnIndex("grade"));
                    String _word = cursor.getString(cursor.getColumnIndex("word"));
                    String mean = cursor.getString(cursor.getColumnIndex("mean"));
                    String theme = cursor.getString(cursor.getColumnIndex("theme"));
                    if(grade == num && (_word.replaceAll(" ","").contains(query.replaceAll(" ","")))){
                        Word word = new Word(_word, mean, theme, grade);
                        wordItems.add(word);
                    }
                }
            }

            cursor.close();
            return wordItems;
        }

}

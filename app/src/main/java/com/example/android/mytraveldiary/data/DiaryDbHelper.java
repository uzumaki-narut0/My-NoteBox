package com.example.android.mytraveldiary.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.android.mytraveldiary.data.DiaryContract.DiaryEntry;
/**
 * Created by kumar swapnil on 13-10-2016.
 */

public class DiaryDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "diaryDatabse.db";
    public static final int DATABASE_VERSION = 1;

    public DiaryDbHelper(Context context){
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {


        String createTableStatement = "CREATE TABLE " + DiaryEntry.TABLE_NAME + " ( "
                + DiaryEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DiaryEntry.COLUMN_CONTENT + " TEXT NOT NULL,"
                +DiaryEntry.COLUMN_TYPE_OF_ENTRY + " TEXT NOT NULL "
                + " );";

        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

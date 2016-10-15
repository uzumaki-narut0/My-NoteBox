package com.example.android.mytraveldiary;

import android.app.LauncherActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.android.mytraveldiary.data.DiaryContract;
import com.example.android.mytraveldiary.data.DiaryDbHelper;
import com.example.android.mytraveldiary.data.DiaryContract.DiaryEntry;

import java.util.ArrayList;

public class WelcomePageActivity extends AppCompatActivity {

    ArrayList<String> contentList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);

        Intent myIntent = getIntent();
        String userName = myIntent.getStringExtra("user_name");
        String userEmail = myIntent.getStringExtra("user_email");
        String userProfilePicUrl = myIntent.getStringExtra("user_profile_pic");

        TextView tv1 = (TextView)findViewById(R.id.welcome_tv_1);
        tv1.setText("Hey! " + userName);

        ImageView iv1 = (ImageView)findViewById(R.id.welcome_iv_1);
        Glide.with(getApplicationContext()).load(userProfilePicUrl)
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(iv1);

        //calling displayDatabase()
        displayDatabase();

        //now display part
        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(this,R.layout.list_view,contentList);
        ListView listView = (ListView)findViewById(R.id.list_view);
        listView.setAdapter(itemsAdapter);
    }

    public void openeventsList(View v)
    {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action
                LinearLayout fabExtender = (LinearLayout)findViewById(R.id.fab_layout);
                fabExtender.setVisibility(View.VISIBLE);
            }
        });
    }

    public void openNoteActivity(View v)
    {
        Intent i = new Intent(WelcomePageActivity.this, NoteActivity.class);
        startActivity(i);
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabase();
    }
    private void displayDatabase()
    {
         /*
         retrieve information from database
        and create an arrayList of events
        and display them on activity screen
         */

        DiaryDbHelper mDbHelper = new DiaryDbHelper(this);

        //get the database in readable mode
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        //use the query method on db object to retrieve data
        String[] projection = new String[]{DiaryEntry._ID,DiaryEntry.COLUMN_CONTENT};

        Cursor cursor = db.query(DiaryEntry.TABLE_NAME,projection,null,null,null,null,null);

        //create an array list of contents
        contentList = new ArrayList<String>();
        while(cursor.moveToNext())
        {
            contentList.add(cursor.getString(1));
            Log.d("welcome","displayDatabase: " +contentList.get(0));
        }

        //don't forget to close the cursor
        cursor.close();


    }


}

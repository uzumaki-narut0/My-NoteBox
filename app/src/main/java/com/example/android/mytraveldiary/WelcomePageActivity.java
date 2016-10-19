package com.example.android.mytraveldiary;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.android.mytraveldiary.data.DiaryContract.DiaryEntry;
import com.example.android.mytraveldiary.data.DiaryDbHelper;

import java.util.ArrayList;

public class WelcomePageActivity extends AppCompatActivity {

    private static String yandexBaseUrl = "https://translate.yandex.net/api/v1.5/tr.json/translate?";
    private static String apiKey = "key=trnsl%2E1%2E1%2E20161017T145552Z%2Ea5d85c0d250180cc%2Ef4e858377ba1447c60464e8d4b89bb435feb3685";

    ArrayList<String> contentList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);

        TextView tv1 = (TextView)findViewById(R.id.welcome_tv_1);
        tv1.setText("Hey! " + GoogleSignInActivity.personName);

        ImageView iv1 = (ImageView)findViewById(R.id.welcome_iv_1);
        Glide.with(getApplicationContext()).load(GoogleSignInActivity.personPhotoUrl)
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(iv1);

        //calling displayDatabase()

        TranslationAsyncTask asynkTaskObject = new TranslationAsyncTask();
        asynkTaskObject.execute(yandexBaseUrl + apiKey);


        /*now display part
        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(this,R.layout.list_view,contentList);
        ListView listView = (ListView)findViewById(R.id.list_view);
        listView.setAdapter(itemsAdapter);*/
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
        TranslationAsyncTask asynkTaskObject = new TranslationAsyncTask();
        asynkTaskObject.execute(yandexBaseUrl + apiKey);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * react to the user tapping/selecting an options menu item
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_thingy:
                //Toast.makeText(this, "ADD!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(WelcomePageActivity.this, MyPreferencesActivity.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private class TranslationAsyncTask extends AsyncTask<String, Void, ArrayList<String>>
    {


        @Override
        protected ArrayList<String> doInBackground(String... urls) {

            DiaryDbHelper mDbHelper = new DiaryDbHelper(WelcomePageActivity.this);

            //get the database in readable mode
            SQLiteDatabase db = mDbHelper.getReadableDatabase();

            //use the query method on db object to retrieve data
            String[] projection = new String[]{DiaryEntry._ID, DiaryEntry.COLUMN_CONTENT};

            Cursor cursor = db.query(DiaryEntry.TABLE_NAME, projection, null, null, null, null, null);

            //create an array list of contents
            contentList = new ArrayList<String>();
            //if preffered language is Hindi
            //use yandexTranslate api
            SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
            //String strUserName = SP.getString("username", "NA");
            // boolean bAppUpdates = SP.getBoolean("applicationUpdates",false);
            String lang_preffered = SP.getString("lang_preffered", "1");
            if (lang_preffered.equals("2")) {
                while (cursor.moveToNext()) {
                    String translatedData = QueryUtils.fetchTranslatedData(urls[0] + "&lang=hi" + "&text=" + cursor.getString(1));
                    //  contentList.add(cursor.getString(1));
                    contentList.add(translatedData);
                    Log.d("welcome", "displayDatabase: " + contentList.get(0));
                }
                cursor.close();
                return contentList;
            } else {
                while (cursor.moveToNext()) {
                    //String translatedData = QueryUtils.fetchTranslatedData(urls[0]+cursor.getString(1));
                    contentList.add(cursor.getString(1));
                    // contentList.add(translatedData);
                    Log.d("welcome", "displayDatabase: " + contentList.get(0));
                }
                cursor.close();
                return contentList;
            }

        }

        @Override
        protected void onPostExecute(ArrayList<String> translations) {
            //super.onPostExecute(earthquakes);
            UpdateUI(translations);
        }

        public void UpdateUI(ArrayList<String> translatedList) {

            // EarthQuakeAdapter itemsAdapter = new EarthQuakeAdapter(EarthquakeActivity.this,earthquakeList);
            ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(WelcomePageActivity.this, R.layout.list_view, translatedList);
            ListView listview = (ListView) findViewById(R.id.list_view);
            listview.setAdapter(itemsAdapter);
        }
    }
}

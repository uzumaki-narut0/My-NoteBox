package com.example.android.mytraveldiary;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.mytraveldiary.data.DiaryContract;
import com.example.android.mytraveldiary.data.DiaryDbHelper;

public class NoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                // save the file to diary.db
                insertEventIntoDatabase();
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                // Do nothing for now
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void insertEventIntoDatabase(){
        EditText titleView = (EditText) findViewById(R.id.canvas_title);
        EditText contentView = (EditText) findViewById(R.id.canvas_content);
        String title = String.valueOf(titleView.getText());
        String content = String.valueOf(contentView.getText());

        DiaryDbHelper mDbHelper = new DiaryDbHelper(this);
        //getWritableDatabase is for insert update and delete operations
        SQLiteDatabase mDbObject = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DiaryContract.DiaryEntry.COLUMN_CONTENT,content);
        values.put(DiaryContract.DiaryEntry.COLUMN_TYPE_OF_ENTRY, DiaryContract.DiaryEntry.TYPE_TEXT);

        mDbObject.insert(DiaryContract.DiaryEntry.TABLE_NAME,null,values);

        Toast.makeText(getApplicationContext(),"Event saved",Toast.LENGTH_SHORT ).show();

    }

}

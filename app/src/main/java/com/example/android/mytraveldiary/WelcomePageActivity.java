package com.example.android.mytraveldiary;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class WelcomePageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);

        Intent myIntent = getIntent();
        String userName = myIntent.getStringExtra("user_name");
        String userEmail = myIntent.getStringExtra("user_email");
        String userProfilePicUrl = myIntent.getStringExtra("user_profile_pic");

        TextView tv1 = (TextView)findViewById(R.id.welcome_tv_1);
        tv1.setText("Hey " + userName);

        ImageView iv1 = (ImageView)findViewById(R.id.welcome_iv_1);
        Glide.with(getApplicationContext()).load(userProfilePicUrl)
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(iv1);

        //TextView tv2 = (TextView)findViewById(R.id.welcome_tv_2);
        //tv2.setText(userEmail);
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

        

       // GoogleSignInActivity signInObj = new GoogleSignInActivity();
        //signInObj.onStart();
    }
}

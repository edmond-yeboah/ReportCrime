package com.example.crime_report;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class police_login extends AppCompatActivity {
    TextView potologin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police_login);

        //getting textview
        potologin = (TextView) findViewById(R.id.potologin);

        //setting onclick listener for textview
        potologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(police_login.this,login.class);
                startActivity(intent);
                overridePendingTransition(R.anim.enter_from_right,R.anim.exit_through_right);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        //do nothing
    }
}
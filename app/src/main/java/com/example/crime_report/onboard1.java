package com.example.crime_report;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class onboard1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboard1);
    }

    public void next(View view) {
        Intent intent = new Intent(onboard1.this,onboard2.class);
        startActivity(intent);
        overridePendingTransition(R.anim.enter_from_right,R.anim.exit_through_right);
    }
}
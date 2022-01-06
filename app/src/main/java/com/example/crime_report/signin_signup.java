package com.example.crime_report;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

public class signin_signup extends AppCompatActivity {
    Button register, login;
    private FirebaseAuth mAuth;
    private View view;

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser !=null){

            Dexter.withContext(signin_signup.this)
                    .withPermissions(
                            Manifest.permission.CAMERA,
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                    .withListener(new MultiplePermissionsListener() {
                        @Override
                        public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                            if (multiplePermissionsReport.areAllPermissionsGranted()){
                                Intent intent = new Intent(signin_signup.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                            }else{
                                Snackbar.make(view,"Please accept all permissions",Snackbar.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                            permissionToken.continuePermissionRequest();
                        }
                    }).check();


        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_signup);

        FirebaseApp.initializeApp(this);

        //getting the buttons
        register = (Button) findViewById(R.id.signup);
        login = (Button) findViewById(R.id.login);
        mAuth = FirebaseAuth.getInstance();
        view = (View) findViewById(R.id.theview);

        //setting onclick listeners for register button
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //navigating to signup activity
                Intent intent = new Intent(signin_signup.this,signup.class);
                startActivity(intent);
                overridePendingTransition(R.anim.enter_from_right,R.anim.exit_through_right);
                finish();
            }
        });

        //setting onclick listener for login button
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(signin_signup.this, login.class);
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
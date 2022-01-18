package com.example.crime_report;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    private ImageView profile;
    private LottieAnimationView btnlocation;
    private Boolean record = false;
    private FusedLocationProviderClient fusedLocationProviderClient;

    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private double lat,lon;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        profile = (ImageView) findViewById(R.id.profile);
        btnlocation = (LottieAnimationView) findViewById(R.id.location);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

//        locationRequest = com.google.android.gms.location.LocationRequest.create();
//        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        locationRequest.setInterval(20);
//
//        locationCallback = new LocationCallback() {
//            @Override
//            public void onLocationResult(@NonNull LocationResult locationResult) {
//
//                for (Location location : locationResult.getLocations()){
//                    if (location != null){
//                               lat = location.getLatitude();
//                               lon = location.getLongitude();
//                    }
//                }
//            }
//        };



        //setting onclick listener for location
        btnlocation.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View view) {
                fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null){
                            Double lat = location.getLatitude();
                            Double lon = location.getLongitude();
                            Snackbar.make(view,""+lat+" "+lon,Snackbar.LENGTH_LONG).show();
                        }else{
                            Snackbar.make(view,"Problem getting geolocation",Snackbar.LENGTH_SHORT).show();
                        }

                    }
                });

//                Snackbar.make(view,""+lat+" "+lon,Snackbar.LENGTH_SHORT).show();
//                fusedLocationProviderClient.removeLocationUpdates(locationCallback);



            }
        });



        //setting onclick listener for profile
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go to profile activity
                Intent intent = new Intent(MainActivity.this, com.example.crime_report.profile.class);
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
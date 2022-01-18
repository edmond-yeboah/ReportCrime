package com.example.crime_report;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class profile extends AppCompatActivity {
    private TextView closeprofile,fullname,myemail;
    private Button logout;
    private FirebaseAuth mAuth;
    private UserClass userClass;
    private String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        closeprofile = (TextView) findViewById(R.id.clprofile);
        logout   = (Button) findViewById(R.id.logout);
        fullname = (TextView) findViewById(R.id.fullname);
        myemail = (TextView) findViewById(R.id.myemail);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();
        if (user!=null){
            email = user.getEmail();
        }

        //getting user information from the database
        Query query = FirebaseDatabase.getInstance().getReference("user").orderByChild("email").equalTo(email);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    userClass = ds.getValue(UserClass.class);
                }

                //showing information on textview
                if (userClass != null){
                    fullname.setText(String.format("%s %s", userClass.getFname(), userClass.getLname()));
                    myemail.setText(userClass.getEmail());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //setting onclick listener for close profile
        closeprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go back to dashboard
                Intent intent = new Intent(profile.this,MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.enter_from_left,R.anim.exit_through_left);
                finish();
            }
        });

        //setting onclick listener for logout button
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent intent = new Intent(profile.this,signin_signup.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        //do nothing
    }
}
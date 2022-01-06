package com.example.crime_report;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.wang.avi.AVLoadingIndicatorView;

public class signup extends AppCompatActivity {
    private TextView tologin;
    private TextInputLayout mEmail,mPassword,mCPassword,fname,lname;
    private boolean isvalidEmail=false,isvalidPassword = false, ispasswordmatch=false,isfnamevalid=false,islnamevalid=false;
    private Button register;
    private AVLoadingIndicatorView avi;
    private String emailpattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private FirebaseAuth mAuth;
    private UserClass userClass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        tologin = (TextView) findViewById(R.id.tologin);
        register = (Button) findViewById(R.id.createaccount);
        mEmail = (TextInputLayout) findViewById(R.id.lemail);
        mPassword = (TextInputLayout) findViewById(R.id.lpassword);
        mCPassword = (TextInputLayout) findViewById(R.id.cpassword);
        fname = (TextInputLayout) findViewById(R.id.fname);
        lname = (TextInputLayout) findViewById(R.id.lname);
        avi = (AVLoadingIndicatorView) findViewById(R.id.aviregister);

        //for firebase
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("user");
        mAuth = FirebaseAuth.getInstance();

        //disabbling register button
        register.setEnabled(false);

        //getting email
        mEmail.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                validateemail(editable);

            }
        });

        mEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b){
                    validateemail(((EditText)view).getText());
                }
            }
        });


        //getting the password
        mPassword.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                validatepassword(editable);

            }
        });

        mPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                validatepassword(((EditText)view).getText());
            }
        });


        //getting confirm password
        mCPassword.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                validatecpassword(editable);

            }
        });

        mCPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                validatecpassword(((EditText)view).getText());
            }
        });


        //getting firstname
        fname.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                validatefname(editable);

            }
        });

        fname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                validatefname(((EditText)view).getText());
            }
        });


        //getting lname
        lname.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                validatelname(editable);

            }
        });

        lname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                validatelname(((EditText)view).getText());
            }
        });

        //setting onclick listener for register button
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAnim();
                //register user with email and password
                mAuth.createUserWithEmailAndPassword(mEmail.getEditText().getText().toString().trim(),mPassword.getEditText().getText().toString().trim())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    saveinfo(view);
                                }else {
                                    task.addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            stopAnim();
                                            Snackbar.make(view,"Error creating account",Snackbar.LENGTH_LONG).show();
                                        }
                                    });
                                }
                            }
                        });

            }
        });

        //setting onclick listener for tologin
        tologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(signup.this,login.class);
                startActivity(intent);
                overridePendingTransition(R.anim.enter_from_right,R.anim.exit_through_right);
                finish();
            }
        });
    }

    private void saveinfo(View view) {
        String uid = reference.push().getKey();
        String email = mEmail.getEditText().getText().toString().trim();
        String firstname = fname.getEditText().getText().toString().trim();
        String lastname = lname.getEditText().getText().toString().trim();

        userClass = new UserClass(firstname,lastname,email,uid);
        reference.child(uid).setValue(userClass).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                stopAnim();
                //go to onboarding
                Intent intent = new Intent(signup.this,onboard1.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void stopAnim() {
        avi.hide();
    }

    private void startAnim() {
        avi.show();
    }

    //validate lname method
    private void validatelname(Editable s) {
        if (!TextUtils.isEmpty(s) & s.toString().trim().length()>=1){
            islnamevalid = true;
            lname.setError(null);
            if (isvalidPassword & isvalidEmail & ispasswordmatch & isfnamevalid & islnamevalid){
                register.setEnabled(true);
            }else {
                register.setEnabled(false);
            }
        }else{
            lname.setError("last name is required");
            register.setEnabled(false);
        }
    }

    //validate fname method
    private void validatefname(Editable s) {
        if (!TextUtils.isEmpty(s) & s.toString().trim().length()>=1){
            isfnamevalid = true;
            fname.setError(null);
            if (isvalidPassword & isvalidEmail & ispasswordmatch & isfnamevalid & islnamevalid){
                register.setEnabled(true);
            }else {
                register.setEnabled(false);
            }
        }else{
            fname.setError("first name required");
            register.setEnabled(false);
        }
    }

    //validate confirm password method
    private void validatecpassword(Editable s) {
        if (!TextUtils.isEmpty(s) & s.toString().trim().length()>=6 & s.toString().equals(mPassword.getEditText().getText().toString())){
            ispasswordmatch = true;
            mCPassword.setError(null);
            if (isvalidPassword & isvalidEmail & ispasswordmatch & isfnamevalid & islnamevalid){
                register.setEnabled(true);
            }else {
                register.setEnabled(false);
            }
        }else {
            mCPassword.setError("passwords do not match");
            register.setEnabled(false);
        }
    }

    //validate password method
    private void validatepassword(Editable s) {
        if (!TextUtils.isEmpty(s) & s.toString().trim().length()>=6){
            isvalidPassword=true;
            mPassword.setError(null);
            if (isvalidPassword & isvalidEmail & ispasswordmatch & isfnamevalid & islnamevalid){
                register.setEnabled(true);
            }else {
                register.setEnabled(false);
            }
        }
        else {
            mPassword.setError("too short");
            register.setEnabled(false);
        }

        if (!TextUtils.isEmpty(mCPassword.getEditText().getText().toString())){
            if (s.toString().equals(mCPassword.getEditText().getText().toString())){
                ispasswordmatch=true;
                mCPassword.setError(null);
                if (isvalidPassword & isvalidEmail & ispasswordmatch & isfnamevalid & islnamevalid){
                    register.setEnabled(true);
                }else {
                    register.setEnabled(false);
                }
            }
            else {
                mCPassword.setError("passwords do not match");
                register.setEnabled(false);
            }
        }
    }

    //validate email method
    private void validateemail(Editable s) {
        if (!TextUtils.isEmpty(s)) {
            if (!mEmail.getEditText().getText().toString().matches(emailpattern)) {
                mEmail.setError("Invalid email");
                register.setEnabled(false);
            } else {
                isvalidEmail = true;
                mEmail.setError(null);
                if (isvalidPassword & isvalidEmail & ispasswordmatch & isfnamevalid & islnamevalid){
                    register.setEnabled(true);
                }else {
                    register.setEnabled(false);
                }
            }
        } else {
            mEmail.setError("Invalid email");
            register.setEnabled(false);
        }
    }

    @Override
    public void onBackPressed() {
        //do nothing
    }
}
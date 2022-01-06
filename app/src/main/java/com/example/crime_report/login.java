package com.example.crime_report;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

public class login extends AppCompatActivity {

    private TextView tosignup, topologin;
    private AVLoadingIndicatorView avi;
    private TextInputLayout mEmail,mPassword;
    private FirebaseAuth mAuth;
    private DatabaseReference reference;
    private Button btnlogin;
    private boolean isvalidemail=false,isvalidpassword=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //getting text views
        tosignup = (TextView) findViewById(R.id.tosignup);
        topologin = (TextView) findViewById(R.id.topologin);
        avi = (AVLoadingIndicatorView) findViewById(R.id.avilogin);
        mEmail = (TextInputLayout) findViewById(R.id.email);
        mPassword = (TextInputLayout) findViewById(R.id.password);
        btnlogin = (Button) findViewById(R.id.signin);

        mAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();

        //disabling button
        btnlogin.setEnabled(false);

        //setting onclick listener for button
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //loggin user in with email and password
                startAnim();
                mAuth.signInWithEmailAndPassword(mEmail.getEditText().getText().toString().trim(),mPassword.getEditText().getText().toString().trim())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {

                                //checking if all permissions has been accepted
                                Dexter.withContext(login.this)
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
                                                    Intent intent = new Intent(login.this,MainActivity.class);
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
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        stopAnim();
                        Snackbar.make(view,"Check credentials and try again",Snackbar.LENGTH_LONG).show();
                    }
                });
            }
        });

        //getting the email
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
                validateemail(((EditText)view).getText());
            }
        });

        //getting password
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

        //setting onclick listener for tosignup
        tosignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login.this,signup.class);
                startActivity(intent);
                overridePendingTransition(R.anim.enter_from_right,R.anim.exit_through_right);
                finish();
            }
        });

        //setting onclick listener for topologin
        topologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login.this,police_login.class);
                startActivity(intent);
                overridePendingTransition(R.anim.enter_from_right,R.anim.exit_through_right);
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

    private void validatepassword(Editable editable) {
        if (TextUtils.isEmpty(editable)){
            mPassword.setError("");
            btnlogin.setEnabled(false);
        }else {
            mPassword.setError(null);
            isvalidpassword=true;
            if (isvalidpassword & isvalidemail){
                btnlogin.setEnabled(true);
            }
        }
    }

    private void validateemail(Editable editable) {
        if (TextUtils.isEmpty(editable)){
            mEmail.setError("");
            btnlogin.setEnabled(false);
        }else {
            mEmail.setError(null);
            isvalidemail = true;
            if (isvalidemail & isvalidpassword){
                btnlogin.setEnabled(true);
            }
        }
    }

    @Override
    public void onBackPressed() {
        //do nothing
    }
}
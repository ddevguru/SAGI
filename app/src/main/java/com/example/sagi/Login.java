package com.example.sagi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    EditText mEmail,mPassword;
    Button mLoginbtn;
    TextView mSignup;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    TextView forgetbtn;
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent =new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = findViewById(R.id.Email);
        mPassword = findViewById(R.id.Password);
        mLoginbtn = findViewById(R.id.LoginBtn);
        mSignup = findViewById(R.id.createtext);
        progressBar = findViewById(R.id.progressBar);
        forgetbtn = findViewById(R.id.createtxt1);
        mAuth = FirebaseAuth.getInstance();
        forgetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Login.this,"You can reset your password now!",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Login.this,ForgotPassword.class));
            }
        });

        ImageView imageViewShowHidepass = findViewById(R.id.showpass);
        imageViewShowHidepass.setImageResource(R.drawable.ic_baseline_visibility_off_24);
        imageViewShowHidepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mPassword.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance()))
                {
                    mPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    imageViewShowHidepass.setImageResource(R.drawable.ic_baseline_visibility_off_24);
                }
                else
                {
                    mPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    imageViewShowHidepass.setImageResource(R.drawable.ic_baseline_visibility_24);
                }
            }
        });


        mSignup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Signup.class));
            }
        });
        mLoginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String email,password;

                email = mEmail.getText().toString();
                password = mPassword.getText().toString();


                if(TextUtils.isEmpty(email))
                {
                    Toast.makeText(Login.this, "Enter your Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password))
                {
                    Toast.makeText(Login.this, "Enter your Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                   progressBar.setVisibility(View.GONE);
                                    Toast.makeText(Login.this, "Sign in Successfull",
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent =new Intent(getApplicationContext(),MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // If sign in fails, display a message to the user.

                                    Toast.makeText(Login.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });



            }
        });
    }
}
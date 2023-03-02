package com.example.sagi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class ForgotPassword extends AppCompatActivity {

     Button buttonpass;
     EditText remail;
     ProgressBar progressBar;
     FirebaseAuth authProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        getSupportActionBar().setTitle("Forgot Password");
        remail = findViewById(R.id.textpass);
        buttonpass = findViewById(R.id.reset_button);
        progressBar = findViewById(R.id.progressBar);

        buttonpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = remail.getText().toString();
                if (TextUtils.isEmpty(email))
                {
                    Toast.makeText(ForgotPassword.this,"Please enter your registered email id" ,Toast.LENGTH_SHORT).show();
                    remail.setError("Email id is required");
                    remail.requestFocus();
                }
                else  if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                    Toast.makeText(ForgotPassword.this,"Please enter valid email id" ,Toast.LENGTH_SHORT).show();
                    remail.setError("valid email id is required");
                    remail.requestFocus();
                }
                else
                {
                    progressBar.setVisibility(View.VISIBLE);
                    resetPassword(email);
                }
            }

            private void resetPassword(String email) {
                authProfile = FirebaseAuth.getInstance();
                authProfile.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(ForgotPassword.this,"Please Check your spam section in mail box for password reeset link !",Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(ForgotPassword.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            Toast.makeText(ForgotPassword.this,"Something went wrong !",Toast.LENGTH_SHORT).show();
                        }
                        progressBar.setVisibility(View.GONE);
                    }
                });
            }
        });


    }
}
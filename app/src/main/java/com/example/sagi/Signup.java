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


public class Signup extends AppCompatActivity {


    EditText mfullname,memail,mpassword,mphone;
    Button Registerbtn;
    TextView loginbtn;
    FirebaseAuth mAuth;
    ProgressBar progressBar;

   


    @Override
    public void onStart() {
        super.onStart();

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
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();
        mfullname = findViewById(R.id.fullname);
        memail = findViewById(R.id.emailid);
        mpassword = findViewById(R.id.password);
        mphone = findViewById(R.id.phone);

        progressBar = findViewById(R.id.progressBar);
        Registerbtn = findViewById(R.id.registerBtn);
        loginbtn = findViewById(R.id.createtxt);



        ImageView imageViewShowHidepas = findViewById(R.id.showpass);
        imageViewShowHidepas.setImageResource(R.drawable.ic_baseline_visibility_off_24);
        imageViewShowHidepas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mpassword.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())) {
                    mpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    imageViewShowHidepas.setImageResource(R.drawable.ic_baseline_visibility_off_24);
                } else {
                    mpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    imageViewShowHidepas.setImageResource(R.drawable.ic_baseline_visibility_24);
                }
            }
        });


        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();


            }
        });

        Registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String fullname, email, password, phone;
                fullname = mfullname.getText().toString().trim();
                email = memail.getText().toString();
                password = mpassword.getText().toString();
                phone = mphone.getText().toString();

                if (TextUtils.isEmpty(fullname)) {
                    mfullname.setError("Enter your name");
                    mfullname.requestFocus();
                    progressBar.setVisibility(View.GONE);
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    memail.setError("email kon dalega teri aatma");
                    memail.requestFocus();
                    progressBar.setVisibility(View.GONE);
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    mpassword.setError("Enter your password ");
                    mpassword.requestFocus();
                    progressBar.setVisibility(View.GONE);
                    return;
                }
                if (TextUtils.isEmpty(phone)) {

                    mphone.setError("Enter your phone number");
                    mphone.requestFocus();
                    progressBar.setVisibility(View.GONE);
                    return;
                }
                if (phone.length() < 10) {
                    mphone.setError("Please enter valid phone number");
                    mphone.requestFocus();
                    progressBar.setVisibility(View.GONE);
                    return;

                }
                if (phone.length() > 10) {
                    mphone.setError("Please enter valid phone number");
                    mphone.requestFocus();
                    progressBar.setVisibility(View.GONE);
                    return;

                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    progressBar.setVisibility(View.GONE);

                                    Toast.makeText(Signup.this, "Signup Successfull",
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    finish();

                                } else {
                                    // If sign in fails, display a message to the user.

                                    Toast.makeText(Signup.this, "User already Exist.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });

            }
        });


    }



}





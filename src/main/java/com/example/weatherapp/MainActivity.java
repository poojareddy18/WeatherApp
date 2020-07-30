package com.example.weatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    Button signin;
    TextView signinEmail,signinPassword;
    TextView registerlink;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signin = (Button)findViewById(R.id.Signin);
        signinEmail=(TextView)findViewById(R.id.Email_Address);
        signinPassword = (TextView)findViewById(R.id.Password);

        registerlink = (TextView)findViewById(R.id.Register);
        mAuth= FirebaseAuth.getInstance();
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = signinEmail.getText().toString().trim();
                final String password = signinPassword.getText().toString().trim();

                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                     if(task.isSuccessful()){
                         Toast.makeText(MainActivity.this,"Logged in successfully!!!!",Toast.LENGTH_SHORT).show();
                         Intent i = new Intent(MainActivity.this, NavigateActivity.class);
                         startActivity(i);
                    }else {
                         Toast.makeText(MainActivity.this,"Error!!!"+ task.getException().getMessage() ,Toast.LENGTH_SHORT).show();
                     }
                     }
                });

            }
        });
        registerlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(i);
            }
        });
    }
}
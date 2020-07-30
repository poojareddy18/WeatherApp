package com.example.weatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {
    private static final String SIGNUP_ACTIVITY_TAG="SignupActivity";
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    TextView signupEmail,signupPassword,userName;
    Button signup;
    FirebaseFirestore frstore;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        signupEmail = findViewById(R.id.Signup_Email);
        signupPassword= findViewById(R.id.Signup_Password);
        userName= findViewById(R.id.Name);
        signup= findViewById(R.id.Signup);
        frstore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("SignupActivity","I am here....0");
                String name = userName.getText().toString();
                final String email = signupEmail.getText().toString().trim();
                final String password = signupPassword.getText().toString().trim();

                if(name.isEmpty() && email.isEmpty() && password.isEmpty()){
                    Toast.makeText(getApplicationContext(), "enter credentials to signup!", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                } else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    Toast.makeText(getApplicationContext(), "Enter a valid email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(SignupActivity.this,"Registered Successfully!!!!",Toast.LENGTH_SHORT).show();
                        saveDetails();


                    }else{
                        if(task.getException() instanceof FirebaseAuthUserCollisionException){
                            Toast.makeText(SignupActivity.this,"You are already registered!!!" ,Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(SignupActivity.this,"Error!!!"+ task.getException().getMessage() ,Toast.LENGTH_SHORT).show();
                        }

                    }
                    }
                });


            }
        });

    }

    private void saveDetails() {
        String name = userName.getText().toString();
        final String email = signupEmail.getText().toString().trim();
        final String password = signupPassword.getText().toString().trim();
        DocumentReference documentReference = frstore.collection("user_details").document(email);
        Map<String,Object> user = new HashMap<>();
        user.put("uName",name);
        user.put("uEmail",email);
        user.put("uPassword",password);
        Log.i("SignupActivity","I am here...2");
        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.i("SignupActivity","I am here....3");
                Toast.makeText(SignupActivity.this,"User Saved Successfully to Database!!!!",Toast.LENGTH_LONG).show();
                Intent u = new Intent(SignupActivity.this,MainActivity.class);
                startActivity(u);
            }
        });
    }
}
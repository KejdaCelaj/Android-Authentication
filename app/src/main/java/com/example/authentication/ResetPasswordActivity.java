package com.example.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText email;
    private Button rst;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        email = findViewById(R.id.editText5);
        rst = findViewById(R.id.button6);

        mAuth= FirebaseAuth.getInstance();


        rst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String loginEmail=email.getText().toString();

                if(TextUtils.isEmpty(loginEmail)){
                    Toast.makeText(getApplicationContext(),"Enter Your Registered Email",Toast.LENGTH_LONG).show();
                    return;
                }

                mAuth.sendPasswordResetEmail(loginEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Check Your Email For Reset Password Instructions",Toast.LENGTH_LONG).show();

                        }else{
                            Toast.makeText(getApplicationContext(),"Failed To Send Reset Password Email",Toast.LENGTH_LONG).show();

                        }
                    }
                });
            }
        });
    }
}
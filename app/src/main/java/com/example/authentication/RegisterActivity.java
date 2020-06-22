package com.example.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private EditText namereg, emailreg, passreg, repassreg;
    private Button buttonreg, loginreg;
    String email, name, password, repassword;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        namereg = findViewById(R.id.et_name);
        emailreg = findViewById(R.id.et_email);
        passreg = findViewById(R.id.et_password);
        repassreg = findViewById(R.id.et_repassword);
        buttonreg = findViewById(R.id.btn_register);
        loginreg = findViewById(R.id.relogin);

        mAuth=FirebaseAuth.getInstance();

        //Registering user after button click
        buttonreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Retrieving text from edittext fields and assigning it to string values
                name = namereg.getText().toString();
                email = emailreg.getText().toString();
                password = passreg.getText().toString();
                repassword= repassreg.getText().toString();

                //Checking if all fields are filled
                if(!TextUtils.isEmpty(email)&&!TextUtils.isEmpty(password)&&!TextUtils.isEmpty(name)&&!TextUtils.isEmpty(repassword)) {

                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){

                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(mAuth.getUid());
                                User user = new User(name, email);
                                reference.setValue(user);

                                startActivity(new Intent(RegisterActivity.this, MainActivity.class)
                                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));

                                finish();

                            }else{

                                Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT);

                            }
                        }
                    });
                }
            }
        });

        //Send user to Login page
        loginreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        //Checks if user is logged in
        if(currentUser != null){
            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
        }
    }
}
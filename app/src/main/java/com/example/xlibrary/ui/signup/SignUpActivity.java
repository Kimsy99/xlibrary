package com.example.xlibrary.ui.signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xlibrary.DatabaseHelper;
import com.example.xlibrary.R;
import com.example.xlibrary.ui.login.LoginActivity;

public class SignUpActivity extends AppCompatActivity {
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db = new DatabaseHelper(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        TextView signUp = findViewById(R.id.link_signin);
        Button signUpSubmit = findViewById(R.id.sign_up_btn);
        signUp.setOnClickListener(v ->  startActivity(new Intent( SignUpActivity.this, LoginActivity.class)));
        signUpSubmit.setOnClickListener(v -> {
            EditText usernameInput = findViewById(R.id.signUpUsername);
            String username = usernameInput.getText().toString();
            EditText emailInput = findViewById(R.id.signUpEmail);
            String email = emailInput.getText().toString();
            EditText pwdInput = findViewById(R.id.signUpPasswordTextInput);
            String pwd = pwdInput.getText().toString();
            boolean result = db.addUser(username, email, pwd);
            if(result){
                Toast.makeText(this, "Congratulation! You had sign up successfully!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, LoginActivity.class));
            }else {
                Toast.makeText(this, "Sign Up Failed, please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
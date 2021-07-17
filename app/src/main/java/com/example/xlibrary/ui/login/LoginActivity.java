package com.example.xlibrary.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xlibrary.DatabaseHelper;
import com.example.xlibrary.MainActivity;
import com.example.xlibrary.R;
import com.example.xlibrary.ui.signup.SignUpActivity;

public class LoginActivity extends AppCompatActivity {
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView signIn = findViewById(R.id.link_signup);
        Button signInSubmit = findViewById(R.id.sign_in_btn);
        signIn.setOnClickListener(v ->  startActivity(new Intent(LoginActivity.this, SignUpActivity.class)));
        signInSubmit.setOnClickListener(v -> {
            db = new DatabaseHelper(this);
            EditText emailInput = findViewById(R.id.loginEmail);
            String email = emailInput.getText().toString();
            EditText pwdInput = findViewById(R.id.loginPasswordTextInput);
            String pwd = pwdInput.getText().toString();
            if (TextUtils.isEmpty(email)){
                Toast.makeText(this, "Please enter Email.", Toast.LENGTH_SHORT).show();
            return;
            }
            if (TextUtils.isEmpty(pwd)){
                Toast.makeText(this, "Please enter Password.", Toast.LENGTH_SHORT).show();
            return;}
            Cursor result = db.getUser(email, pwd);
            if (result != null && result.getCount() == 1 && result.moveToFirst()) {
                SharedPreferences sp = this.getSharedPreferences("user", 0);
                SharedPreferences.Editor editor = sp.edit();
                editor.putInt("uid", result.getInt(0));
                editor.putString("username", result.getString(1));
                editor.putString("email", email);
                editor.putString("password", pwd);
                editor.apply();
                result.close();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            } else {
                Toast.makeText(this, "Wrong Email/Password, please enter again.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
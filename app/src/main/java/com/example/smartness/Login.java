package com.example.smartness;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.smartness.Model.DatabaseHandler;
import com.example.smartness.Model.Services;
import com.example.smartness.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void login(View view) {
        String username = ((EditText) findViewById(R.id.EnterUser)).getText().toString();
        String password = ((EditText) findViewById(R.id.EnterPass)).getText().toString();
        if (username.isEmpty()) {
            Toast.makeText(Login.this, "Enter Username!", Toast.LENGTH_SHORT).show();
        } else if (password.isEmpty()) {
            Toast.makeText(Login.this, "Enter Password!", Toast.LENGTH_SHORT).show();
        } else {
            Services.getDatabase().login(username, password).addOnCompleteListener(new OnCompleteListener<DatabaseHandler.UserLoginMessage>() {
                @Override
                public void onComplete(@NonNull Task<DatabaseHandler.UserLoginMessage> task) {
                    switch (task.getResult()) {
                        case USER_DOES_NOT_EXIST: {
                            Toast.makeText(Login.this, "Username Does Not Exist!", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        case USER_LOGIN_SUCCESFULL: {
                            Toast.makeText(Login.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Login.this, HomeScreen.class));
                            break;
                        }
                        case USER_PASSWORD_DOES_NOT_MATCH: {
                            Toast.makeText(Login.this, "Username and Password Does Not Match!", Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                }
            });
        }
    }


    public void REGISTER(View view) {
        startActivity(new Intent(this, REGESTER.class));
    }

    public void TOMAIN(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }

}
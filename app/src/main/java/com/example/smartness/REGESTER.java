package com.example.smartness;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.smartness.Model.Services;
import com.example.smartness.Model.User;


public class REGESTER extends AppCompatActivity {
    EditText usernameET,passwordET,repasswordET,idET;
    Spinner spinner;
    String user,pass,repass,type,idNum;
    final int USERNAME_LENGTH = 4;
    final int USERNAME_MAX = 12;
    final int PASSWORD_LENGTH = 8;
    final int PASSWORD_MAX = 16;
    final int ID_LENGTH = 9;
    boolean allowedToCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regester);
        usernameET = findViewById(R.id.username);
        passwordET = findViewById(R.id.pass);
        repasswordET = findViewById(R.id.rePass);
        idET = findViewById(R.id.id);
        spinner = findViewById(R.id.spinner);
        activeSpinner();
    }

    public void Click(View view) {
        setInfo();
        allowedToCreate = true;
        if(idET.length() != ID_LENGTH) {
            Toast.makeText(REGESTER.this, "Invalid ID", Toast.LENGTH_LONG).show();
            idET.setError("9 digits only");
            allowedToCreate = false;
        }
        else if(!checkUsername(user))
            allowedToCreate = false;
        else if(!checkPassword(pass))
            allowedToCreate = false;

        if(allowedToCreate) {
            addUser(user, pass, idNum, type);
            startActivity(new Intent(this, HomeScreen.class));
            }

        }

    public boolean checkUsername(String username) {
        if(username.length() < USERNAME_LENGTH) {
            Toast.makeText(REGESTER.this, "Username Too Short", Toast.LENGTH_LONG).show();
            usernameET.setError("At least 4 characters");
            return false;
        } else if(username.length() > USERNAME_MAX) {
            Toast.makeText(REGESTER.this, "Username Too Long", Toast.LENGTH_LONG).show();
            usernameET.setError("Maximum 12 characters");
            return false;
        }
        for(int i = 0; i < username.length(); i++) {
            if ((!isLetter(username.charAt(i))) && (!isNumeric(username.charAt(i)))) {
                Toast.makeText(REGESTER.this, "Invalid Username", Toast.LENGTH_LONG).show();
                usernameET.setError("English and Numbers only");
                return false;
            }
        }
        return true;
    }

    public boolean checkPassword(String password) {
        if(password.length() < PASSWORD_LENGTH) {
            Toast.makeText(REGESTER.this, "Password Too Short", Toast.LENGTH_LONG).show();
            passwordET.setError("At least 8 characters");
            return false;
        } else if(password.length() > PASSWORD_MAX) {
            Toast.makeText(REGESTER.this, "Password Too Long", Toast.LENGTH_LONG).show();
            passwordET.setError("Maximum 16 characters");
            return false;
        }

        else if(!password.equals(repass)) {
            Toast.makeText(REGESTER.this, "Passwords are not the same", Toast.LENGTH_LONG).show();
            repasswordET.setError("Passwords has to be the same");
            return false;
        }

        int upperCharCount = 0, numCount = 0;

        for(int i = 0; i < password.length(); i++) {
            if(isUpper(password.charAt(i)))
                upperCharCount++;
            else if(isNumeric(password.charAt(i)))
                numCount++;
        }

        if((upperCharCount <= 0) || (numCount <= 0)) {
            Toast.makeText(REGESTER.this, "Invalid Password", Toast.LENGTH_LONG).show();
            passwordET.setError("At least 1 digit and 1 uppercase character");
            return false;
        }
        return true;
    }

    public boolean isLetter(char ch) {
        ch = Character.toUpperCase(ch);
        return ((ch >= 'A') && (ch <= 'Z'));
    }

    public boolean isUpper(char ch) {
        return ((ch >= 'A') && (ch <= 'Z'));
    }
    public boolean isNumeric(char ch) {
        return ((ch >= '0') && (ch <= '9'));
    }

    public void setInfo() {
        user = usernameET.getText().toString();
        pass = passwordET.getText().toString();
        repass = repasswordET.getText().toString();
        idNum = idET.getText().toString();
        type = spinner.getSelectedItem().toString();
    }

    public void addUser(String user, String pass, String id, String type) {
        Services.getDatabase().addUser(new User(user, pass, id, type));
        Toast.makeText(REGESTER.this, "Account Created!", Toast.LENGTH_LONG).show();
    }

    public void activeSpinner() {
        ArrayAdapter<String> Adapter = new ArrayAdapter<String>(REGESTER.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Types));
        Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(Adapter);
    }

    public void login(View view) {
        startActivity(new Intent(this,Login.class));
    }

    public void TOMAIN(View view) { startActivity(new Intent(this,MainActivity.class));}

}

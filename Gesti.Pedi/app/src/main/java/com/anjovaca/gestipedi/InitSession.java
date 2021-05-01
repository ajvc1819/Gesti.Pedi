package com.anjovaca.gestipedi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class InitSession extends AppCompatActivity {
    EditText username, password;
    DbGestiPedi dbGestiPedi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init_session);
        username = findViewById(R.id.etUsuario);
        password = findViewById(R.id.etContraseña);
        dbGestiPedi = new DbGestiPedi(getApplicationContext());

    }

    public void initSession(View view) {
        try{
            dbGestiPedi.initSession(username.getText().toString(),password.getText().toString());
        }catch (Exception e) {
            Log.d("TAG", e.toString());
        }
    }

    public void register(View view) {
        Intent intent = new Intent(this,Register.class);
        startActivity(intent);
    }
}
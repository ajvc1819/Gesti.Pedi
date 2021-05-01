package com.anjovaca.gestipedi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class Register extends AppCompatActivity {

    DbGestiPedi dbGestiPedi;
    EditText username, password, name, lastName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dbGestiPedi = new DbGestiPedi(getApplicationContext());

        username = findViewById(R.id.etUsuario);
        password = findViewById(R.id.etContraseña);
        name = findViewById(R.id.etNombreReg);
        lastName = findViewById(R.id.etApellidosReg);
    }

    public void register(View view) {
        try{
            dbGestiPedi.insertUser(username.getText().toString(), password.getText().toString(), name.getText().toString(), lastName.getText().toString());
        } catch (Exception e){
            Log.d("TAG", e.toString());
        }
    }

    public void cancel(View view) {
        finish();
    }
}
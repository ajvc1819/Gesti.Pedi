package com.anjovaca.gestipedi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.List;

public class Register extends AppCompatActivity {

    public List<UserModel> userModelList;
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
            userModelList = dbGestiPedi.checkUsers(username.getText().toString());
            if(userModelList.isEmpty()){
                dbGestiPedi.insertUser(name.getText().toString(), lastName.getText().toString(), username.getText().toString(), password.getText().toString(),"Usuario");
            } else {
              Log.d("TAG", "El usuario ya existe.");
            }
            finish();
        } catch (Exception e){
            Log.d("TAG", e.toString());
        }
    }

    public void cancel(View view) {
        finish();
    }
}
package com.anjovaca.gestipedi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class InitSession extends AppCompatActivity {
    public static final String EXTRA_LOGED_IN =
            "com.example.android.twoactivities.extra.login";
    public List<UserModel> userModelList;
    public boolean login;
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
            userModelList = dbGestiPedi.initSession(username.getText().toString(), password.getText().toString());
            if(!userModelList.isEmpty()){
                login = true;
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.putExtra(EXTRA_LOGED_IN, login);
                startActivity(intent);
                finish();
            }
            else {
                Toast.makeText(getApplicationContext(),"El usuario introducido no es correcto.",Toast.LENGTH_SHORT).show();
            }


        }catch (Exception e) {
            Log.d("TAG", e.toString());
        }
    }

    public void register(View view) {
        Intent intent = new Intent(this,Register.class);
        startActivity(intent);
    }
}
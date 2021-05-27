package com.anjovaca.gestipedi.LogIn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.anjovaca.gestipedi.DB.DbGestiPedi;
import com.anjovaca.gestipedi.Main.MainActivity;
import com.anjovaca.gestipedi.R;
import com.anjovaca.gestipedi.DB.Models.UserModel;

import java.util.List;

public class LogIn extends AppCompatActivity {

    private SharedPreferences mPreferences;
    public static final String EXTRA_LOGED_IN =
            "com.example.android.twoactivities.extra.login";
    public List<UserModel> userModelList;
    public boolean login;
    EditText username, password;
    DbGestiPedi dbGestiPedi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        username = findViewById(R.id.etUsuario);
        password = findViewById(R.id.etContraseña);
        dbGestiPedi = new DbGestiPedi(getApplicationContext());
        String sharedPrefFile = "com.example.android.sharedprefs";
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
    }

    //Función que permite iniciar sesión.
    public void initSession(View view) {
        try {
            userModelList = dbGestiPedi.initSession(username.getText().toString(), password.getText().toString());
            int userId = userModelList.get(0).getId();
            String rol = userModelList.get(0).getRol();
            if (!userModelList.isEmpty()) {
                login = true;

                SharedPreferences.Editor preferencesEditor = mPreferences.edit();
                String LOG_KEY = "log";
                preferencesEditor.putBoolean(LOG_KEY, login);
                String USER_KEY = "user";
                preferencesEditor.putInt(USER_KEY, userId);
                String ROL_KEY = "rol";
                preferencesEditor.putString(ROL_KEY, rol);
                preferencesEditor.apply();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra(EXTRA_LOGED_IN, login);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "El usuario introducido no es correcto.", Toast.LENGTH_SHORT).show();
            }


        } catch (Exception e) {
            Log.d("TAG", e.toString());
        }
    }

    //Función que permite abrir la actividad Register.
    public void register(View view) {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

    //Función que permite regresar al menú principal al pulsar sobre el logotipo de la empresa.
    public void returnMainMenu(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}
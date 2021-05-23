package com.anjovaca.gestipedi.LogIn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.anjovaca.gestipedi.DB.DbGestiPedi;
import com.anjovaca.gestipedi.DB.Models.UserModel;
import com.anjovaca.gestipedi.Main.MainActivity;
import com.anjovaca.gestipedi.R;

import java.util.List;

public class RegisterAdministrator extends AppCompatActivity {
    public List<UserModel> userModelList;
    DbGestiPedi dbGestiPedi;
    EditText username, password, name, lastName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_administrator);
        dbGestiPedi = new DbGestiPedi(getApplicationContext());

        username = findViewById(R.id.etUsuario);
        password = findViewById(R.id.etContraseña);
        name = findViewById(R.id.etNombreReg);
        lastName = findViewById(R.id.etApellidosReg);
    }

    //Función que permite el registro de un administrador en la base de datos.
    public void register(View view) {
        try {
            userModelList = dbGestiPedi.checkUsers(username.getText().toString());
            if (userModelList.isEmpty()) {
                if (!name.getText().toString().isEmpty() && !lastName.getText().toString().isEmpty() && !username.getText().toString().isEmpty() && !password.getText().toString().isEmpty()) {
                    dbGestiPedi.insertUser(name.getText().toString(), lastName.getText().toString(), username.getText().toString(), password.getText().toString(), "Administrador");
                } else {
                    Toast.makeText(getApplicationContext(), "Ha introducido un campo vacío.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "El usuario ya existe.", Toast.LENGTH_SHORT).show();
            }
            finish();
        } catch (Exception e) {
            Log.d("TAG", e.toString());
        }
    }

    //Función que permite la cancelación de la acción y el cierre de la actividad.
    public void cancel(View view) {
        finish();
    }

    //Función que permite regresar al menú principal al pulsar sobre el logotipo de la empresa.
    public void returnMainMenu(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}
package com.anjovaca.gestipedi.LogIn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class Register extends AppCompatActivity {

    public List<UserModel> userModelList;
    DbGestiPedi dbGestiPedi;
    EditText username, password, name, lastName, dni, city, country, phone, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dbGestiPedi = new DbGestiPedi(getApplicationContext());

        username = findViewById(R.id.etUser);
        password = findViewById(R.id.etPass);
        name = findViewById(R.id.etNombre);
        lastName = findViewById(R.id.etApellidos);
        dni = findViewById(R.id.etDni);
        city = findViewById(R.id.etCiudad);
        country = findViewById(R.id.etPais);
        phone = findViewById(R.id.etTelf);
        email = findViewById(R.id.etEmail);
    }

    //Función que permite el registro de nuevos usuarios en la base de datos.
    public void register(View view) {
        userModelList = dbGestiPedi.checkUsers(username.getText().toString());
        if (userModelList.isEmpty()) {
            boolean dniValid = dni.getText().toString().matches("^[0-9]{8}[A-Za-z]$");
            if (dni.getText().toString().length() == 9 && dniValid && !name.getText().toString().isEmpty() && !lastName.getText().toString().isEmpty() && !city.getText().toString().isEmpty() && !country.getText().toString().isEmpty() && phone.getText().toString().length() == 9 && !email.getText().toString().isEmpty()) {
                dbGestiPedi.insertUser(name.getText().toString(), lastName.getText().toString(), username.getText().toString(), password.getText().toString(), "Usuario", dni.getText().toString(), phone.getText().toString(), email.getText().toString(), city
                        .getText().toString(), country.getText().toString());
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Ha introducido un campo vacío.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "El usuario ya existe.", Toast.LENGTH_SHORT).show();
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
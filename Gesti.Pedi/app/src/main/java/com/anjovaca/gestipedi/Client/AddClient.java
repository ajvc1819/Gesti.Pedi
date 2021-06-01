package com.anjovaca.gestipedi.Client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.anjovaca.gestipedi.DB.Models.ClientModel;
import com.anjovaca.gestipedi.DB.DbGestiPedi;
import com.anjovaca.gestipedi.Main.MainActivity;
import com.anjovaca.gestipedi.R;

import java.util.List;
import java.util.regex.Pattern;

public class AddClient extends AppCompatActivity {

    List<ClientModel> clientModelList;
    DbGestiPedi dbGestiPedi;
    EditText dni, name, lastname, enterprise, cp, address, city, country, phone, email;
    public boolean login;

    public static final String EXTRA_LOGED_IN =
            "com.example.android.twoactivities.extra.login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client);
        dbGestiPedi = new DbGestiPedi(getApplicationContext());

        dni = findViewById(R.id.etDni);
        name = findViewById(R.id.etNombre);
        lastname = findViewById(R.id.etApellidos);
        enterprise = findViewById(R.id.etEmpresa);
        cp = findViewById(R.id.etCP);
        address = findViewById(R.id.etDireccion);
        city = findViewById(R.id.etCiudad);
        country = findViewById(R.id.etPais);
        phone = findViewById(R.id.etTelf);
        email = findViewById(R.id.etEmail);

        String sharedPrefFile = "com.example.android.sharedprefs";
        SharedPreferences mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        String LOG_KEY = "log";
        login = mPreferences.getBoolean(LOG_KEY, login);

    }

    //Función que permite la creación e inserción de un nuevo cliente en la base de datos.
    public void insertClient(View view) {
        boolean dniValid = dni.getText().toString().matches("^[0-9]{8}[A-Za-z]$");
        if (dni.getText().toString().length() == 9 && dniValid && !name.getText().toString().isEmpty() && !lastname.getText().toString().isEmpty() && !enterprise.getText().toString().isEmpty() && cp.getText().toString().length() == 5 && !address.getText().toString().isEmpty() && !city.getText().toString().isEmpty() && !country.getText().toString().isEmpty() && phone.getText().toString().length() == 9 && !email.getText().toString().isEmpty()) {
            clientModelList = dbGestiPedi.checkClient(dni.getText().toString(), phone.getText().toString(), email.getText().toString());
            if (clientModelList.isEmpty()) {
                dbGestiPedi.insertClient(dni.getText().toString(), name.getText().toString(), lastname.getText().toString(), enterprise.getText().toString(), address.getText().toString(), cp.getText().toString(), city.getText().toString(), country.getText().toString(), phone.getText().toString(), email.getText().toString());
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Alguno de los datos introducidos no es correcto o ya está en uso.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Ha introducido un campo de manera erronea.", Toast.LENGTH_SHORT).show();
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
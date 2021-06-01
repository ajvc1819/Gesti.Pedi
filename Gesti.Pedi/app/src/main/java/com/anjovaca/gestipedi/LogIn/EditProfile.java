package com.anjovaca.gestipedi.LogIn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.anjovaca.gestipedi.Client.ClientDetail;
import com.anjovaca.gestipedi.DB.DbGestiPedi;
import com.anjovaca.gestipedi.DB.Models.ClientModel;
import com.anjovaca.gestipedi.DB.Models.UserModel;
import com.anjovaca.gestipedi.Main.MainActivity;
import com.anjovaca.gestipedi.R;

import java.util.List;

public class EditProfile extends AppCompatActivity {
    DbGestiPedi dbGestiPedi;
    int id;
    EditText name, lastName, dni, city, country, phone, email;
    public List<UserModel> userModelList;
    public boolean login;

    public static final String EXTRA_LOGED_IN =
            "com.example.android.twoactivities.extra.login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Intent intent = getIntent();
        id = intent.getIntExtra(Profile.EXTRA_ID, 0);


        name = findViewById(R.id.etNombre);
        lastName = findViewById(R.id.etApellidos);
        dni = findViewById(R.id.etDni);
        city = findViewById(R.id.etCiudad);
        country = findViewById(R.id.etPais);
        phone = findViewById(R.id.etTelf);
        email = findViewById(R.id.etEmail);

        dbGestiPedi = new DbGestiPedi(getApplicationContext());

        userModelList = dbGestiPedi.getUsersById(id);

        name.setText(userModelList.get(0).getName());
        lastName.setText(userModelList.get(0).getLastname());
        dni.setText(userModelList.get(0).getDni());
        city.setText(userModelList.get(0).getCity());
        country.setText(userModelList.get(0).getCountry());
        phone.setText(userModelList.get(0).getPhone());
        email.setText(userModelList.get(0).getEmail());

        String sharedPrefFile = "com.example.android.sharedprefs";
        SharedPreferences mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        String LOG_KEY = "log";
        login = mPreferences.getBoolean(LOG_KEY, login);
    }

    //Función que permite la creación de funcionalidades de los elementos que se muestran en el menú superior.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Función que permite regresar al menú principal en el caso de hacer click sobre el logo de la empresa.
    public void returnMainMenu(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    //Función que permite cancelar la acción que se esta desarrollando y cerrar la actividad.
    public void cancel(View view) {
        finish();
    }

    //Función que permite la edición de los datos de un usuario.
    public void editProfile(View view) {
        if (!name.getText().toString().isEmpty() && !lastName.getText().toString().isEmpty()) {
            dbGestiPedi.updateProfile(id, name.getText().toString(), lastName.getText().toString(), dni.getText().toString(), city.getText().toString(),country.getText().toString(),phone.getText().toString(),email.getText().toString());
        } else {
            Toast.makeText(getApplicationContext(), "No se han podido editar los datos del perfil.", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}
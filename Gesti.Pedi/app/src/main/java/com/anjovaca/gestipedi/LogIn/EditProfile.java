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
    EditText name, lastname;
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

        name = findViewById(R.id.etName);
        lastname = findViewById(R.id.etLastName);

        dbGestiPedi = new DbGestiPedi(getApplicationContext());

        userModelList = dbGestiPedi.getUsersById(id);

        name.setText(userModelList.get(0).getName());
        lastname.setText(userModelList.get(0).getLastname());

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

    public void returnMainMenu(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    public void cancel(View view) {
        finish();
    }

    public void editClient(View view) {
        if (!name.getText().toString().isEmpty() && !lastname.getText().toString().isEmpty()) {
            dbGestiPedi.updateProfile(id, name.getText().toString(), lastname.getText().toString());
        } else {
            Toast.makeText(getApplicationContext(), "No se han podido editar los datos del perfil.", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}
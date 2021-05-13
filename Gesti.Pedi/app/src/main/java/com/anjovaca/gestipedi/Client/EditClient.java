package com.anjovaca.gestipedi.Client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.anjovaca.gestipedi.DB.Models.ClientModel;
import com.anjovaca.gestipedi.DB.DbGestiPedi;
import com.anjovaca.gestipedi.LogIn.InitSession;
import com.anjovaca.gestipedi.LogIn.LogOut;
import com.anjovaca.gestipedi.Order.ShoppingCart;
import com.anjovaca.gestipedi.R;

import java.util.List;

public class EditClient extends AppCompatActivity {
    DbGestiPedi dbGestiPedi;
    int id;
    EditText dni, name, lastname, enterprise, cp, address, city, country, phone, email;
    public List<ClientModel> clientModelList;
    int orderId;
    public boolean login;

    public static final String EXTRA_LOGED_IN =
            "com.example.android.twoactivities.extra.login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_client);
        Intent intent = getIntent();
        id = intent.getIntExtra(ClientDetail.EXTRA_ID, 0);

        dni = findViewById(R.id.etDni);
        name =  findViewById(R.id.etNombre);
        lastname =  findViewById(R.id.etApellidos);
        enterprise =  findViewById(R.id.etEmpresa);
        cp =  findViewById(R.id.etCP);
        address =  findViewById(R.id.etDireccion);
        city =  findViewById(R.id.etCiudad);
        country =  findViewById(R.id.etPais);
        phone =  findViewById(R.id.etTelf);
        email =  findViewById(R.id.etEmail);

        dbGestiPedi = new DbGestiPedi(getApplicationContext());

        clientModelList = dbGestiPedi.getClientsById(id);

        dni.setText(clientModelList.get(0).getDni());
        name.setText(clientModelList.get(0).getName());
        lastname.setText(clientModelList.get(0).getLastname());
        enterprise.setText(clientModelList.get(0).getEnterprise());
        cp.setText(clientModelList.get(0).getCp());
        address.setText(clientModelList.get(0).getAddress());
        city.setText(clientModelList.get(0).getCity());
        country.setText(clientModelList.get(0).getCountry());
        phone.setText(clientModelList.get(0).getPhone());
        email.setText(clientModelList.get(0).getEmail());

        String sharedPrefFile = "com.example.android.hellosharedprefs";
        SharedPreferences mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        String LOG_KEY = "log";
        login = mPreferences.getBoolean(LOG_KEY, login);

    }

    public void cancel(View view) {
        finish();
    }

    public void editClient(View view) {
        if(dni.getText().toString().length() == 9 && !name.getText().toString().isEmpty() && !lastname.getText().toString().isEmpty() && !enterprise.getText().toString().isEmpty() && cp.getText().toString().length() == 5 && !address.getText().toString().isEmpty() && !city.getText().toString().isEmpty() && !country.getText().toString().isEmpty() && phone.getText().toString().length() == 9 && ! email.getText().toString().isEmpty()){
            dbGestiPedi.editClient(id, dni.getText().toString(), name.getText().toString(), lastname.getText().toString(), enterprise.getText().toString(), address.getText().toString(), cp.getText().toString(), city.getText().toString(), country.getText().toString(), phone.getText().toString(), email.getText().toString());
        }
        finish();
    }
}
package com.anjovaca.gestipedi.Client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.anjovaca.gestipedi.DB.Models.ClientModel;
import com.anjovaca.gestipedi.DB.DbGestiPedi;
import com.anjovaca.gestipedi.LogIn.InitSession;
import com.anjovaca.gestipedi.LogIn.LogOut;
import com.anjovaca.gestipedi.Order.ShoppingCart;
import com.anjovaca.gestipedi.R;

import java.util.List;

public class AddCliente extends AppCompatActivity {

    List<ClientModel> clientModelList;
    DbGestiPedi dbGestiPedi;
    EditText dni, name, lastname, enterprise, cp, address, city, country, phone, email;
    int orderId;
    public boolean login;

    public static final String EXTRA_LOGED_IN =
            "com.example.android.twoactivities.extra.login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anyadir_cliente);
        dbGestiPedi = new DbGestiPedi(getApplicationContext());

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

        String sharedPrefFile = "com.example.android.hellosharedprefs";
        SharedPreferences mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        String LOG_KEY = "log";
        login = mPreferences.getBoolean(LOG_KEY, login);

    }

    public void insertClient(View view) {
        if(dni.getText().toString().length() == 9 && !name.getText().toString().isEmpty() && !lastname.getText().toString().isEmpty() && !enterprise.getText().toString().isEmpty() && cp.getText().toString().length() == 5 && !address.getText().toString().isEmpty() && !city.getText().toString().isEmpty() && !country.getText().toString().isEmpty() && phone.getText().toString().length() == 9 && ! email.getText().toString().isEmpty()){
            clientModelList = dbGestiPedi.checkClient(dni.getText().toString(), phone.getText().toString(), email.getText().toString());
            if(clientModelList.isEmpty()){
                dbGestiPedi.insertClient(dni.getText().toString(), name.getText().toString(), lastname.getText().toString(), enterprise.getText().toString(),address.getText().toString(),cp.getText().toString(),city.getText().toString(),country.getText().toString(), phone.getText().toString(), email.getText().toString());
                finish();
            } else {
                Toast.makeText(getApplicationContext(),"Alguno de los datos introducidos no es correcto o ya está en uso.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void cancel(View view) {
        finish();
    }


}
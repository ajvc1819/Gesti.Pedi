package com.anjovaca.gestipedi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.List;

public class EditClient extends AppCompatActivity {
    DbGestiPedi dbGestiPedi;
    int id;
    EditText dni, nombre, apellidos, empresa,cp, direccion, ciudad, pais, telefono, correo;
    public List<ClienteModelo> clienteModeloList;

    public boolean login;

    public static final String EXTRA_LOGED_IN =
            "com.example.android.twoactivities.extra.login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_client);
        Intent intent = getIntent();
        id = intent.getIntExtra(ClienteDetalle.EXTRA_ID, 0);

        dni = findViewById(R.id.etDni);
        nombre =  findViewById(R.id.etNombre);
        apellidos =  findViewById(R.id.etApellidos);
        empresa =  findViewById(R.id.etEmpresa);
        cp =  findViewById(R.id.etCP);
        direccion =  findViewById(R.id.etDireccion);
        ciudad =  findViewById(R.id.etCiudad);
        pais =  findViewById(R.id.etPais);
        telefono =  findViewById(R.id.etTelf);
        correo =  findViewById(R.id.etEmail);

        dbGestiPedi = new DbGestiPedi(getApplicationContext());

        clienteModeloList = dbGestiPedi.mostrarClientePorId(id);

        dni.setText(clienteModeloList.get(0).getDni());
        nombre.setText(clienteModeloList.get(0).getNombre());
        apellidos.setText(clienteModeloList.get(0).getApellidos());
        empresa.setText(clienteModeloList.get(0).getEmpresa());
        cp.setText(clienteModeloList.get(0).getCp());
        direccion.setText(clienteModeloList.get(0).getDireccion());
        ciudad.setText(clienteModeloList.get(0).getCiudad());
        pais.setText(clienteModeloList.get(0).getPais());
        telefono.setText(clienteModeloList.get(0).getTelefono());
        correo.setText(clienteModeloList.get(0).getCorreo());

        String sharedPrefFile = "com.example.android.hellosharedprefs";
        SharedPreferences mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        String LOG_KEY = "log";
        login = mPreferences.getBoolean(LOG_KEY, login);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.initSession) {
            Intent intent;
            if(login){
                intent = new Intent(getApplicationContext(), LogOut.class);
                intent.putExtra(EXTRA_LOGED_IN, login);
            }else {
                intent = new Intent(this, InitSession.class);
            }
            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }

    public void cancel(View view) {
        finish();
    }

    public void editClient(View view) {
        if(dni.getText().toString().length() == 9 && !nombre.getText().toString().isEmpty() && !apellidos.getText().toString().isEmpty() && !empresa.getText().toString().isEmpty() && cp.getText().toString().length() == 5 && !direccion.getText().toString().isEmpty() && !ciudad.getText().toString().isEmpty() && !pais.getText().toString().isEmpty() && telefono.getText().toString().length() == 9 && ! correo.getText().toString().isEmpty()){
            dbGestiPedi.editClient(id, dni.getText().toString(), nombre.getText().toString(), apellidos.getText().toString(), empresa.getText().toString(),direccion.getText().toString(), cp.getText().toString(),ciudad.getText().toString(),pais.getText().toString(), telefono.getText().toString(), correo.getText().toString());
        }
        finish();
    }
}
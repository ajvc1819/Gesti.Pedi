package com.anjovaca.gestipedi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AnyadirCliente extends AppCompatActivity {

    DbGestiPedi dbGestiPedi;
    EditText dni, nombre, apellidos, empresa,cp, direccion, ciudad, pais, telefono, correo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anyadir_cliente);
        dbGestiPedi = new DbGestiPedi(getApplicationContext());

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
    }


    public void insertClient(View view) {
        if(dni.getText().toString().length() == 9 && !nombre.getText().toString().isEmpty() && !apellidos.getText().toString().isEmpty() && !empresa.getText().toString().isEmpty() && cp.getText().toString().length() == 5 && !direccion.getText().toString().isEmpty() && !ciudad.getText().toString().isEmpty() && !pais.getText().toString().isEmpty() && telefono.getText().toString().length() == 9 && ! correo.getText().toString().isEmpty()){
            dbGestiPedi.agregarCliente(dni.getText().toString(), nombre.getText().toString(), apellidos.getText().toString(), empresa.getText().toString(),direccion.getText().toString(),cp.getText().toString(),ciudad.getText().toString(),pais.getText().toString(), telefono.getText().toString(), correo.getText().toString());
        }
        finish();
    }

    public void cancel(View view) {
        finish();
    }


}
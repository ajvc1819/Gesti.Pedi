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
        nombre =  findViewById(R.id.tvmNombre);
        apellidos =  findViewById(R.id.tvmApellidos);
        empresa =  findViewById(R.id.tvmEmpresa);
        cp =  findViewById(R.id.tvmCP);
        direccion =  findViewById(R.id.tvmDireccion);
        ciudad =  findViewById(R.id.tvmCiudad);
        pais =  findViewById(R.id.tvmPais);
        telefono =  findViewById(R.id.tvmTelf);
        correo =  findViewById(R.id.tvmEmail);
    }


    public void insertClient(View view) {
        if(dni.getText().toString().length() == 9 && !nombre.getText().toString().isEmpty() && !apellidos.getText().toString().isEmpty() && !empresa.getText().toString().isEmpty() && cp.getText().toString().length() == 5 && !direccion.getText().toString().isEmpty() && !ciudad.getText().toString().isEmpty() && !pais.getText().toString().isEmpty() && telefono.getText().toString().length() == 9 && ! correo.getText().toString().isEmpty()){
            dbGestiPedi.agregarCliente(dni.getText().toString(), nombre.getText().toString(), apellidos.getText().toString(), empresa.getText().toString(), cp.getText().toString(),direccion.getText().toString(),ciudad.getText().toString(),pais.getText().toString(), telefono.getText().toString(), correo.getText().toString());
        }
        finish();
    }

    public void cancel(View view) {
        finish();
    }
}
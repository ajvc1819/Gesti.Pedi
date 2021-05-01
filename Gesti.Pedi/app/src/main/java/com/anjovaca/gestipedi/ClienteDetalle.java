package com.anjovaca.gestipedi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class ClienteDetalle extends AppCompatActivity {

    public static final String EXTRA_ID =
            "com.example.android.twoactivities.extra.id";
    DbGestiPedi dbGestiPedi;
    int id;
    TextView dni, nombre, apellidos, empresa,cp, direccion, ciudad, pais, telefono, correo;
    public List<ClienteModelo> clienteModeloList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_detalle);
        Intent intent = getIntent();
        id = intent.getIntExtra(ClientActivity.EXTRA_ID, 0);

        dni = findViewById(R.id.tvmDni);
        nombre =  findViewById(R.id.tvmNombre);
        apellidos =  findViewById(R.id.tvmApellidos);
        empresa =  findViewById(R.id.tvmEmpresa);
        cp =  findViewById(R.id.tvmCP);
        direccion =  findViewById(R.id.tvmDireccion);
        ciudad =  findViewById(R.id.tvmCiudad);
        pais =  findViewById(R.id.tvmPais);
        telefono =  findViewById(R.id.tvmTelf);
        correo =  findViewById(R.id.tvmEmail);

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
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        id = intent.getIntExtra(ClientActivity.EXTRA_ID, 0);

        dni = findViewById(R.id.tvmDni);
        nombre =  findViewById(R.id.tvmNombre);
        apellidos =  findViewById(R.id.tvmApellidos);
        empresa =  findViewById(R.id.tvmEmpresa);
        cp =  findViewById(R.id.tvmCP);
        direccion =  findViewById(R.id.tvmDireccion);
        ciudad =  findViewById(R.id.tvmCiudad);
        pais =  findViewById(R.id.tvmPais);
        telefono =  findViewById(R.id.tvmTelf);
        correo =  findViewById(R.id.tvmEmail);

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
    }

    public void deleteClient(View view) {
        dbGestiPedi.deleteClient(id);
        finish();
    }

    public void editClient(View view) {
        Intent intent = new Intent(getApplicationContext(),EditClient.class);
        intent.putExtra(EXTRA_ID, id);
        startActivity(intent);
    }
}
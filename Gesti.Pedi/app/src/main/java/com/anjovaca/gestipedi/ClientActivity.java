package com.anjovaca.gestipedi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class ClientActivity extends AppCompatActivity {

    public static final String EXTRA_ID =
            "com.example.android.twoactivities.extra.id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        final RecyclerView recyclerViewClient = findViewById(R.id.rvClient);
        recyclerViewClient.setLayoutManager(new LinearLayoutManager(this));

        final DbGestiPedi dbGestiPedi = new DbGestiPedi(getApplicationContext());

        final ClienteAdaptador clienteAdaptador = new ClienteAdaptador(dbGestiPedi.mostrarClientes());

        clienteAdaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int clientId =  clienteAdaptador.clienteModeloList.get(recyclerViewClient.getChildAdapterPosition(v)).getId();
                Intent intent = new Intent(getApplicationContext(), ClienteDetalle.class);
                intent.putExtra(EXTRA_ID, clientId);
                startActivity(intent);
            }
        });

        recyclerViewClient.setAdapter(clienteAdaptador);
    }

    @Override
    protected void onStart() {
        super.onStart();
        
        final RecyclerView recyclerViewClient = findViewById(R.id.rvClient);
        recyclerViewClient.setLayoutManager(new LinearLayoutManager(this));
        final DbGestiPedi dbGestiPedi = new DbGestiPedi(getApplicationContext());

        final ClienteAdaptador clienteAdaptador = new ClienteAdaptador(dbGestiPedi.mostrarClientes());

        clienteAdaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int clientId =  clienteAdaptador.clienteModeloList.get(recyclerViewClient.getChildAdapterPosition(v)).getId();
                Intent intent = new Intent(getApplicationContext(), ClienteDetalle.class);
                intent.putExtra(EXTRA_ID, clientId);
                startActivity(intent);
            }
        });

        recyclerViewClient.setAdapter(clienteAdaptador);
    }

    public void anyadirCliente(View view) {
        Intent intent = new Intent(this, AnyadirCliente.class);
        startActivity(intent);
    }

    public void selectClient(View view) {

    }
}
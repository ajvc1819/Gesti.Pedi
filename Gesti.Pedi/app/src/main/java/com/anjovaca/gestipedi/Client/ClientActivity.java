package com.anjovaca.gestipedi.Client;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.anjovaca.gestipedi.DB.DbGestiPedi;
import com.anjovaca.gestipedi.LogIn.InitSession;
import com.anjovaca.gestipedi.LogIn.LogOut;
import com.anjovaca.gestipedi.R;

public class ClientActivity extends AppCompatActivity {

    public static final String EXTRA_ID =
            "com.example.android.twoactivities.extra.id";
    public boolean login;

    public static final String EXTRA_LOGED_IN =
            "com.example.android.twoactivities.extra.login";

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

        String sharedPrefFile = "com.example.android.hellosharedprefs";
        SharedPreferences mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        String LOG_KEY = "log";
        login = mPreferences.getBoolean(LOG_KEY, login);
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

    public void anyadirCliente(View view) {
        Intent intent = new Intent(this, AnyadirCliente.class);
        startActivity(intent);
    }

    public void selectClient(View view) {

    }
}
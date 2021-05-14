package com.anjovaca.gestipedi.Client;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.anjovaca.gestipedi.DB.DbGestiPedi;
import com.anjovaca.gestipedi.DB.Models.ClientModel;
import com.anjovaca.gestipedi.LogIn.LogIn;
import com.anjovaca.gestipedi.LogIn.LogOut;
import com.anjovaca.gestipedi.Order.ShoppingCart;
import com.anjovaca.gestipedi.R;

import java.util.ArrayList;
import java.util.List;

public class ClientActivity extends AppCompatActivity {
    public Button btnAddClient;
    public static final String EXTRA_ID =
            "com.example.android.twoactivities.extra.id";
    public boolean login;
    public List<ClientModel> clientModelList;
    public String rol;
    public String ROL_KEY = "rol";
    int orderId;
    public static final String EXTRA_LOGED_IN =
            "com.example.android.twoactivities.extra.login";
    public ClientAdapter clientAdapter;
    EditText buscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        final RecyclerView recyclerViewClient = findViewById(R.id.rvClient);
        recyclerViewClient.setLayoutManager(new LinearLayoutManager(this));

        final DbGestiPedi dbGestiPedi = new DbGestiPedi(getApplicationContext());

        buscar = findViewById(R.id.etBuscar);
        buscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filtrar(s.toString());
            }
        });

        clientAdapter = new ClientAdapter(ClientActivity.this, dbGestiPedi.showClients());

        clientModelList = dbGestiPedi.showClients();

        clientAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int clientId = clientAdapter.clientModelList.get(recyclerViewClient.getChildAdapterPosition(v)).getId();
                Intent intent = new Intent(getApplicationContext(), ClientDetail.class);
                intent.putExtra(EXTRA_ID, clientId);
                startActivity(intent);
            }
        });

        recyclerViewClient.setAdapter(clientAdapter);

        String sharedPrefFile = "com.example.android.hellosharedprefs";
        SharedPreferences mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        String LOG_KEY = "log";
        login = mPreferences.getBoolean(LOG_KEY, login);
        rol = mPreferences.getString(ROL_KEY, rol);
        String ORDER_ID_KEY = "id";
        orderId = mPreferences.getInt(ORDER_ID_KEY, orderId);
        btnAddClient = findViewById(R.id.btnAddClient);

        if (!rol.equals("Administrador")) {
            btnAddClient.setVisibility(View.INVISIBLE);
        }
    }

    public void filtrar(String text) {
        ArrayList<ClientModel> filterList = new ArrayList<>();

        for (ClientModel client : clientModelList) {
            if (client.getEnterprise().toLowerCase().contains(text.toLowerCase())) {
                filterList.add(client);
            }
        }
        clientAdapter.filter(filterList);
    }

    @Override
    protected void onStart() {
        super.onStart();

        final RecyclerView recyclerViewClient = findViewById(R.id.rvClient);
        recyclerViewClient.setLayoutManager(new LinearLayoutManager(this));
        final DbGestiPedi dbGestiPedi = new DbGestiPedi(getApplicationContext());

        buscar = findViewById(R.id.etBuscar);
        buscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filtrar(s.toString());
            }
        });
        clientAdapter = new ClientAdapter(ClientActivity.this, dbGestiPedi.showClients());

        clientAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int clientId = clientAdapter.clientModelList.get(recyclerViewClient.getChildAdapterPosition(v)).getId();
                Intent intent = new Intent(getApplicationContext(), ClientDetail.class);
                intent.putExtra(EXTRA_ID, clientId);
                startActivity(intent);
            }
        });

        recyclerViewClient.setAdapter(clientAdapter);

        String sharedPrefFile = "com.example.android.hellosharedprefs";
        SharedPreferences mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        String LOG_KEY = "log";
        login = mPreferences.getBoolean(LOG_KEY, login);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.ShoppingCart);

        if (orderId == 0) {
            item.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int idLogin = item.getItemId();
        int idShopping = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (idLogin == R.id.initSession) {
            Intent intent;
            if (login) {
                intent = new Intent(getApplicationContext(), LogOut.class);
                intent.putExtra(EXTRA_LOGED_IN, login);
            } else {
                intent = new Intent(this, LogIn.class);
            }
            startActivity(intent);
        }

        if (idShopping == R.id.ShoppingCart) {
            Intent intent = new Intent(getApplicationContext(), ShoppingCart.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public void addClient(View view) {
        Intent intent = new Intent(this, AddClient.class);
        startActivity(intent);
    }

    public void deleteText(View view) {
        buscar.setText("");
    }

}
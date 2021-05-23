package com.anjovaca.gestipedi.Order;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.anjovaca.gestipedi.Client.ClientActivity;
import com.anjovaca.gestipedi.Client.ClientAdapter;
import com.anjovaca.gestipedi.Client.ClientDetail;
import com.anjovaca.gestipedi.DB.DbGestiPedi;
import com.anjovaca.gestipedi.DB.Models.ClientModel;
import com.anjovaca.gestipedi.DB.Models.OrderModel;
import com.anjovaca.gestipedi.R;

import java.util.List;

public class SelectClientForOrder extends AppCompatActivity {

    public Button btnAddClient;
    public static final String EXTRA_ID =
            "com.example.android.twoactivities.extra.id";
    public boolean login;
    public List<ClientModel> clientModelList;
    public String rol;
    public String ROL_KEY = "rol";
    int orderId;
    int idUser;
    public static final String EXTRA_LOGED_IN =
            "com.example.android.twoactivities.extra.login";
    public ClientAdapter clientAdapter;
    EditText buscar;
    String sharedPrefFile = "com.example.android.hellosharedprefs";
    SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_client_for_order);
        String USER_KEY = "user";
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        idUser = mPreferences.getInt(USER_KEY, idUser);
        final RecyclerView recyclerViewClient = findViewById(R.id.rvClient);
        recyclerViewClient.setLayoutManager(new LinearLayoutManager(this));

        final DbGestiPedi dbGestiPedi = new DbGestiPedi(getApplicationContext());

        clientAdapter = new ClientAdapter(SelectClientForOrder.this, dbGestiPedi.showClients());

        clientModelList = dbGestiPedi.showClients();

        clientAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int clientId = clientAdapter.clientModelList.get(recyclerViewClient.getChildAdapterPosition(v)).getId();
                dbGestiPedi.insertOrder(clientId, idUser);
                SharedPreferences.Editor preferencesEditor = mPreferences.edit();
                List<OrderModel> orderDetailModelList = dbGestiPedi.selectLastOrder();
                int id = orderDetailModelList.get(0).getId();

                String ORDER_ID_KEY = "id";
                preferencesEditor.putInt(ORDER_ID_KEY, id);
                preferencesEditor.apply();

                Intent intent = new Intent(getApplicationContext(), ShoppingCart.class);
                startActivity(intent);
            }
        });

        recyclerViewClient.setAdapter(clientAdapter);
    }

    public void returnMainMenu(View view) {
    }
}
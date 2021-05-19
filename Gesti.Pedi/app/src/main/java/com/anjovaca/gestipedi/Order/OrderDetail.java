package com.anjovaca.gestipedi.Order;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.anjovaca.gestipedi.Client.ClientActivity;
import com.anjovaca.gestipedi.DB.DbGestiPedi;
import com.anjovaca.gestipedi.DB.Models.ClientModel;
import com.anjovaca.gestipedi.DB.Models.OrderDetailModel;
import com.anjovaca.gestipedi.DB.Models.OrderModel;
import com.anjovaca.gestipedi.DB.Models.UserModel;
import com.anjovaca.gestipedi.R;

import java.util.List;

public class OrderDetail extends AppCompatActivity {

    public static final String EXTRA_ID =
            "com.example.android.twoactivities.extra.id";
    DbGestiPedi dbGestiPedi;
    int id;
    TextView idOrder, client, date, state, total, user;
    public List<ClientModel> clientModelList;
    public List<OrderModel> orderModelList;
    public List<OrderDetailModel> orderDetailModelList;
    public List<UserModel> userModelList;
    public String rol;
    public boolean login;
    Button btnEdit, btnDelete;
    public static final String EXTRA_LOGED_IN =
            "com.example.android.twoactivities.extra.login";

    String sharedPrefFile = "com.example.android.hellosharedprefs";
    SharedPreferences mPreferences;
    int orderId;
    public OrderDetailAdapter orderDetailAdapter;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        Intent intent = getIntent();
        final RecyclerView recyclerViewOrder = findViewById(R.id.rvProductsOrder);

        id = intent.getIntExtra(OrderActivity.EXTRA_ID, 0);

        idOrder = findViewById(R.id.tvmIdOrderD);
        client = findViewById(R.id.tvmClientOrderD);
        date = findViewById(R.id.tvmDateOrderD);
        state = findViewById(R.id.tvmStateOrderD);
        total = findViewById(R.id.tvmTotalOrderD);
        user = findViewById(R.id.tvmUsuarioOrderD);

        dbGestiPedi = new DbGestiPedi(getApplicationContext());

        orderModelList = dbGestiPedi.getOrderById(id);
        clientModelList = dbGestiPedi.getClientsById(orderModelList.get(0).getIdClient());
        userModelList = dbGestiPedi.getUserById(orderModelList.get(0).getIdUser());

        idOrder.setText(Integer.toString(orderModelList.get(0).getId()));
        client.setText(clientModelList.get(0).getEnterprise());
        date.setText(orderModelList.get(0).getDate());
        state.setText(orderModelList.get(0).getState());
        user.setText(userModelList.get(0).getUsername());
        total.setText(orderModelList.get(0).getTotal() + "€");

        orderDetailModelList = dbGestiPedi.showOrderDetail(Integer.parseInt((String) idOrder.getText()));

        orderDetailAdapter = new OrderDetailAdapter(OrderDetail.this, dbGestiPedi.showOrderDetail(Integer.parseInt((String) idOrder.getText())), dbGestiPedi.showProducts(), Integer.parseInt((String) idOrder.getText()));

        recyclerViewOrder.setAdapter(orderDetailAdapter);

        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        String LOG_KEY = "log";
        login = mPreferences.getBoolean(LOG_KEY, login);
        String ROL_KEY = "rol";
        rol = mPreferences.getString(ROL_KEY, rol);
        String ORDER_ID_KEY = "id";
        orderId = mPreferences.getInt(ORDER_ID_KEY, orderId);

        btnEdit = findViewById(R.id.btnEditOrder);
        btnDelete = findViewById(R.id.btnDeleteOrder);

        if (orderModelList.get(0).getState() != "En Proceso") {
            btnDelete.setVisibility(View.INVISIBLE);
            btnEdit.setVisibility(View.INVISIBLE);
        }
        else {
            btnDelete.setVisibility(View.VISIBLE);
            btnEdit.setVisibility(View.VISIBLE);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = getIntent();
        final RecyclerView recyclerViewOrder = findViewById(R.id.rvProductsOrder);

        id = intent.getIntExtra(OrderActivity.EXTRA_ID, 0);

        idOrder = findViewById(R.id.tvmIdOrderD);
        client = findViewById(R.id.tvmClientOrderD);
        date = findViewById(R.id.tvmDateOrderD);
        state = findViewById(R.id.tvmStateOrderD);
        total = findViewById(R.id.tvmTotalOrderD);

        dbGestiPedi = new DbGestiPedi(getApplicationContext());

        orderModelList = dbGestiPedi.getOrderById(id);
        clientModelList = dbGestiPedi.getClientsById(orderModelList.get(0).getIdClient());
        userModelList = dbGestiPedi.getUserById(orderModelList.get(0).getIdUser());

        idOrder.setText(Integer.toString(orderModelList.get(0).getId()));
        client.setText(clientModelList.get(0).getEnterprise());
        date.setText(orderModelList.get(0).getDate());
        state.setText(orderModelList.get(0).getState());
        user.setText(userModelList.get(0).getUsername());
        total.setText(orderModelList.get(0).getTotal() + "€");

        orderDetailModelList = dbGestiPedi.showOrderDetail(id);

        orderDetailAdapter = new OrderDetailAdapter(OrderDetail.this, orderDetailModelList, dbGestiPedi.showProducts(), id);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerViewOrder.setLayoutManager(manager);
        recyclerViewOrder.setHasFixedSize(true);
        recyclerViewOrder.setAdapter(orderDetailAdapter);

        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        String LOG_KEY = "log";
        login = mPreferences.getBoolean(LOG_KEY, login);
        String ROL_KEY = "rol";
        rol = mPreferences.getString(ROL_KEY, rol);
        String ORDER_ID_KEY = "id";
        orderId = mPreferences.getInt(ORDER_ID_KEY, orderId);

        btnEdit = findViewById(R.id.btnEditOrder);
        btnDelete = findViewById(R.id.btnDeleteOrder);

        if (orderModelList.get(0).getState().equals("En Proceso")) {
            btnDelete.setVisibility(View.VISIBLE);
            btnEdit.setVisibility(View.VISIBLE);
        } else {
            btnDelete.setVisibility(View.INVISIBLE);
            btnEdit.setVisibility(View.INVISIBLE);
        }
    }

    public void editOrder(View view) {
        if(orderId == 0 || orderId == id){
            orderId = id;
            Intent intent = new Intent(getApplicationContext(), ShoppingCart.class);
            SharedPreferences.Editor preferencesEditor = mPreferences.edit();
            String ORDER_ID_KEY = "id";
            preferencesEditor.putInt(ORDER_ID_KEY, orderId);
            preferencesEditor.apply();
            startActivity(intent);
        }
    }

    public void deleteOrder(View view) {
        dbGestiPedi.deleteOrder(id);
        orderId = 0;
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        String ORDER_ID_KEY = "id";
        preferencesEditor.putInt(ORDER_ID_KEY, orderId);
        preferencesEditor.apply();
        Intent intent = new Intent(getApplicationContext(), OrderActivity.class);
        startActivity(intent);
    }
}
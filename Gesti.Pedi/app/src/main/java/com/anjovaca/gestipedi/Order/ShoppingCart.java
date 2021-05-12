package com.anjovaca.gestipedi.Order;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.anjovaca.gestipedi.DB.DbGestiPedi;
import com.anjovaca.gestipedi.R;

import java.util.List;

public class ShoppingCart extends AppCompatActivity {


    public static final String EXTRA_ID =
            "com.example.android.twoactivities.extra.id";
    public boolean login;
    public List<OrderDetailModel> orderDetailModelList;
    public String rol;
    public String ROL_KEY = "rol";
    int idOrder;

    public static final String EXTRA_LOGED_IN =
            "com.example.android.twoactivities.extra.login";
    public ShoppingCartAdapter orderAdapter;

    String sharedPrefFile = "com.example.android.hellosharedprefs";
    SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        String ORDER_ID_KEY = "id";
        idOrder = mPreferences.getInt(ORDER_ID_KEY, idOrder);

        final DbGestiPedi dbGestiPedi = new DbGestiPedi(getApplicationContext());
        final RecyclerView recyclerViewShopping = findViewById(R.id.rvShoppingCart);
        recyclerViewShopping.setLayoutManager(new LinearLayoutManager(this));

        orderAdapter = new ShoppingCartAdapter(ShoppingCart.this,dbGestiPedi.showOrderDetail(idOrder));

        orderDetailModelList = dbGestiPedi.showOrderDetail(idOrder);

        orderAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int clientId =  orderAdapter.orderDetailModelList.get(recyclerViewShopping.getChildAdapterPosition(v)).getId();
                Intent intent = new Intent(getApplicationContext(), OrderDetail.class);
                intent.putExtra(EXTRA_ID, clientId);
                startActivity(intent);
            }
        });

        recyclerViewShopping.setAdapter(orderAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        final DbGestiPedi dbGestiPedi = new DbGestiPedi(getApplicationContext());
        final RecyclerView recyclerViewShopping = findViewById(R.id.rvShoppingCart);
        recyclerViewShopping.setLayoutManager(new LinearLayoutManager(this));

        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        String ORDER_ID_KEY = "id";
        idOrder = mPreferences.getInt(ORDER_ID_KEY, idOrder);

        orderAdapter = new ShoppingCartAdapter(ShoppingCart.this,dbGestiPedi.showOrderDetail(idOrder));

        orderDetailModelList = dbGestiPedi.showOrderDetail(idOrder);

        orderAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int clientId =  orderAdapter.orderDetailModelList.get(recyclerViewShopping.getChildAdapterPosition(v)).getId();
                Intent intent = new Intent(getApplicationContext(), OrderDetail.class);
                intent.putExtra(EXTRA_ID, clientId);
                startActivity(intent);
            }
        });

        recyclerViewShopping.setAdapter(orderAdapter);
    }

    public void addProduct(View view) {
        Intent intent = new Intent(getApplicationContext(), AddProductToShoppingCart.class);
        startActivity(intent);
    }
}
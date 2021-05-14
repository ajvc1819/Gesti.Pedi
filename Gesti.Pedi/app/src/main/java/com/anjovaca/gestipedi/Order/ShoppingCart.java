package com.anjovaca.gestipedi.Order;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.anjovaca.gestipedi.DB.DbGestiPedi;
import com.anjovaca.gestipedi.DB.Models.OrderDetailModel;
import com.anjovaca.gestipedi.DB.Models.ProductsModel;
import com.anjovaca.gestipedi.Main.MainActivity;
import com.anjovaca.gestipedi.R;

import java.util.List;

public class ShoppingCart extends AppCompatActivity {

    public static final String EXTRA_ID =
            "com.example.android.twoactivities.extra.id";
    public boolean login;
    public List<OrderDetailModel> orderDetailModelList;
    int idOrder;
    public DbGestiPedi dbGestiPedi;
    public static final String EXTRA_LOGED_IN =
            "com.example.android.twoactivities.extra.login";
    public ShoppingCartAdapter orderAdapter;
    int orderId;
    String sharedPrefFile = "com.example.android.hellosharedprefs";
    SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        String ORDER_ID_KEY = "id";
        idOrder = mPreferences.getInt(ORDER_ID_KEY, idOrder);

        dbGestiPedi = new DbGestiPedi(getApplicationContext());
        final RecyclerView recyclerViewShopping = findViewById(R.id.rvShoppingCart);
        recyclerViewShopping.setLayoutManager(new LinearLayoutManager(this));

        orderAdapter = new ShoppingCartAdapter(ShoppingCart.this, dbGestiPedi.showOrderDetail(idOrder));

        orderDetailModelList = dbGestiPedi.showOrderDetail(idOrder);

        orderAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int clientId = orderAdapter.orderDetailModelList.get(recyclerViewShopping.getChildAdapterPosition(v)).getId();
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
        dbGestiPedi = new DbGestiPedi(getApplicationContext());
        final RecyclerView recyclerViewShopping = findViewById(R.id.rvShoppingCart);
        recyclerViewShopping.setLayoutManager(new LinearLayoutManager(this));

        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        String ORDER_ID_KEY = "id";
        idOrder = mPreferences.getInt(ORDER_ID_KEY, idOrder);

        orderAdapter = new ShoppingCartAdapter(ShoppingCart.this, dbGestiPedi.showOrderDetail(idOrder));

        orderDetailModelList = dbGestiPedi.showOrderDetail(idOrder);

        orderAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int clientId = orderAdapter.orderDetailModelList.get(recyclerViewShopping.getChildAdapterPosition(v)).getId();
                Intent intent = new Intent(getApplicationContext(), OrderDetail.class);
                intent.putExtra(EXTRA_ID, clientId);
                startActivity(intent);
            }
        });

        recyclerViewShopping.setAdapter(orderAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void addProduct(View view) {
        Intent intent = new Intent(getApplicationContext(), AddProductToShoppingCart.class);
        startActivity(intent);
    }

    public void confirmOrder(View view) {
        List<OrderDetailModel> orderDetailModelList = dbGestiPedi.showOrderDetail(idOrder);

        for (OrderDetailModel orderDetailModel : orderDetailModelList) {
            int quantity = orderDetailModel.getQuantity();
            int idProd = orderDetailModel.getIdProduct();

            List<ProductsModel> products = dbGestiPedi.selectProductById(idProd);
            int stock = products.get(0).getStock();

            dbGestiPedi.decreaseStock(idProd, quantity, stock);

        }

        dbGestiPedi.confirmOrder(idOrder);
        orderId = 0;
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        String ORDER_ID_KEY = "id";
        preferencesEditor.putInt(ORDER_ID_KEY, orderId);
        preferencesEditor.apply();

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}
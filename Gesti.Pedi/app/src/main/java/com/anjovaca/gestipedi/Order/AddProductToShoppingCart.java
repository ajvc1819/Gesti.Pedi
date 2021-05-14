package com.anjovaca.gestipedi.Order;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.anjovaca.gestipedi.DB.DbGestiPedi;
import com.anjovaca.gestipedi.DB.Models.OrderDetailModel;
import com.anjovaca.gestipedi.DB.Models.ProductsModel;
import com.anjovaca.gestipedi.R;
import com.anjovaca.gestipedi.Stock.ProductAdapter;

import java.util.List;

public class AddProductToShoppingCart extends AppCompatActivity {
    public ProductAdapter productAdapter;
    public boolean login;
    public static final String EXTRA_PRODUCT_ID =
            "com.example.android.twoactivities.extra.ID";
    public List<ProductsModel> productsModelList;
    public int orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product_to_shopping_cart);

        String sharedPrefFile = "com.example.android.hellosharedprefs";
        SharedPreferences mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        String LOG_KEY = "log";
        login = mPreferences.getBoolean(LOG_KEY, login);
        String ORDER_ID_KEY = "id";
        orderId = mPreferences.getInt(ORDER_ID_KEY, orderId);

        final RecyclerView recyclerViewProduct = findViewById(R.id.rvProducts);
        recyclerViewProduct.setLayoutManager(new LinearLayoutManager(this));

        final DbGestiPedi dbGestiPedi = new DbGestiPedi(getApplicationContext());
        productsModelList = dbGestiPedi.showProducts();

        productAdapter = new ProductAdapter(dbGestiPedi.showProducts());

        productAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int productId = productAdapter.productsModelList.get(recyclerViewProduct.getChildAdapterPosition(v)).getId();
                List<ProductsModel> productsModelList = dbGestiPedi.selectProductById(productId);
                List<OrderDetailModel> orderDetailModelList = dbGestiPedi.checkOrderDetail(productId, orderId);
                double price = productsModelList.get(0).getPrice();
                if (!orderDetailModelList.isEmpty()) {
                    int id = orderDetailModelList.get(0).getId();
                    int quantity = orderDetailModelList.get(0).getQuantity();
                    dbGestiPedi.increaseQuantity(id, quantity);
                } else {
                    dbGestiPedi.insertOrderDetail(price, orderId, productId);
                }
                finish();
            }
        });

        recyclerViewProduct.setAdapter(productAdapter);
    }
}
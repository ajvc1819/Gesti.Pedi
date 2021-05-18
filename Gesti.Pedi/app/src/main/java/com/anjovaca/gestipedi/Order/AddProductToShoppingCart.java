package com.anjovaca.gestipedi.Order;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.anjovaca.gestipedi.DB.DbGestiPedi;
import com.anjovaca.gestipedi.DB.Models.OrderDetailModel;
import com.anjovaca.gestipedi.DB.Models.ProductsModel;
import com.anjovaca.gestipedi.R;
import com.anjovaca.gestipedi.Stock.ProductAdapter;

import java.util.List;

public class AddProductToShoppingCart extends AppCompatActivity {
    public ProductAdapter productAdapter;
    public boolean login;
    public List<ProductsModel> productsModelList;
    public int orderId;
    DbGestiPedi dbGestiPedi;

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

        dbGestiPedi = new DbGestiPedi(getApplicationContext());
        productsModelList = dbGestiPedi.showProducts();

        productAdapter = new ProductAdapter(dbGestiPedi.showProducts());

        productAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int productId = productAdapter.productsModelList.get(recyclerViewProduct.getChildAdapterPosition(v)).getId();
                List<ProductsModel> productsModelList = dbGestiPedi.selectProductById(productId);
                List<OrderDetailModel> orderDetailModelList = dbGestiPedi.checkOrderDetail(productId, orderId);
                double priceProduct = productsModelList.get(0).getPrice();
                int stock = productsModelList.get(0).getStock();

                if (!orderDetailModelList.isEmpty()) {
                    int id = orderDetailModelList.get(0).getId();
                    int quantity = orderDetailModelList.get(0).getQuantity();
                    double priceDetail = orderDetailModelList.get(0).getPrice();
                    dbGestiPedi.increaseQuantity(id, stock, quantity, priceDetail, priceProduct, orderId);
                    double totalPrice = updateTotalPriceOrder();
                    dbGestiPedi.updateTotalPrice(orderId, totalPrice);

                } else {
                    if (stock > 0) {
                        dbGestiPedi.insertOrderDetail(priceProduct, orderId, productId);
                        double totalPrice = updateTotalPriceOrder();
                        dbGestiPedi.updateTotalPrice(orderId, totalPrice);
                    } else {
                        Toast.makeText(getApplicationContext(), "No se puede añadir el producto al carrito por que no hay existencias en el almacen.", Toast.LENGTH_SHORT).show();
                    }
                }

                finish();


            }
        });

        recyclerViewProduct.setAdapter(productAdapter);
    }

    public double updateTotalPriceOrder() {
        double totalPrice = 0;
        List<OrderDetailModel> orderDetailModelList = dbGestiPedi.showOrderDetail(orderId);

        for (OrderDetailModel orderDetail : orderDetailModelList) {
            double price = orderDetail.getPrice();
            totalPrice += price;
        }

        return totalPrice;
    }
}
package com.anjovaca.gestipedi.Order;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.anjovaca.gestipedi.Client.ClientDetail;
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
    public List<ProductsModel> productsModelList;
    int idOrder;
    public DbGestiPedi dbGestiPedi;
    public static final String EXTRA_LOGED_IN =
            "com.example.android.twoactivities.extra.login";
    public ShoppingCartAdapter orderAdapter;
    int orderId;
    String sharedPrefFile = "com.example.android.hellosharedprefs";
    SharedPreferences mPreferences;
    RecyclerView recyclerViewShopping;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        String ORDER_ID_KEY = "id";
        idOrder = mPreferences.getInt(ORDER_ID_KEY, idOrder);

        dbGestiPedi = new DbGestiPedi(getApplicationContext());
        recyclerViewShopping = findViewById(R.id.rvShoppingCart);
        recyclerViewShopping.setLayoutManager(new LinearLayoutManager(this));

        orderDetailModelList = dbGestiPedi.showOrderDetail(idOrder);

        orderAdapter = new ShoppingCartAdapter(ShoppingCart.this, dbGestiPedi.showOrderDetail(idOrder), dbGestiPedi.showProducts(), idOrder);

        recyclerViewShopping.setAdapter(orderAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        dbGestiPedi = new DbGestiPedi(getApplicationContext());
        recyclerViewShopping = findViewById(R.id.rvShoppingCart);
        recyclerViewShopping.setLayoutManager(new LinearLayoutManager(this));

        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        String ORDER_ID_KEY = "id";
        idOrder = mPreferences.getInt(ORDER_ID_KEY, idOrder);

        orderAdapter = new ShoppingCartAdapter(ShoppingCart.this, dbGestiPedi.showOrderDetail(idOrder), dbGestiPedi.showProducts(), idOrder);

        orderDetailModelList = dbGestiPedi.showOrderDetail(idOrder);

        recyclerViewShopping.setAdapter(orderAdapter);
    }

    //Función que permite la creación de funcionalidades de los elementos que se muestran en el menú superior.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Función que permite llamar a la actividad AddProductToShoppingCart.
    public void addProduct(View view) {
        Intent intent = new Intent(getApplicationContext(), AddProductToShoppingCart.class);
        startActivity(intent);
    }

    //Función que permite la confirmación del pedido que está en curso.
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

    //Función que permite la eliminación del pedido que se encuentra en proceso.
    public void cancelOrder(View view) {

        for (OrderDetailModel orderDetailModel : orderDetailModelList) {
            dbGestiPedi.deleteOrderDetailById(orderDetailModel.getId());
        }

        dbGestiPedi.deleteOrder(idOrder);

        orderId = 0;
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        String ORDER_ID_KEY = "id";
        preferencesEditor.putInt(ORDER_ID_KEY, orderId);
        preferencesEditor.apply();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    //Función que permite regresar al menú principal al pulsar sobre el logotipo de la empresa.
    public void returnMainMenu(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}
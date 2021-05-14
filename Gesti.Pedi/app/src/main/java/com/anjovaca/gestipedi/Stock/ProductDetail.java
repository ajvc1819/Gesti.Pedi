package com.anjovaca.gestipedi.Stock;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.anjovaca.gestipedi.DB.DbGestiPedi;
import com.anjovaca.gestipedi.DB.Models.ProductsModel;
import com.anjovaca.gestipedi.LogIn.LogIn;
import com.anjovaca.gestipedi.LogIn.LogOut;
import com.anjovaca.gestipedi.Order.ShoppingCart;
import com.anjovaca.gestipedi.R;

import java.util.List;

public class ProductDetail extends AppCompatActivity {

    public static final String EXTRA_ID =
            "com.example.android.twoactivities.extra.id";
    DbGestiPedi dbGestiPedi;
    int id;
    TextView name, description, stock, price, category;
    ImageView imageProduct;

    public List<ProductsModel> productsModelList;
    int orderId;
    public boolean login;
    public String rol;

    public Button btnEdit, btnDelete;
    public static final String EXTRA_LOGED_IN =
            "com.example.android.twoactivities.extra.login";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        Intent intent = getIntent();
        id = intent.getIntExtra(StockActivity.EXTRA_PRODUCT_ID, 0);

        name = findViewById(R.id.tvmNombreProdD);
        description = findViewById(R.id.tvmDescProdD);
        stock = findViewById(R.id.tvmStockProdD);
        price = findViewById(R.id.tvmPrecioProdD);
        category = findViewById(R.id.tvmCategoriaProdD);
        imageProduct = findViewById(R.id.imgProduct);

        dbGestiPedi = new DbGestiPedi(getApplicationContext());

        productsModelList = dbGestiPedi.selectProductById(id);

        name.setText(productsModelList.get(0).getName());
        description.setText(productsModelList.get(0).getDescription());
        stock.setText(Integer.toString(productsModelList.get(0).getStock()));
        price.setText(Double.toString(productsModelList.get(0).getPrice()));
        category.setText(productsModelList.get(0).getCategory());
        imageProduct.setImageURI(Uri.parse(productsModelList.get(0).getImage()));

        String sharedPrefFile = "com.example.android.hellosharedprefs";
        SharedPreferences mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        String LOG_KEY = "log";
        login = mPreferences.getBoolean(LOG_KEY, login);
        String ROL_KEY = "rol";
        rol = mPreferences.getString(ROL_KEY, rol);
        String ORDER_ID_KEY = "id";
        orderId = mPreferences.getInt(ORDER_ID_KEY, orderId);

        btnDelete = findViewById(R.id.btnDeleteProduct);
        btnEdit = findViewById((R.id.btnEditProd));

        if (!rol.equals("Administrador")) {
            btnDelete.setVisibility(View.INVISIBLE);
            btnEdit.setVisibility(View.INVISIBLE);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = getIntent();
        id = intent.getIntExtra(StockActivity.EXTRA_PRODUCT_ID, 0);

        name = findViewById(R.id.tvmNombreProdD);
        description = findViewById(R.id.tvmDescProdD);
        stock = findViewById(R.id.tvmStockProdD);
        price = findViewById(R.id.tvmPrecioProdD);
        category = findViewById(R.id.tvmCategoriaProdD);
        imageProduct = findViewById(R.id.imgProduct);

        dbGestiPedi = new DbGestiPedi(getApplicationContext());

        productsModelList = dbGestiPedi.selectProductById(id);

        name.setText(productsModelList.get(0).getName());
        description.setText(productsModelList.get(0).getDescription());
        stock.setText(Integer.toString(productsModelList.get(0).getStock()));
        price.setText(Double.toString(productsModelList.get(0).getPrice()));
        category.setText(productsModelList.get(0).getCategory());
        imageProduct.setImageURI(Uri.parse(productsModelList.get(0).getImage()));

        String sharedPrefFile = "com.example.android.hellosharedprefs";
        SharedPreferences mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        String LOG_KEY = "log";
        login = mPreferences.getBoolean(LOG_KEY, login);
        String ORDER_ID_KEY = "id";
        orderId = mPreferences.getInt(ORDER_ID_KEY, orderId);
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

    public void editProduct(View view) {
        Intent intent = new Intent(getApplicationContext(), EditProduct.class);
        intent.putExtra(EXTRA_ID, id);
        startActivity(intent);
    }

    public void deleteProduct(View view) {
        dbGestiPedi.deleteProduct(id);
        finish();
    }
}
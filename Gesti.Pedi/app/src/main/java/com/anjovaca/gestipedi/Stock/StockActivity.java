package com.anjovaca.gestipedi.Stock;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.anjovaca.gestipedi.DB.DbGestiPedi;
import com.anjovaca.gestipedi.DB.Models.ProductsModel;
import com.anjovaca.gestipedi.LogIn.LogIn;
import com.anjovaca.gestipedi.LogIn.LogOut;
import com.anjovaca.gestipedi.Order.ShoppingCart;
import com.anjovaca.gestipedi.R;

import java.util.ArrayList;
import java.util.List;

public class StockActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {
    Button btnAddProduct;
    public ProductAdapter productAdapter;
    String category;
    public List<ProductsModel> productsModelList;
    EditText buscar;
    public boolean login;
    public String rol;
    public String ROL_KEY = "rol";
    public static final String EXTRA_LOGED_IN =
            "com.example.android.twoactivities.extra.login";
    public static final String EXTRA_PRODUCT_ID =
            "com.example.android.twoactivities.extra.ID";
    int orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);

        Spinner spinner = findViewById(R.id.spnSearchCategory);
        if (spinner != null) {
            spinner.setOnItemSelectedListener(this);
        }
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.labels_array_search, R.layout.spinner_item);
        adapter.setDropDownViewResource
                (R.layout.spinner_item);
        if (spinner != null) {
            spinner.setAdapter(adapter);
        }

        final RecyclerView recyclerViewProduct = findViewById(R.id.rvProducts);
        recyclerViewProduct.setLayoutManager(new LinearLayoutManager(this));

        final DbGestiPedi dbGestiPedi = new DbGestiPedi(getApplicationContext());
        productsModelList = dbGestiPedi.showProducts();

        productAdapter = new ProductAdapter(dbGestiPedi.showProducts());

        productAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int productId = productAdapter.productsModelList.get(recyclerViewProduct.getChildAdapterPosition(v)).getId();
                Intent intent = new Intent(getApplicationContext(), ProductDetail.class);
                intent.putExtra(EXTRA_PRODUCT_ID, productId);
                startActivity(intent);
            }
        });

        recyclerViewProduct.setAdapter(productAdapter);

        String sharedPrefFile = "com.example.android.hellosharedprefs";
        SharedPreferences mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        String LOG_KEY = "log";
        login = mPreferences.getBoolean(LOG_KEY, login);
        String ORDER_ID_KEY = "id";
        orderId = mPreferences.getInt(ORDER_ID_KEY, orderId);

        btnAddProduct = findViewById(R.id.btnAddProduct);
        rol = mPreferences.getString(ROL_KEY, rol);

        if (!rol.equals("Administrador")) {
            btnAddProduct.setVisibility(View.INVISIBLE);
        }

        buscar = findViewById(R.id.etBuscarN);
        buscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString(), category);
            }
        });
    }

    public void filter(String nombre, String category) {
        ArrayList<ProductsModel> filterList = new ArrayList<>();
        if (!category.equals("All")) {
            for (ProductsModel product : productsModelList) {
                if (product.getName().toLowerCase().contains(nombre.toLowerCase()) && product.getCategory().toLowerCase().contains(category.toLowerCase())) {
                    filterList.add(product);
                }
            }
        } else {
            for (ProductsModel product : productsModelList) {
                if (product.getName().toLowerCase().contains(nombre.toLowerCase())) {
                    filterList.add(product);
                }
            }
        }

        productAdapter.filter(filterList);
    }

    @Override
    protected void onStart() {
        super.onStart();
        final RecyclerView recyclerViewProduct = findViewById(R.id.rvProducts);
        recyclerViewProduct.setLayoutManager(new LinearLayoutManager(this));

        Spinner spinner = findViewById(R.id.spnSearchCategory);
        if (spinner != null) {
            spinner.setOnItemSelectedListener(this);
        }
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.labels_array_search, R.layout.spinner_item);
        adapter.setDropDownViewResource
                (R.layout.spinner_item);
        if (spinner != null) {
            spinner.setAdapter(adapter);
        }

        final DbGestiPedi dbGestiPedi = new DbGestiPedi(getApplicationContext());
        productsModelList = dbGestiPedi.showProducts();
        productAdapter = new ProductAdapter(dbGestiPedi.showProducts());

        productAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int productId = productAdapter.productsModelList.get(recyclerViewProduct.getChildAdapterPosition(v)).getId();
                Intent intent = new Intent(getApplicationContext(), ProductDetail.class);
                intent.putExtra(EXTRA_PRODUCT_ID, productId);
                startActivity(intent);
            }
        });

        recyclerViewProduct.setAdapter(productAdapter);

        String sharedPrefFile = "com.example.android.hellosharedprefs";
        SharedPreferences mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        String LOG_KEY = "log";
        login = mPreferences.getBoolean(LOG_KEY, login);
        String ORDER_ID_KEY = "id";
        orderId = mPreferences.getInt(ORDER_ID_KEY, orderId);

        buscar = findViewById(R.id.etBuscarN);
        buscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString(), category);
            }
        });
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

    public void addProduct(View view) {
        Intent intent = new Intent(this, AddProduct.class);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        category = parent.getItemAtPosition(position).toString();
        filter(buscar.getText().toString(), category);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void deleteText(View view) {
        buscar.setText("");
    }
}
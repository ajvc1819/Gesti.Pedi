package com.anjovaca.gestipedi.Stock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.anjovaca.gestipedi.Client.ClientActivity;
import com.anjovaca.gestipedi.Client.EditClient;
import com.anjovaca.gestipedi.DB.DbGestiPedi;
import com.anjovaca.gestipedi.DB.Models.ClienteModelo;
import com.anjovaca.gestipedi.DB.Models.ProductsModel;
import com.anjovaca.gestipedi.EditProduct;
import com.anjovaca.gestipedi.LogIn.InitSession;
import com.anjovaca.gestipedi.LogIn.LogOut;
import com.anjovaca.gestipedi.R;

import java.util.List;

public class ProductDetail extends AppCompatActivity {

    public static final String EXTRA_ID =
            "com.example.android.twoactivities.extra.id";
    DbGestiPedi dbGestiPedi;
    int id;
    TextView nombre, descripcion, stock,precio, categoria;
    ImageView imageProduct;

    public List<ProductsModel> productsModelList;

    public boolean login;

    public static final String EXTRA_LOGED_IN =
            "com.example.android.twoactivities.extra.login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        Intent intent = getIntent();
        id = intent.getIntExtra(StockActivity.EXTRA_PRODUCT_ID, 0);

        nombre =  findViewById(R.id.tvmNombreProdD);
        descripcion =  findViewById(R.id.tvmDescProdD);
        stock =  findViewById(R.id.tvmStockProdD);
        precio =  findViewById(R.id.tvmPrecioProdD);
        categoria =  findViewById(R.id.tvmCategoriaProdD);
        imageProduct =  findViewById(R.id.imgProduct);


        dbGestiPedi = new DbGestiPedi(getApplicationContext());

         productsModelList = dbGestiPedi.selectProductById(id);

        nombre.setText(productsModelList.get(0).getName());
        descripcion.setText(productsModelList.get(0).getDescripcion());
        stock.setText(Integer.toString(productsModelList.get(0).getStock()));
        precio.setText(Double.toString(productsModelList.get(0).getPrecio()));
        categoria.setText(productsModelList.get(0).getCategoria());
        imageProduct.setImageURI(Uri.parse(productsModelList.get(0).getImg()));

        String sharedPrefFile = "com.example.android.hellosharedprefs";
        SharedPreferences mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        String LOG_KEY = "log";
        login = mPreferences.getBoolean(LOG_KEY, login);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = getIntent();
        id = intent.getIntExtra(StockActivity.EXTRA_PRODUCT_ID, 0);

        nombre =  findViewById(R.id.tvmNombreProdD);
        descripcion =  findViewById(R.id.tvmDescProdD);
        stock =  findViewById(R.id.tvmStockProdD);
        precio =  findViewById(R.id.tvmPrecioProdD);
        categoria =  findViewById(R.id.tvmCategoriaProdD);
        imageProduct =  findViewById(R.id.imgProduct);


        dbGestiPedi = new DbGestiPedi(getApplicationContext());

        productsModelList = dbGestiPedi.selectProductById(id);

        nombre.setText(productsModelList.get(0).getName());
        descripcion.setText(productsModelList.get(0).getDescripcion());
        stock.setText(Integer.toString(productsModelList.get(0).getStock()));
        precio.setText(Double.toString(productsModelList.get(0).getPrecio()));
        categoria.setText(productsModelList.get(0).getCategoria());
        imageProduct.setImageURI(Uri.parse(productsModelList.get(0).getImg()));

        String sharedPrefFile = "com.example.android.hellosharedprefs";
        SharedPreferences mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        String LOG_KEY = "log";
        login = mPreferences.getBoolean(LOG_KEY, login);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.initSession) {
            Intent intent;
            if(login){
                intent = new Intent(getApplicationContext(), LogOut.class);
                intent.putExtra(EXTRA_LOGED_IN, login);
            }else {
                intent = new Intent(this, InitSession.class);
            }
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
        dbGestiPedi.deleteClient(id);
        finish();
    }
}
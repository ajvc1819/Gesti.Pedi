package com.anjovaca.gestipedi.Stock;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.anjovaca.gestipedi.Category.CategoryActivity;
import com.anjovaca.gestipedi.DB.DbGestiPedi;
import com.anjovaca.gestipedi.DB.Models.CategoryModel;
import com.anjovaca.gestipedi.DB.Models.ProductsModel;
import com.anjovaca.gestipedi.LogIn.LogIn;
import com.anjovaca.gestipedi.LogIn.Profile;
import com.anjovaca.gestipedi.LogIn.RegisterAdministrator;
import com.anjovaca.gestipedi.Main.MainActivity;
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
    List<CategoryModel> categoryModelList;
    public Button btnEdit, btnDelete;
    public static final String EXTRA_LOGED_IN =
            "com.example.android.twoactivities.extra.login";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        dbGestiPedi = new DbGestiPedi(getApplicationContext());
        Intent intent = getIntent();
        id = intent.getIntExtra(StockActivity.EXTRA_PRODUCT_ID, 0);

        name = findViewById(R.id.tvmNombreProdD);
        description = findViewById(R.id.tvmDescProdD);
        stock = findViewById(R.id.tvmStockProdD);
        price = findViewById(R.id.tvmPrecioProdD);
        category = findViewById(R.id.tvmCategoriaProdD);
        imageProduct = findViewById(R.id.imgProduct);
        btnDelete = findViewById(R.id.btnDeleteProduct);
        btnEdit = findViewById((R.id.btnEditProd));

        productsModelList = dbGestiPedi.selectProductById(id);
        categoryModelList = dbGestiPedi.selectCategoryById(productsModelList.get(0).getCategory());

        getProductsData();
        getPreferences();

        if (!rol.equals("Administrador")) {
            btnDelete.setVisibility(View.INVISIBLE);
            btnEdit.setVisibility(View.INVISIBLE);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onStart() {
        super.onStart();

        dbGestiPedi = new DbGestiPedi(getApplicationContext());
        Intent intent = getIntent();
        id = intent.getIntExtra(StockActivity.EXTRA_PRODUCT_ID, 0);

        name = findViewById(R.id.tvmNombreProdD);
        description = findViewById(R.id.tvmDescProdD);
        stock = findViewById(R.id.tvmStockProdD);
        price = findViewById(R.id.tvmPrecioProdD);
        category = findViewById(R.id.tvmCategoriaProdD);
        imageProduct = findViewById(R.id.imgProduct);
        btnDelete = findViewById(R.id.btnDeleteProduct);
        btnEdit = findViewById((R.id.btnEditProd));

        productsModelList = dbGestiPedi.selectProductById(id);
        categoryModelList = dbGestiPedi.selectCategoryById(productsModelList.get(0).getCategory());

        getProductsData();
        getPreferences();

        if (!rol.equals("Administrador")) {
            btnDelete.setVisibility(View.INVISIBLE);
            btnEdit.setVisibility(View.INVISIBLE);
        }
    }

    //Funci??n que nos permite crear los diferentes elementos que aparecen en el men?? superior.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem shoppingCart = menu.findItem(R.id.ShoppingCart);
        MenuItem addAdmin = menu.findItem(R.id.Users);
        MenuItem categories = menu.findItem(R.id.Category);

        if (orderId == 0) {
            shoppingCart.setVisible(false);
        }

        if (rol == null || !rol.equals("Administrador")) {
            addAdmin.setVisible(false);
            categories.setVisible(false);
        }

        return true;
    }

    //Funci??n que nos permite asignar las funcionalides de los diferentes elementos que aparecen en el men?? superior.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.initSession) {
            Intent intent;
            if (login) {
                intent = new Intent(getApplicationContext(), Profile.class);
                intent.putExtra(EXTRA_LOGED_IN, login);
            } else {
                intent = new Intent(this, LogIn.class);
            }
            startActivity(intent);
        }

        if (id == R.id.ShoppingCart) {
            Intent intent = new Intent(getApplicationContext(), ShoppingCart.class);
            startActivity(intent);
        }

        if (id == R.id.Users) {
            Intent intent = new Intent(getApplicationContext(), RegisterAdministrator.class);
            startActivity(intent);
        }

        if (id == R.id.Category) {
            Intent intent = new Intent(getApplicationContext(), CategoryActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    //Funci??n que permite abrir la actividad EditProduct.
    public void editProduct(View view) {
        Intent intent = new Intent(getApplicationContext(), EditProduct.class);
        intent.putExtra(EXTRA_ID, id);
        startActivity(intent);
    }

    //Funci??n que permite eliminar productos de la base de datos.
    public void deleteProduct(View view) {
        dbGestiPedi.deleteProduct(id);
        finish();
    }

    //Funci??n que permite la obtenci??n de los datos almacenados en SharedPreferences.
    private void getPreferences() {
        String sharedPrefFile = "com.example.android.sharedprefs";
        SharedPreferences mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        String LOG_KEY = "log";
        login = mPreferences.getBoolean(LOG_KEY, login);
        String ORDER_ID_KEY = "id";
        orderId = mPreferences.getInt(ORDER_ID_KEY, orderId);
        String ROL_KEY = "rol";
        rol = mPreferences.getString(ROL_KEY, rol);
    }

    //Funci??n que se utiliza para obtener y mostrar los datos relativos a los productos.
    @SuppressLint("SetTextI18n")
    public void getProductsData() {
        SQLiteDatabase db = dbGestiPedi.getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT Products.nombre, Products.descripcion, Products.stock, Products.precio, Categories.nombre, Products.foto  FROM Products INNER JOIN Categories ON Products.idCategoria = Categories.id WHERE Products.id ='" + id + "'", null);

        if (cursor.moveToFirst()) {
            do {
                name.setText(cursor.getString(0));
                description.setText(cursor.getString(1));
                stock.setText(Integer.toString(cursor.getInt(2)));
                price.setText(cursor.getDouble(3) + "???");
                category.setText(cursor.getString(4));
                imageProduct.setImageURI(Uri.parse(cursor.getString(5)));
            } while ((cursor.moveToNext()));
        }

    }

    //Funci??n que permite regresar al men?? principal al pulsar sobre el logotipo de la empresa.
    public void returnMainMenu(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}
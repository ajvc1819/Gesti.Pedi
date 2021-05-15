package com.anjovaca.gestipedi.Category;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.anjovaca.gestipedi.Client.ClientActivity;
import com.anjovaca.gestipedi.DB.DbGestiPedi;
import com.anjovaca.gestipedi.DB.Models.CategoryModel;
import com.anjovaca.gestipedi.DB.Models.ClientModel;
import com.anjovaca.gestipedi.LogIn.LogIn;
import com.anjovaca.gestipedi.LogIn.LogOut;
import com.anjovaca.gestipedi.LogIn.RegisterAdministrator;
import com.anjovaca.gestipedi.Order.ShoppingCart;
import com.anjovaca.gestipedi.R;

import java.util.List;

public class CategoryDetail extends AppCompatActivity {
    public static final String EXTRA_ID =
            "com.example.android.twoactivities.extra.id";
    DbGestiPedi dbGestiPedi;
    int id;
    TextView dni, name, lastname, enterprise, cp, address, city, country, phone, email;
    public List<CategoryModel> categoryModelList;
    public String rol;
    public boolean login;
    Button btnEdit, btnDelete;
    public static final String EXTRA_LOGED_IN =
            "com.example.android.twoactivities.extra.login";

    String sharedPrefFile = "com.example.android.hellosharedprefs";
    SharedPreferences mPreferences;
    int orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_detail);
        Intent intent = getIntent();
        id = intent.getIntExtra(ClientActivity.EXTRA_ID, 0);

        name = findViewById(R.id.tvmName);

        dbGestiPedi = new DbGestiPedi(getApplicationContext());

        categoryModelList = dbGestiPedi.selectCategoryById(id);

        name.setText(categoryModelList.get(0).getName());

        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        String LOG_KEY = "log";
        login = mPreferences.getBoolean(LOG_KEY, login);
        String ROL_KEY = "rol";
        rol = mPreferences.getString(ROL_KEY, rol);
        String ORDER_ID_KEY = "id";
        orderId = mPreferences.getInt(ORDER_ID_KEY, orderId);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        id = intent.getIntExtra(ClientActivity.EXTRA_ID, 0);

        name = findViewById(R.id.tvmName);

        dbGestiPedi = new DbGestiPedi(getApplicationContext());

        categoryModelList = dbGestiPedi.selectCategoryById(id);

        name.setText(categoryModelList.get(0).getName());

        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        String LOG_KEY = "log";
        login = mPreferences.getBoolean(LOG_KEY, login);
        String ROL_KEY = "rol";
        rol = mPreferences.getString(ROL_KEY, rol);
        String ORDER_ID_KEY = "id";
        orderId = mPreferences.getInt(ORDER_ID_KEY, orderId);
    }

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
                intent = new Intent(getApplicationContext(), LogOut.class);
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

    public void deleteCategory(View view) {
        dbGestiPedi.deleteCategory(id);
        finish();
    }

    public void editCategory(View view) {
        Intent intent = new Intent(getApplicationContext(), EditCategory.class);
        intent.putExtra(EXTRA_ID, id);
        startActivity(intent);
    }
}
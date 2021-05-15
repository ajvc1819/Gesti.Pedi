package com.anjovaca.gestipedi.Main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;

import com.anjovaca.gestipedi.Category.CategoryActivity;
import com.anjovaca.gestipedi.Client.ClientActivity;
import com.anjovaca.gestipedi.DB.DbGestiPedi;
import com.anjovaca.gestipedi.LogIn.LogIn;
import com.anjovaca.gestipedi.LogIn.LogOut;
import com.anjovaca.gestipedi.LogIn.RegisterAdministrator;
import com.anjovaca.gestipedi.Order.OrderActivity;
import com.anjovaca.gestipedi.Order.ShoppingCart;
import com.anjovaca.gestipedi.R;
import com.anjovaca.gestipedi.Stock.StockActivity;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences mPreferences;
    private final String LOG_KEY = "log";
    int orderId;
    public String rol;
    SQLiteDatabase db;
    boolean login;
    public static final String EXTRA_LOGED_IN =
            "com.example.android.twoactivities.extra.login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        login = intent.getBooleanExtra(LogIn.EXTRA_LOGED_IN, false);

        DbGestiPedi dbHelper = new DbGestiPedi(this);
        db = dbHelper.getWritableDatabase();

        String sharedPrefFile = "com.example.android.hellosharedprefs";
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);

        login = mPreferences.getBoolean(LOG_KEY, login);
        String ORDER_ID_KEY = "id";
        orderId = mPreferences.getInt(ORDER_ID_KEY, orderId);
        String ROL_KEY = "rol";
        rol = mPreferences.getString(ROL_KEY, rol);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        login = intent.getBooleanExtra(LogIn.EXTRA_LOGED_IN, false);

        DbGestiPedi dbHelper = new DbGestiPedi(this);
        db = dbHelper.getWritableDatabase();

        String sharedPrefFile = "com.example.android.hellosharedprefs";
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);

        login = mPreferences.getBoolean(LOG_KEY, login);
        String ORDER_ID_KEY = "id";
        orderId = mPreferences.getInt(ORDER_ID_KEY, orderId);
        String ROL_KEY = "rol";
        rol = mPreferences.getString(ROL_KEY, rol);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putBoolean(LOG_KEY, login);
        preferencesEditor.apply();
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

    public void launchClientes(View view) {
        Intent intent;
        if (login) {
            intent = new Intent(this, ClientActivity.class);
        } else {
            intent = new Intent(this, LogIn.class);
        }
        startActivity(intent);
    }

    public void launchStock(View view) {
        Intent intent;
        if (login) {
            intent = new Intent(this, StockActivity.class);
        } else {
            intent = new Intent(this, LogIn.class);
        }
        startActivity(intent);
    }

    public void launchPedidos(View view) {
        Intent intent;
        if (login) {
            intent = new Intent(this, OrderActivity.class);
        } else {
            intent = new Intent(this, LogIn.class);
        }
        startActivity(intent);
    }
}
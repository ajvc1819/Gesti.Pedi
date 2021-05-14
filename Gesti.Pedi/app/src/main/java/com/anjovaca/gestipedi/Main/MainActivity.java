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

import com.anjovaca.gestipedi.Client.ClientActivity;
import com.anjovaca.gestipedi.DB.DbGestiPedi;
import com.anjovaca.gestipedi.LogIn.LogIn;
import com.anjovaca.gestipedi.LogIn.LogOut;
import com.anjovaca.gestipedi.Order.OrderActivity;
import com.anjovaca.gestipedi.Order.ShoppingCart;
import com.anjovaca.gestipedi.R;
import com.anjovaca.gestipedi.Stock.StockActivity;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences mPreferences;
    private final String LOG_KEY = "log";
    private final String ORDER_ID_KEY = "id";
    int orderId;
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
        orderId = mPreferences.getInt(ORDER_ID_KEY, orderId);
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
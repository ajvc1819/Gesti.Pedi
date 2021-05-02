package com.anjovaca.gestipedi.LogIn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.anjovaca.gestipedi.Main.MainActivity;
import com.anjovaca.gestipedi.R;

public class LogOut extends AppCompatActivity {
    boolean login;
    public static final String EXTRA_LOGED_IN =
            "com.example.android.twoactivities.extra.login";

    private SharedPreferences mPreferences;
    private String sharedPrefFile =
            "com.example.android.hellosharedprefs";
    private final String LOG_KEY = "log";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_out);
        Intent intent = getIntent();
        login = intent.getBooleanExtra(MainActivity.EXTRA_LOGED_IN, false);
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void logOut(View view) {
        login = false;

        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.clear();
        preferencesEditor.apply();

        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        intent.putExtra(EXTRA_LOGED_IN,login);
        startActivity(intent);
    }
}
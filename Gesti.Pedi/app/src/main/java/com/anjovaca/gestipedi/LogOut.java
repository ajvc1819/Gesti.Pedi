package com.anjovaca.gestipedi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class LogOut extends AppCompatActivity {
    boolean login;
    public static final String EXTRA_LOGED_IN =
            "com.example.android.twoactivities.extra.login";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_out);
        Intent intent = getIntent();
        login = intent.getBooleanExtra(MainActivity.EXTRA_LOGED_IN, false);

    }

    public void logOut(View view) {
        login = false;
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        intent.putExtra(EXTRA_LOGED_IN,login);
        startActivity(intent);
    }
}
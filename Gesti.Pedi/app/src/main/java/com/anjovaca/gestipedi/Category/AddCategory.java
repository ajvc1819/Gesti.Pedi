package com.anjovaca.gestipedi.Category;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.anjovaca.gestipedi.DB.DbGestiPedi;
import com.anjovaca.gestipedi.R;

public class AddCategory extends AppCompatActivity {
    DbGestiPedi dbGestiPedi;
    EditText name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        dbGestiPedi = new DbGestiPedi(getApplicationContext());

        name = findViewById(R.id.etNameCatA);
    }

    public void addCategory(View view) {
        String nameCat = name.getText().toString();
        dbGestiPedi.addCategory(nameCat);
        finish();
    }

    public void cancel(View view) { finish();
    }
}
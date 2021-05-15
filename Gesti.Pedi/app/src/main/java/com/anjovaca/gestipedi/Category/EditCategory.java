package com.anjovaca.gestipedi.Category;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.anjovaca.gestipedi.Client.ClientDetail;
import com.anjovaca.gestipedi.DB.DbGestiPedi;
import com.anjovaca.gestipedi.DB.Models.CategoryModel;
import com.anjovaca.gestipedi.DB.Models.ClientModel;
import com.anjovaca.gestipedi.R;

import java.util.List;

public class EditCategory extends AppCompatActivity {
    DbGestiPedi dbGestiPedi;
    int id;
    EditText name;
    public List<CategoryModel> categoryModelList;
    public boolean login;

    public static final String EXTRA_LOGED_IN =
            "com.example.android.twoactivities.extra.login";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_category);

        Intent intent = getIntent();
        id = intent.getIntExtra(CategoryDetail.EXTRA_ID, 0);

        name = findViewById(R.id.etNameCatE);

        dbGestiPedi = new DbGestiPedi(getApplicationContext());

        categoryModelList = dbGestiPedi.selectCategoryById(id);

        name.setText(categoryModelList.get(0).getName());

        String sharedPrefFile = "com.example.android.hellosharedprefs";
        SharedPreferences mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        String LOG_KEY = "log";
        login = mPreferences.getBoolean(LOG_KEY, login);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void cancel(View view) {finish();
    }

    public void editCategory(View view) {
        dbGestiPedi.updateCategory(id,name.getText().toString());
    }
}
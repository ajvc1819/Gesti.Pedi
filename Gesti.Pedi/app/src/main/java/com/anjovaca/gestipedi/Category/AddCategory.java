package com.anjovaca.gestipedi.Category;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.anjovaca.gestipedi.DB.DbGestiPedi;
import com.anjovaca.gestipedi.Main.MainActivity;
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

    //Función que nos permitirá añadir una nueva categoría a la base de datos.
    public void addCategory(View view) {
        String nameCat = name.getText().toString();
        if (!nameCat.isEmpty()) {
            dbGestiPedi.addCategory(nameCat);
        } else {
            Toast.makeText(getApplicationContext(),"Ha introducido un campo vacío.", Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    //Función que nos permite cancelar la acción y cerrar la activity.
    public void cancel(View view) {
        finish();
    }

    //Función que permite regresar al menú principal al pulsar sobre el logotipo de la empresa.
    public void returnMainMenu(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}
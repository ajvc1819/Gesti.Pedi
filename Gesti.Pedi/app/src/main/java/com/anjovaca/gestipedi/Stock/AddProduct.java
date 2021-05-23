package com.anjovaca.gestipedi.Stock;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.anjovaca.gestipedi.DB.DbGestiPedi;
import com.anjovaca.gestipedi.DB.Models.CategoryModel;
import com.anjovaca.gestipedi.Main.MainActivity;
import com.anjovaca.gestipedi.R;

import java.util.ArrayList;
import java.util.List;


public class AddProduct extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {
    private static final int PICK_IMAGE = 100;
    Uri imageUri;
    ImageView image;
    EditText name, description, stock, price;
    DbGestiPedi dbGestiPedi;
    int category;
    List<CategoryModel> categoryModelList;
    ArrayList<String> categoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        dbGestiPedi = new DbGestiPedi(getApplicationContext());
        categoryModelList = dbGestiPedi.getCategories();
        obtenerLista();
        Spinner spinner = findViewById(R.id.spnCategorias);
        if (spinner != null) {
            spinner.setOnItemSelectedListener(this);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, categoryList);
        adapter.setDropDownViewResource
                (R.layout.spinner_item);
        if (spinner != null) {
            spinner.setAdapter(adapter);
        }

        image = findViewById(R.id.imgImagenProdA);
        name = findViewById(R.id.etNombreProdA);
        description = findViewById(R.id.etDescripcionProdA);
        stock = findViewById(R.id.etStockProdA);
        price = findViewById(R.id.etPrecioProdA);
    }

    //Función que permite mostrar la imagen.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            image.setImageURI(imageUri);
        }
    }

    //Función que permite obtener la lista de categorias.
    public void obtenerLista() {
        categoryList = new ArrayList<>();

        for (CategoryModel category : categoryModelList) {
            categoryList.add(category.getName());
        }

    }

    //Función que permite la selección de imagenes de la galeria.
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void selectImage(View view) {
        Intent gallery = new Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    //Función que permite añadir nuevos productos a la base de datos.
    public void insertProduct(View view) {
        int stockInt = Integer.parseInt(stock.getText().toString());
        double priceDouble = Double.parseDouble(price.getText().toString());
        if (!imageUri.toString().isEmpty() && !name.getText().toString().isEmpty() && !description.getText().toString().isEmpty() && !stock.getText().toString().isEmpty() && !price.getText().toString().isEmpty()) {
            dbGestiPedi.insertProduct(name.getText().toString(), description.getText().toString(), stockInt, priceDouble, imageUri.toString(), category);
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Falta algún campo por rellenar o se ha introducido un campo erroneo.", Toast.LENGTH_SHORT).show();
        }

    }

    //Función que permite cancelar la acción y cerrar la actividad.
    public void cancel(View view) {
        finish();
    }

    //Función que permite obtener el valor del Spinner que se he seleccionado.
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        category = categoryModelList.get(position).getId();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    //Función que permite regresar al menú principal al pulsar sobre el logotipo de la empresa.
    public void returnMainMenu(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}
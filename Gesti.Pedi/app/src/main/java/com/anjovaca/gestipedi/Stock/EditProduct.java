package com.anjovaca.gestipedi.Stock;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.anjovaca.gestipedi.DB.DbGestiPedi;
import com.anjovaca.gestipedi.DB.Models.CategoryModel;
import com.anjovaca.gestipedi.DB.Models.ProductsModel;
import com.anjovaca.gestipedi.Main.MainActivity;
import com.anjovaca.gestipedi.R;

import java.util.ArrayList;
import java.util.List;

public class EditProduct extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {

    private static final int PICK_IMAGE = 100;
    Uri imageUri;
    ImageView image;
    EditText name, description, stock, price;
    DbGestiPedi dbGestiPedi;
    int category;
    int id;
    public List<ProductsModel> productsModelList;
    List<CategoryModel> categoryModelList;
    List<CategoryModel> categoryModelListId;
    ArrayList<String> categoryList;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        Intent intent = getIntent();
        id = intent.getIntExtra(ProductDetail.EXTRA_ID, 0);

        dbGestiPedi = new DbGestiPedi(getApplicationContext());

        productsModelList = dbGestiPedi.selectProductById(id);

        categoryModelList = dbGestiPedi.getCategories();
        obtenerLista();

        Spinner spinner = findViewById(R.id.spnCategoriasProdE);
        if (spinner != null) {
            spinner.setOnItemSelectedListener(this);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, categoryList);
        adapter.setDropDownViewResource
                (R.layout.spinner_item);
        if (spinner != null) {
            spinner.setAdapter(adapter);
        }
        categoryModelListId = dbGestiPedi.selectCategoryById(productsModelList.get(0).getCategory());

        assert spinner != null;
        selectValue(spinner, categoryModelListId.get(0).getName());

        image = findViewById(R.id.imgImageProdE);
        name = findViewById(R.id.etNameProdE);
        description = findViewById(R.id.etDescriptionProdE);
        stock = findViewById(R.id.etStockProdE);
        price = findViewById(R.id.etPriceProdE);

        name.setText(productsModelList.get(0).getName());
        description.setText(productsModelList.get(0).getDescription());
        stock.setText(Integer.toString(productsModelList.get(0).getStock()));
        price.setText(Double.toString(productsModelList.get(0).getPrice()));
        image.setImageURI(Uri.parse(productsModelList.get(0).getImage()));
    }

    //Función que permite la edición de los datos de un producto en la base de datos.
    public void editProduct(View view) {
        int stockInt;
        double priceDouble;
        stockInt = Integer.parseInt(stock.getText().toString());
        priceDouble = Double.parseDouble(price.getText().toString());
        if (imageUri == null) {
            if (!name.getText().toString().isEmpty() && !description.getText().toString().isEmpty() && !stock.getText().toString().isEmpty() && !price.getText().toString().isEmpty())
                dbGestiPedi.editProduct(id, name.getText().toString(), description.getText().toString(), stockInt, priceDouble, productsModelList.get(0).getImage(), category);
            else
                Toast.makeText(getApplicationContext(), "Falta algún campo por rellenar o se ha introducido un campo erroneo.", Toast.LENGTH_SHORT).show();
        } else {
            if (!name.getText().toString().isEmpty() && !description.getText().toString().isEmpty() && !stock.getText().toString().isEmpty() && !price.getText().toString().isEmpty())
                dbGestiPedi.editProduct(id, name.getText().toString(), description.getText().toString(), stockInt, priceDouble, imageUri.toString(), category);
            else
                Toast.makeText(getApplicationContext(), "Falta algún campo por rellenar o se ha introducido un campo erroneo.", Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    //Función que permite obtener la lista de categorias.
    public void obtenerLista() {
        categoryList = new ArrayList<>();

        for (CategoryModel category : categoryModelList) {
            categoryList.add(category.getName());
        }

    }

    //Función que permite cancelar la acción y cerrar la actividad.
    public void cancel(View view) {
        finish();
    }

    //Función que permite seleccionar una imagen de la galería.
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void selectImage(View view) {
        Intent gallery = new Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    // Función que permite establecer la imagen.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            image.setImageURI(imageUri);
        }
    }

    //Función que permite crear las funcionalidades de los botones que se muestran en el menú superior.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Función que permite la selección de categorias en el spinner.
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        category = categoryModelList.get(position).getId();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    //Función que permite mostrar la opción del spinner guaradada en la base de datos.
    private void selectValue(Spinner spinner, String value) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).equals(value)) {
                spinner.setSelection(i);
                break;
            }
        }
    }

    //Función que permite regresar al menú principal al pulsar sobre el logotipo de la empresa.
    public void returnMainMenu(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}
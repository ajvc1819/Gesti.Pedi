package com.anjovaca.gestipedi.Stock;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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

import com.anjovaca.gestipedi.DB.DbGestiPedi;
import com.anjovaca.gestipedi.DB.Models.ProductsModel;
import com.anjovaca.gestipedi.R;

import java.util.List;

public class EditProduct extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener{

    private static final int PICK_IMAGE = 100;
    Uri imageUri;
    ImageView image;
    EditText name, description, stock, price;
    DbGestiPedi dbGestiPedi;
    String category;
    int id;
    public List<ProductsModel> productsModelList;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        Intent intent = getIntent();
        id = intent.getIntExtra(ProductDetail.EXTRA_ID, 0);

        dbGestiPedi = new DbGestiPedi(getApplicationContext());

        productsModelList = dbGestiPedi.selectProductById(id);

        Spinner spinner = findViewById(R.id.spnCategoriasProdE);
        if (spinner != null) {
            spinner.setOnItemSelectedListener(this);
        }
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.labels_array, R.layout.spinner_item);
        adapter.setDropDownViewResource
                (R.layout.spinner_item);
        if (spinner != null) {
            spinner.setAdapter(adapter);
        }

        selectValue(spinner, productsModelList.get(0).getCategory());

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

    public void editProduct(View view) {
        int stockInt;
        double priceDouble;
        stockInt = Integer.parseInt(stock.getText().toString());
        priceDouble = Double.parseDouble(price.getText().toString());
        if(imageUri == null){
            dbGestiPedi.editProduct(id, name.getText().toString(), description.getText().toString(), stockInt,priceDouble,productsModelList.get(0).getImage(), category);
        } else {
            dbGestiPedi.editProduct(id, name.getText().toString(), description.getText().toString(), stockInt,priceDouble,imageUri.toString(), category);
        }
        finish();
    }

    public void cancel(View view) {
        finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void selectImage(View view) {
        Intent gallery = new Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            image.setImageURI(imageUri);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        category = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void selectValue(Spinner spinner, String value) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).equals(value)) {
                spinner.setSelection(i);
                break;
            }
        }
    }
}
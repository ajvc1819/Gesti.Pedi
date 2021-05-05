package com.anjovaca.gestipedi.Stock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.anjovaca.gestipedi.DB.DbGestiPedi;
import com.anjovaca.gestipedi.R;

import java.io.FileDescriptor;
import java.io.IOException;

import static android.content.Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION;
import static android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION;

public class AddProduct extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener{
    private static final int PICK_IMAGE = 100;
    Uri imageUri;
    ImageView foto_gallery;
    EditText nombre, descripcion, stock, precio;
    DbGestiPedi dbGestiPedi;
    String category;
    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        Spinner spinner = findViewById(R.id.spnCategorias);
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

        foto_gallery = findViewById(R.id.imgImagenProdA);
        nombre = findViewById(R.id.etNombreProdA);
        descripcion = findViewById(R.id.etDescripcionProdA);
        stock = findViewById(R.id.etStockProdA);
        precio = findViewById(R.id.etPrecioProdA);

        dbGestiPedi = new DbGestiPedi(getApplicationContext());

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            foto_gallery.setImageURI(imageUri);

        }
    }


    public void selectImage(View view) {
        Intent gallery = new Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }


    public void insertProduct(View view) throws IOException {
        int stockInt = Integer.parseInt(stock.getText().toString());
        double priceDouble = Double.parseDouble(precio.getText().toString());

        dbGestiPedi.insertProduct(nombre.getText().toString(), descripcion.getText().toString(), stockInt,priceDouble,imageUri.toString(), category);
        finish();
    }

    public void cancel(View view) {
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String spinnerLabel = parent.getItemAtPosition(position).toString();
        category = spinnerLabel;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
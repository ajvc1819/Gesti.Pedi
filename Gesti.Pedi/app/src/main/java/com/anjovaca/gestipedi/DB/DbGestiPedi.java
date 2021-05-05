package com.anjovaca.gestipedi.DB;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.icu.text.BidiRun;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.Nullable;

import com.anjovaca.gestipedi.DB.Models.ClienteModelo;
import com.anjovaca.gestipedi.DB.Models.ProductsModel;
import com.anjovaca.gestipedi.DB.Models.UserModel;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DbGestiPedi extends SQLiteOpenHelper {

    private static final String USERS_TABLE_CREATE = "CREATE TABLE Users(id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT NOT NULL, apellidos TEXT NOT NULL, usuario TEXT NOT NULL, contraseña TEXT NOT NULL, rol TEXT NOT NULL)";
    private static final String PRODUCTS_TABLE_CREATE = "CREATE TABLE Products(id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT NOT NULL, Categoria TEXT NOT NULL, descripcion TEXT NOT NULL, stock INTEGER NOT NULL, precio DOUBLE NOT NULL,cantidadVendida INTEGER NOT NULL, foto BLOB NOT NULL)";
    private static final String CLIENTS_TABLE_CREATE = "CREATE TABLE Clients(id INTEGER PRIMARY KEY AUTOINCREMENT, dni TEXT NOT NULL, nombre TEXT NOT NULL, apellidos TEXT NOT NULL,empresa TEXT NOT NULL, direccion TEXT NOT NULL, cp TEXT NOT NULL, ciudad TEXT NOT NULL, pais TEXT NOT NULL, telefono TEXT NOT NULL, correo TEXT NOT NULL)";
    private static final String ORDERDETAIL_TABLE_CREATE = "CREATE TABLE OrderDetails(id INTEGER PRIMARY KEY AUTOINCREMENT, cantidad INTEGER NOT NULL, precio DOUBLE NOT NULL, idPedido INTEGER NOT NULL, idProducto INTEGER NOT NULL, FOREIGN KEY (idPedido) REFERENCES Orders(id), FOREIGN KEY (idProducto) REFERENCES Products(id))";
    private static final String ORDER_TABLE_CREATE = "CREATE TABLE Orders(id INTEGER PRIMARY KEY AUTOINCREMENT, fecha TEXT NOT NULL, idCliente INTEGER NOT NULL, estado TEXT NOT NULL, total DOUBLE NOT NULL, idUsuario INTEGER NOT NULL, FOREIGN KEY (idCliente) REFERENCES Clients(id), FOREIGN KEY (idUsuario) REFERENCES Users(id))";
    private static final String DB_NAME = "Gesti.Pedi.Dat";
    private static final int DB_VERSION = 1;

    public DbGestiPedi(@Nullable Context context) {
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(USERS_TABLE_CREATE);
        db.execSQL(PRODUCTS_TABLE_CREATE);
        db.execSQL(CLIENTS_TABLE_CREATE);
        db.execSQL(ORDERDETAIL_TABLE_CREATE);
        db.execSQL(ORDER_TABLE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            String dropTablaProductos = "DROP TABLE IF EXISTS PRODUCTS_TABLE_CREATE";
            String dropTablaClientes = "DROP TABLE IF EXISTS CLIENTS_TABLE_CREATE";
            String dropTablaDetallePedido = "DROP TABLE IF EXISTS ORDERDETAIL_TABLE_CREATE";
            String dropTablaPedido = "DROP TABLE IF EXISTS ORDER_TABLE_CREATE";

            db.execSQL(dropTablaProductos);
            db.execSQL(dropTablaClientes);
            db.execSQL(dropTablaDetallePedido);
            db.execSQL(dropTablaPedido);

            onCreate(db);
        } catch (SQLiteException e) {
            Log.e("$TAG (onUpgrade)", e.toString());
        }
    }

    public List<ClienteModelo> mostrarClientePorId(int idCliente){
        SQLiteDatabase db = getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM Clients WHERE id = '" + idCliente+ "' ", null);
        List<ClienteModelo> clientes = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                clientes.add(new ClienteModelo(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getString(7),cursor.getString(8),cursor.getString(9), cursor.getString(10)));
            }while ((cursor.moveToNext()));
        }
        return clientes;
    }

    public List<ClienteModelo> mostrarClientes(){
        SQLiteDatabase db = getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM Clients", null);
        List<ClienteModelo> clientes = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                clientes.add(new ClienteModelo(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getString(7),cursor.getString(8),cursor.getString(9),cursor.getString(10)));
            }while ((cursor.moveToNext()));
        }
        return clientes;
    }

    public List<ClienteModelo> checkClient(String dni, String telf, String correo){
        SQLiteDatabase db = getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM Clients WHERE Dni = '" + dni + "' OR telefono = '"+ telf + "' OR correo = '" + correo + "'", null);
        List<ClienteModelo> clientes = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                clientes.add(new ClienteModelo(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getString(7),cursor.getString(8),cursor.getString(9),cursor.getString(10)));
            }while ((cursor.moveToNext()));
        }
        return clientes;
    }

    public void agregarCliente(String dni, String nombre, String apellidos, String empresa, String direccion, String cp, String ciudad, String pais, String telefono, String correo){
        SQLiteDatabase db = getWritableDatabase();
        if(db!=null){
            try{
                db.execSQL("INSERT INTO Clients (dni,nombre,apellidos,empresa,direccion,cp,ciudad,pais,telefono,correo) VALUES ('" + dni + "', '" + nombre + "','" + apellidos + "','" + empresa + "','" + direccion + "','" + cp + "','" + ciudad +"','" + pais + "','" + telefono +"','" + correo +"')");
                db.close();
            } catch (Exception ex){
                Log.d("Tag", ex.toString());
            }

        }
    }

    public void editClient(int id, String dni, String nombre, String apellidos, String empresa, String direccion, String cp, String ciudad, String pais, String telefono, String correo){
        SQLiteDatabase db = getWritableDatabase();
        if(db!=null){
            try{
                db.execSQL("UPDATE Clients SET dni = '" + dni + "', nombre = '" + nombre + "', apellidos = '" + apellidos + "', empresa = '" + empresa + "', direccion = '" + direccion + "', cp = '" + cp + "', ciudad = '" + ciudad +"', pais = '" + pais + "', telefono = '" + telefono +"', correo = '" + correo +"' WHERE id = '" + id + "'");
                db.close();
            } catch (Exception ex){
                Log.d("Tag", ex.toString());
            }

        }
    }

    public void deleteClient(int idClient){
        SQLiteDatabase db = getWritableDatabase();
        if(db!=null){
            try{
                db.execSQL("DELETE FROM Products");
                db.close();
            } catch (Exception ex){
                Log.d("Tag", ex.toString());
            }

        }
    }

    public void insertUser(String nombre, String apellidos, String usuario, String pass, String rol){
        SQLiteDatabase db = getWritableDatabase();
        if(db!=null){
            try{
                db.execSQL("INSERT INTO Users (nombre,apellidos,usuario,contraseña,rol) VALUES ('" + nombre + "','" + apellidos + "','" + usuario + "','" + pass + "','" + rol + "')");
                db.close();
            } catch (Exception ex){
                Log.d("Tag", ex.toString());
            }

        }
    }

    public List<UserModel> initSession(String usuario, String password){
        SQLiteDatabase db = getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM Users WHERE usuario = '" + usuario + "' AND contraseña = '" + password + "'", null);
        List<UserModel> users = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                users.add(new UserModel(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5)));
            }while ((cursor.moveToNext()));
        }
        return users;
    }

    public List<UserModel> checkUsers(String usuario){
        SQLiteDatabase db = getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM Users WHERE usuario = '" + usuario + "'", null);
        List<UserModel> users = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                users.add(new UserModel(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5)));
            }while ((cursor.moveToNext()));
        }
        return users;
    }

    public void insertProduct(String nombre, String descripcion, int stock, double precio, String image, String categoria){
        SQLiteDatabase db = getWritableDatabase();
        int cantidadVendida = 0;
        if(db!=null){

                db.execSQL("INSERT INTO Products (nombre,Categoria,descripcion,stock,precio,cantidadVendida,foto) VALUES ('" + nombre + "', '" + categoria + "','" + descripcion + "','" + stock + "','" + precio + "','"  + cantidadVendida + "','" + image + "')");
                db.close();


        }
    }

    public List<ProductsModel> mostrarProducts(){
        SQLiteDatabase db = getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM Products", null);
        List<ProductsModel> products = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                products.add(new ProductsModel(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getInt(4),cursor.getDouble(5),cursor.getInt(6),cursor.getString(7)));
            }while ((cursor.moveToNext()));
        }
        return products;
    }
}
package com.anjovaca.gestipedi.DB;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.anjovaca.gestipedi.DB.Models.ClientModel;
import com.anjovaca.gestipedi.DB.Models.ProductsModel;
import com.anjovaca.gestipedi.DB.Models.UserModel;

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
            String dropTableProducts = "DROP TABLE IF EXISTS PRODUCTS_TABLE_CREATE";
            String dropTableClients = "DROP TABLE IF EXISTS CLIENTS_TABLE_CREATE";
            String dropTableOrderDetail = "DROP TABLE IF EXISTS ORDERDETAIL_TABLE_CREATE";
            String dropTablaOrder = "DROP TABLE IF EXISTS ORDER_TABLE_CREATE";

            db.execSQL(dropTableProducts);
            db.execSQL(dropTableClients);
            db.execSQL(dropTableOrderDetail);
            db.execSQL(dropTablaOrder);

            onCreate(db);
        } catch (SQLiteException e) {
            Log.e("$TAG (onUpgrade)", e.toString());
        }
    }

    public List<ClientModel> getClientsById(int idClient){
        SQLiteDatabase db = getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM Clients WHERE id = '" + idClient+ "' ", null);
        List<ClientModel> clients = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                clients.add(new ClientModel(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getString(7),cursor.getString(8),cursor.getString(9), cursor.getString(10)));
            }while ((cursor.moveToNext()));
        }
        return clients;
    }

    public List<ClientModel> showClients(){
        SQLiteDatabase db = getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM Clients", null);
        List<ClientModel> clients = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                clients.add(new ClientModel(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getString(7),cursor.getString(8),cursor.getString(9),cursor.getString(10)));
            }while ((cursor.moveToNext()));
        }
        return clients;
    }

    public List<ClientModel> checkClient(String dni, String phone, String email){
        SQLiteDatabase db = getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM Clients WHERE Dni = '" + dni + "' OR telefono = '"+ phone + "' OR correo = '" + email + "'", null);
        List<ClientModel> clients = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                clients.add(new ClientModel(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getString(7),cursor.getString(8),cursor.getString(9),cursor.getString(10)));
            }while ((cursor.moveToNext()));
        }
        return clients;
    }

    public void insertClient(String dni, String name, String lastname, String enterprise, String address, String cp, String city, String country, String phone, String email){
        SQLiteDatabase db = getWritableDatabase();
        if(db != null){
            try{
                db.execSQL("INSERT INTO Clients (dni,nombre,apellidos,empresa,direccion,cp,ciudad,pais,telefono,correo) VALUES ('" + dni + "', '" + name + "','" + lastname + "','" + enterprise + "','" + address + "','" + cp + "','" + city +"','" + country + "','" + phone +"','" + email +"')");
                db.close();
            } catch (Exception ex){
                Log.d("Tag", ex.toString());
            }

        }
    }

    public void editClient(int id, String dni, String name, String lastname, String enterprise, String address, String cp, String city, String country, String phone, String email){
        SQLiteDatabase db = getWritableDatabase();
        if(db!=null){
            try{
                db.execSQL("UPDATE Clients SET dni = '" + dni + "', nombre = '" + name + "', apellidos = '" + lastname + "', empresa = '" + enterprise + "', direccion = '" + address + "', cp = '" + cp + "', ciudad = '" + city +"', pais = '" + country + "', telefono = '" + phone +"', correo = '" + email +"' WHERE id = '" + id + "'");
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
                db.execSQL("DELETE FROM Clients WHERE id = '"+ idClient + "'");
                db.close();
            } catch (Exception ex){
                Log.d("Tag", ex.toString());
            }

        }
    }

    public void insertUser(String name, String lastname, String username, String password, String rol){
        SQLiteDatabase db = getWritableDatabase();
        if(db!=null){
            try{
                db.execSQL("INSERT INTO Users (nombre,apellidos,usuario,contraseña,rol) VALUES ('" + name + "','" + lastname + "','" + username + "','" + password + "','" + rol + "')");
                db.close();
            } catch (Exception ex){
                Log.d("Tag", ex.toString());
            }

        }
    }

    public List<UserModel> initSession(String username, String password){
        SQLiteDatabase db = getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM Users WHERE usuario = '" + username + "' AND contraseña = '" + password + "'", null);
        List<UserModel> users = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                users.add(new UserModel(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5)));
            }while ((cursor.moveToNext()));
        }
        return users;
    }

    public List<UserModel> checkUsers(String username){
        SQLiteDatabase db = getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM Users WHERE usuario = '" + username + "'", null);
        List<UserModel> users = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                users.add(new UserModel(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5)));
            }while ((cursor.moveToNext()));
        }
        return users;
    }

    public void insertProduct(String name, String description, int stock, double price, String image, String category){
        SQLiteDatabase db = getWritableDatabase();
        int sold = 0;
        if(db!=null){

                db.execSQL("INSERT INTO Products (nombre,Categoria,descripcion,stock,precio,cantidadVendida,foto) VALUES ('" + name + "', '" + category + "','" + description + "','" + stock + "','" + price + "','"  + sold + "','" + image + "')");
                db.close();


        }
    }

    public List<ProductsModel> showProducts(){
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

    public List<ProductsModel> selectProductById(int idProduct){
        SQLiteDatabase db = getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM Products WHERE id = '" + idProduct+ "' ", null);
        List<ProductsModel> products = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                products.add(new ProductsModel(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getInt(4),cursor.getDouble(5),cursor.getInt(6),cursor.getString(7)));
            }while ((cursor.moveToNext()));
        }
        return products;
    }

    public void editProduct(int id, String name, String description, int stock, double price, String image, String category){
        SQLiteDatabase db = getWritableDatabase();
        if(db!=null){
            try{
                db.execSQL("UPDATE Products SET nombre = '" + name + "', descripcion = '" + description + "', stock = '" + stock + "', precio = '" + price + "', foto = '" + image + "', categoria = '" + category + "' WHERE id = '" + id + "'");
                db.close();
            } catch (Exception ex){
                Log.d("Tag", ex.toString());
            }

        }
    }

    public void deleteProduct(int idProduct){
        SQLiteDatabase db = getWritableDatabase();
        if(db!=null){
            try{
                db.execSQL("DELETE FROM Products WHERE id = '"+ idProduct + "'");
                db.close();
            } catch (Exception ex){
                Log.d("Tag", ex.toString());
            }

        }
    }
}

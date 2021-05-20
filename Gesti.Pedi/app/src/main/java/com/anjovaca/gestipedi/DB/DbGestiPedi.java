package com.anjovaca.gestipedi.DB;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;

import com.anjovaca.gestipedi.DB.Models.CategoryModel;
import com.anjovaca.gestipedi.DB.Models.ClientModel;
import com.anjovaca.gestipedi.DB.Models.ProductsModel;
import com.anjovaca.gestipedi.DB.Models.UserModel;
import com.anjovaca.gestipedi.DB.Models.OrderDetailModel;
import com.anjovaca.gestipedi.DB.Models.OrderModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DbGestiPedi extends SQLiteOpenHelper {

    private static final String USERS_TABLE_CREATE = "CREATE TABLE Users(id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT NOT NULL, apellidos TEXT NOT NULL, usuario TEXT NOT NULL, contraseña TEXT NOT NULL, rol TEXT NOT NULL)";
    private static final String PRODUCTS_TABLE_CREATE = "CREATE TABLE Products(id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT NOT NULL,idCategoria INTEGER NOT NULL, descripcion TEXT NOT NULL, stock INTEGER NOT NULL, precio DOUBLE NOT NULL,cantidadVendida INTEGER NOT NULL, foto BLOB NOT NULL, FOREIGN KEY (idCategoria) REFERENCES Categories(id))";
    private static final String CLIENTS_TABLE_CREATE = "CREATE TABLE Clients(id INTEGER PRIMARY KEY AUTOINCREMENT, dni TEXT NOT NULL, nombre TEXT NOT NULL, apellidos TEXT NOT NULL,empresa TEXT NOT NULL, direccion TEXT NOT NULL, cp TEXT NOT NULL, ciudad TEXT NOT NULL, pais TEXT NOT NULL, telefono TEXT NOT NULL, correo TEXT NOT NULL)";
    private static final String ORDERDETAIL_TABLE_CREATE = "CREATE TABLE OrderDetails(id INTEGER PRIMARY KEY AUTOINCREMENT, cantidad INTEGER NOT NULL, precio DOUBLE NOT NULL, idPedido INTEGER NOT NULL, idProducto INTEGER NOT NULL, FOREIGN KEY (idPedido) REFERENCES Orders(id), FOREIGN KEY (idProducto) REFERENCES Products(id))";
    private static final String ORDER_TABLE_CREATE = "CREATE TABLE Orders(id INTEGER PRIMARY KEY AUTOINCREMENT, fecha TEXT NOT NULL, idCliente INTEGER NOT NULL, estado TEXT NOT NULL, total DOUBLE NOT NULL, idUsuario INTEGER NOT NULL, FOREIGN KEY (idCliente) REFERENCES Clients(id), FOREIGN KEY (idUsuario) REFERENCES Users(id))";
    private static final String CATEGORY_TABLE_CREATE = "CREATE TABLE Categories(id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT NOT NULL)";
    private static final String DB_NAME = "Gesti.Pedi.DataB";
    private static final int DB_VERSION = 1;

    public DbGestiPedi(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(USERS_TABLE_CREATE);
        db.execSQL(PRODUCTS_TABLE_CREATE);
        db.execSQL(CLIENTS_TABLE_CREATE);
        db.execSQL(ORDERDETAIL_TABLE_CREATE);
        db.execSQL(ORDER_TABLE_CREATE);
        db.execSQL(CATEGORY_TABLE_CREATE);
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

    public List<ClientModel> getClientsById(int idClient) {
        SQLiteDatabase db = getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM Clients WHERE id = '" + idClient + "' ", null);
        List<ClientModel> clients = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                clients.add(new ClientModel(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10)));
            } while ((cursor.moveToNext()));
        }
        return clients;
    }

    public List<ClientModel> showClients() {
        SQLiteDatabase db = getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM Clients", null);
        List<ClientModel> clients = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                clients.add(new ClientModel(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10)));
            } while ((cursor.moveToNext()));
        }
        return clients;
    }

    public List<ClientModel> checkClient(String dni, String phone, String email) {
        SQLiteDatabase db = getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM Clients WHERE Dni = '" + dni + "' OR telefono = '" + phone + "' OR correo = '" + email + "'", null);
        List<ClientModel> clients = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                clients.add(new ClientModel(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10)));
            } while ((cursor.moveToNext()));
        }
        return clients;
    }

    public void insertClient(String dni, String name, String lastname, String enterprise, String address, String cp, String city, String country, String phone, String email) {
        SQLiteDatabase db = getWritableDatabase();

        if (db != null) {
            try {
                db.execSQL("INSERT INTO Clients (dni,nombre,apellidos,empresa,direccion,cp,ciudad,pais,telefono,correo) VALUES ('" + dni + "', '" + name + "','" + lastname + "','" + enterprise + "','" + address + "','" + cp + "','" + city + "','" + country + "','" + phone + "','" + email + "')");
                db.close();
            } catch (Exception ex) {
                Log.d("Tag", ex.toString());
            }
        }
    }

    public void editClient(int id, String dni, String name, String lastname, String enterprise, String address, String cp, String city, String country, String phone, String email) {
        SQLiteDatabase db = getWritableDatabase();

        if (db != null) {
            try {
                db.execSQL("UPDATE Clients SET dni = '" + dni + "', nombre = '" + name + "', apellidos = '" + lastname + "', empresa = '" + enterprise + "', direccion = '" + address + "', cp = '" + cp + "', ciudad = '" + city + "', pais = '" + country + "', telefono = '" + phone + "', correo = '" + email + "' WHERE id = '" + id + "'");
                db.close();
            } catch (Exception ex) {
                Log.d("Tag", ex.toString());
            }
        }
    }

    public void deleteClient(int idClient) {
        SQLiteDatabase db = getWritableDatabase();
        if (db != null) {
            try {
                db.execSQL("DELETE FROM Clients WHERE id = '" + idClient + "'");
                db.close();
            } catch (Exception ex) {
                Log.d("Tag", ex.toString());
            }
        }
    }

    public void insertUser(String name, String lastname, String username, String password, String rol) {
        SQLiteDatabase db = getWritableDatabase();

        if (db != null) {
            try {
                db.execSQL("INSERT INTO Users (nombre,apellidos,usuario,contraseña,rol) VALUES ('" + name + "','" + lastname + "','" + username + "','" + password + "','" + rol + "')");
                db.close();
            } catch (Exception ex) {
                Log.d("Tag", ex.toString());
            }
        }
    }

    public List<UserModel> initSession(String username, String password) {
        SQLiteDatabase db = getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM Users WHERE usuario = '" + username + "' AND contraseña = '" + password + "'", null);
        List<UserModel> users = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                users.add(new UserModel(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5)));
            } while ((cursor.moveToNext()));
        }
        return users;
    }

    public List<UserModel> checkUsers(String username) {
        SQLiteDatabase db = getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM Users WHERE usuario = '" + username + "'", null);
        List<UserModel> users = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                users.add(new UserModel(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5)));
            } while ((cursor.moveToNext()));
        }
        return users;
    }

    public List<UserModel> getUserById(int id) {
        SQLiteDatabase db = getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM Users WHERE id = '" + id + "'", null);
        List<UserModel> users = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                users.add(new UserModel(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5)));
            } while ((cursor.moveToNext()));
        }
        return users;
    }

    public void insertProduct(String name, String description, int stock, double price, String image, int category) {
        SQLiteDatabase db = getWritableDatabase();
        int sold = 0;

        if (db != null) {
            db.execSQL("INSERT INTO Products (nombre,idCategoria,descripcion,stock,precio,cantidadVendida,foto) VALUES ('" + name + "', '" + category + "','" + description + "','" + stock + "','" + price + "','" + sold + "','" + image + "')");
            db.close();
        }
    }

    public List<ProductsModel> showProducts() {
        SQLiteDatabase db = getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM Products", null);
        List<ProductsModel> products = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                products.add(new ProductsModel(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getString(3), cursor.getInt(4), cursor.getDouble(5), cursor.getInt(6), cursor.getString(7)));
            } while ((cursor.moveToNext()));
        }
        return products;
    }

    public List<ProductsModel> selectProductById(int idProduct) {
        SQLiteDatabase db = getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM Products WHERE id = '" + idProduct + "' ", null);
        List<ProductsModel> products = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                products.add(new ProductsModel(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getString(3), cursor.getInt(4), cursor.getDouble(5), cursor.getInt(6), cursor.getString(7)));
            } while ((cursor.moveToNext()));
        }
        return products;
    }

    public void editProduct(int id, String name, String description, int stock, double price, String image, int category) {
        SQLiteDatabase db = getWritableDatabase();

        if (db != null) {
            try {
                db.execSQL("UPDATE Products SET nombre = '" + name + "', descripcion = '" + description + "', stock = '" + stock + "', precio = '" + price + "', foto = '" + image + "', idCategoria = '" + category + "' WHERE id = '" + id + "'");
                db.close();
            } catch (Exception ex) {
                Log.d("Tag", ex.toString());
            }
        }
    }

    public void deleteProduct(int idProduct) {
        SQLiteDatabase db = getWritableDatabase();
        if (db != null) {
            try {
                db.execSQL("DELETE FROM Products WHERE id = '" + idProduct + "'");
                db.close();
            } catch (Exception ex) {
                Log.d("Tag", ex.toString());
            }
        }
    }

    public List<OrderModel> showOrders() {
        SQLiteDatabase db = getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM Orders", null);
        List<OrderModel> orders = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                orders.add(new OrderModel(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getString(3), cursor.getDouble(4), cursor.getInt(5)));
            } while ((cursor.moveToNext()));
        }
        return orders;
    }

    public List<OrderModel> getOrderById(int id) {
        SQLiteDatabase db = getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM Orders WHERE id = '" + id + "'", null);
        List<OrderModel> orders = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                orders.add(new OrderModel(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getString(3), cursor.getDouble(4), cursor.getInt(5)));
            } while ((cursor.moveToNext()));
        }
        return orders;
    }

    public void insertOrder(int idClient, int idUser) {
        SQLiteDatabase db = getWritableDatabase();
        double total = 0;
        String state = "En Proceso";
        Date currentTime = Calendar.getInstance().getTime();
        String date = currentTime.toString();

        if (db != null) {

            db.execSQL("INSERT INTO Orders (fecha,idCliente,estado,total,idUsuario) VALUES ('" + date + "', '" + idClient + "','" + state + "','" + total + "','" + idUser + "')");
            db.close();


        }
    }

    public void confirmOrder(int id) {
        SQLiteDatabase db = getWritableDatabase();
        String state = "Confirmado";

        if (db != null) {
            try {
                db.execSQL("UPDATE Orders SET estado = '" + state + "' WHERE id = '" + id + "'");
                db.close();
            } catch (Exception ex) {
                Log.d("Tag", ex.toString());
            }

        }
    }

    public void sendOrder(int id) {
        SQLiteDatabase db = getWritableDatabase();
        String state = "Enviado";

        if (db != null) {
            try {
                db.execSQL("UPDATE Orders SET estado = '" + state + "' WHERE id = '" + id + "'");
                db.close();
            } catch (Exception ex) {
                Log.d("Tag", ex.toString());
            }

        }
    }

    public void cancelOrder(int id) {
        SQLiteDatabase db = getWritableDatabase();
        String state = "Cancelado";

        if (db != null) {
            try {
                db.execSQL("UPDATE Orders SET estado = '" + state + "' WHERE id = '" + id + "'");
                db.close();
            } catch (Exception ex) {
                Log.d("Tag", ex.toString());
            }

        }
    }

    public void deleteOrder(int id) {
        SQLiteDatabase db = getWritableDatabase();

        if (db != null) {
            try {
                db.execSQL("DELETE FROM Orders WHERE id = '" + id + "'");
                db.close();
            } catch (Exception ex) {
                Log.d("Tag", ex.toString());
            }

        }
    }

    public void updateTotalPrice(int id, double totalPrice) {
        SQLiteDatabase db = getWritableDatabase();

        if (db != null) {
            try {
                db.execSQL("UPDATE Orders SET total = '" + totalPrice + "' WHERE id = '" + id + "'");
                db.close();
            } catch (Exception ex) {
                Log.d("Tag", ex.toString());
            }

        }
    }

    public List<OrderDetailModel> showOrderDetail(int idOrder) {
        SQLiteDatabase db = getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM OrderDetails WHERE idPedido = '" + idOrder + "' ", null);
        List<OrderDetailModel> details = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                details.add(new OrderDetailModel(cursor.getInt(0), cursor.getInt(1), cursor.getDouble(2), cursor.getInt(3), cursor.getInt(4)));
            } while ((cursor.moveToNext()));
        }
        return details;
    }

    public List<OrderDetailModel> getOrderDetailById(int id) {
        SQLiteDatabase db = getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM OrderDetails WHERE id = '" + id + "' ", null);
        List<OrderDetailModel> details = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                details.add(new OrderDetailModel(cursor.getInt(0), cursor.getInt(1), cursor.getDouble(2), cursor.getInt(3), cursor.getInt(4)));
            } while ((cursor.moveToNext()));
        }
        return details;
    }

    public void insertOrderDetail(double price, int idOrder, int idProduct) {
        SQLiteDatabase db = getWritableDatabase();
        int quantity = 1;

        if (db != null) {
            db.execSQL("INSERT INTO OrderDetails (cantidad, precio, idPedido, idProducto) VALUES ('" + quantity + "', '" + price + "','" + idOrder + "','" + idProduct + "')");
            db.close();
        }

        updateTotalPrice(idOrder, price);
    }

    public void deleteOrderDetail(int id, int quantity, double priceDetail, double priceProduct, int idOrder) {
        SQLiteDatabase db = getWritableDatabase();
        priceDetail = priceDetail - (priceProduct * quantity);
        if (db != null) {
            db.execSQL("DELETE FROM OrderDetails WHERE id = '" + id + "'");
            db.close();
        }

        updateTotalPrice(idOrder, priceDetail);
    }
    public List<OrderModel> selectLastOrder() {
        SQLiteDatabase db = getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM Orders ORDER by id DESC LIMIT 1", null);
        List<OrderModel> orders = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                orders.add(new OrderModel(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getString(3), cursor.getDouble(4), cursor.getInt(5)));
            } while ((cursor.moveToNext()));
        }
        return orders;
    }

    public List<OrderDetailModel> checkOrderDetail(int idProduct, int idOrder) {
        SQLiteDatabase db = getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM OrderDetails WHERE idPedido = '" + idOrder + "' AND idProducto = '" + idProduct + "'", null);
        List<OrderDetailModel> details = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                details.add(new OrderDetailModel(cursor.getInt(0), cursor.getInt(1), cursor.getDouble(2), cursor.getInt(3), cursor.getInt(4)));
            } while ((cursor.moveToNext()));
        }
        return details;
    }

    public void increaseQuantity(int id,int stock, int quantity, double priceDetail, double priceProduct, int idOrder) {
        SQLiteDatabase db = getWritableDatabase();
        if(stock != quantity){
            quantity = quantity + 1;
            priceDetail = priceDetail + priceProduct;
            if (db != null) {
                try {
                    db.execSQL("UPDATE OrderDetails SET cantidad = '" + quantity + "', precio = '" + priceDetail + "' WHERE id = '" + id + "'");
                    db.close();
                } catch (Exception ex) {
                    Log.d("Tag", ex.toString());
                }
            }
        }

        updateTotalPrice(idOrder, priceDetail);

    }

    public void decreaseQuantity(int id, int quantity, double priceDetail, double priceProduct, int idOrder) {
        SQLiteDatabase db = getWritableDatabase();
        if(quantity > 1){
            quantity = quantity - 1;
            priceDetail = priceDetail - priceProduct;
            if (db != null) {
                try {
                    db.execSQL("UPDATE OrderDetails SET cantidad = '" + quantity + "', precio = '" + priceDetail + "' WHERE id = '" + id + "'");
                    db.close();
                } catch (Exception ex) {
                    Log.d("Tag", ex.toString());
                }
            }
        }
        updateTotalPrice(idOrder, priceDetail);
    }

    public void decreaseStock(int id, int quantity, int stock) {
        SQLiteDatabase db = getWritableDatabase();
        stock = stock - quantity;
        if (db != null) {
            try {
                db.execSQL("UPDATE Products SET stock = '" + stock + "' WHERE id = '" + id + "'");
                db.close();
            } catch (Exception ex) {
                Log.d("Tag", ex.toString());
            }
        }
    }

    public void increaseStock(int id, int quantity, int stock) {
        SQLiteDatabase db = getWritableDatabase();
        stock = stock + quantity;
        if (db != null) {
            try {
                db.execSQL("UPDATE Products SET stock = '" + stock + "' WHERE id = '" + id + "'");
                db.close();
            } catch (Exception ex) {
                Log.d("Tag", ex.toString());
            }
        }
    }

    public void addCategory(String name) {
        SQLiteDatabase db = getWritableDatabase();
        if (db != null) {
            db.execSQL("INSERT INTO Categories (nombre) VALUES ('" + name + "')");
            db.close();
        }
    }

    public List<CategoryModel> getCategories() {
        SQLiteDatabase db = getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM Categories", null);
        List<CategoryModel> categories = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                categories.add(new CategoryModel(cursor.getInt(0), cursor.getString(1)));
            } while ((cursor.moveToNext()));
        }
        return categories;
    }

    public void updateCategory(int id, String name) {
        SQLiteDatabase db = getWritableDatabase();
        if (db != null) {
            try {
                db.execSQL("UPDATE Categories SET nombre = '" + name + "' WHERE id = '" + id + "'");
                db.close();
            } catch (Exception ex) {
                Log.d("Tag", ex.toString());
            }
        }
    }

    public void deleteCategory(int idCategory) {
        SQLiteDatabase db = getWritableDatabase();
        if (db != null) {
            try {
                db.execSQL("DELETE FROM Categories WHERE id = '" + idCategory + "'");
                db.close();
            } catch (Exception ex) {
                Log.d("Tag", ex.toString());
            }
        }
    }

    public List<CategoryModel> selectCategoryById(int id) {
        SQLiteDatabase db = getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM Categories WHERE id = '" + id + "' ", null);
        List<CategoryModel> categories = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                categories.add(new CategoryModel(cursor.getInt(0), cursor.getString(1)));
            } while ((cursor.moveToNext()));
        }
        return categories;
    }
}

package com.anjovaca.gestipedi.DB.Models;

import android.graphics.drawable.Drawable;

public class ProductsModel {
    int id, idCategoria, stock, cantidadVendida;
    Drawable img;
    String nombre, descripcion;
    double precio;

    public ProductsModel() {
    }

    public ProductsModel(int id, int idCategoria, int stock, int cantidadVendida, Drawable img, String nombre, String descripcion, double precio) {
        this.id = id;
        this.idCategoria = idCategoria;
        this.stock = stock;
        this.cantidadVendida = cantidadVendida;
        this.img = img;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getCantidadVendida() {
        return cantidadVendida;
    }

    public void setCantidadVendida(int cantidadVendida) {
        this.cantidadVendida = cantidadVendida;
    }

    public Drawable getImg() {
        return img;
    }

    public void setImg(Drawable img) {
        this.img = img;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}

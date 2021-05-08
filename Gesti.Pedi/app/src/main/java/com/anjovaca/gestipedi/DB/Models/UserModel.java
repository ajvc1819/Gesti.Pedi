package com.anjovaca.gestipedi.DB.Models;

public class UserModel {
    int id;
    String name, lastname, username, password, rol;


    public UserModel(int id, String name, String lastname, String username, String password, String rol) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.rol = rol;
    }

    public UserModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}

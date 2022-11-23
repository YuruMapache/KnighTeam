package com.example.justajuan.model;

public class User {
    private String nombre;
    private int edad;
    private String genero;
    private Rol rol;

    public User(String nombre, int edad, String genero, Rol rol) {
        this.nombre = nombre;
        this.edad = edad;
        this.genero = genero;
        this.rol = rol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}

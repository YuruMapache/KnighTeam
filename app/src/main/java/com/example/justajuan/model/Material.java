package com.example.justajuan.model;

public class Material {
    private String name;
    private int idDrawable;
    private int cantidad; //El numero que tienes (e.j: el herrero tiene 15 de hierro)
    private int precio; //El precio/valor del material cuando lo compras en la tienda (e.j: el hierro vale 2 monedas).
    private String rol;

    public Material() {

    }

    public Material(String name, int idDrawable, int cantidad, int precio, String rol) {
        setName(name);
        setIdDrawable(idDrawable);
        setCantidad(cantidad);
        setPrecio(precio);
        setRol(rol);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIdDrawable() {
        return idDrawable;
    }

    public void setIdDrawable(int idDrawable) {
        this.idDrawable = idDrawable;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public String getRol() {
        if (this.rol != null) {
            return this.rol;
        } else {
            return "";
        }
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}

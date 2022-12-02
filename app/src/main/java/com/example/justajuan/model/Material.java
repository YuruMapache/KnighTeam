package com.example.justajuan.model;

public class Material {
    private String name;
    private int idDrawable;
    private int cantidad;

    public Material(String name, int idDrawable, int cantidad){
        this.name=name;
        this.idDrawable=idDrawable;
        this.cantidad=cantidad;

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

    public String getCantidad() {
        return String.valueOf(cantidad);
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}

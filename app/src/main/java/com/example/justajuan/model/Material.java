package com.example.justajuan.model;

public class Material {
    private String name;
    private int idDrawable;
    private int cantidad;
    private String rol;

    public Material(){

    }

    public Material(String name, int idDrawable, int cantidad, String rol){
        this.name=name;
        this.idDrawable=idDrawable;
        this.cantidad=cantidad;
        this.rol=rol;
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

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}

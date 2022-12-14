package com.example.justajuan.model;

/**
 * Clase para definir un estadistico. Los 4 que hay son: salud, vida, velocidad y estamina.
 */
public class Estadistico {

    private String nombre;
    private int idDrawable;
    private int valorActual;
    private int valorMax;

    public Estadistico(){

    }

    public Estadistico(String nombre, int idDrawable, int valorActual, int valorMax){
        setNombre(nombre);
        setIdDrawable(idDrawable);
        setValorActual(valorActual);
        setValorMax(valorMax);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdDrawable() {
        return idDrawable;
    }

    public void setIdDrawable(int idDrawable) {
        this.idDrawable = idDrawable;
    }

    public int getValorActual() {
        return valorActual;
    }

    public void setValorActual(int valorActual) {
        this.valorActual = valorActual;
    }

    public int getValorMax() {
        return valorMax;
    }

    public void setValorMax(int valorMax) {
        this.valorMax = valorMax;
    }
}

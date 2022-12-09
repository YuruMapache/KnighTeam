package com.example.justajuan.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Consumible extends Objeto implements Serializable {

    private boolean veneno;
    private boolean paraCaballo;

    public Consumible(){

    }

    public Consumible(String nombre, String clase, String descripcion, int salud, int ataque, int defensa,
                      int estamina, String tiempoCreacion, Map<String, Integer> precio,
                      boolean veneno, boolean paraCaballo) {
        super(nombre, clase, descripcion, salud, ataque, defensa, estamina, tiempoCreacion, precio);
        this.veneno = veneno;
        this.paraCaballo = paraCaballo;
    }

    public boolean isVeneno() {
        return veneno;
    }

    public void setVeneno(boolean veneno) {
        this.veneno = veneno;
    }

    public boolean isParaCaballo() {
        return paraCaballo;
    }

    public void setParaCaballo(boolean paraCaballo) {
        this.paraCaballo = paraCaballo;
    }

}

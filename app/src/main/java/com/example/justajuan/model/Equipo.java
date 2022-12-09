package com.example.justajuan.model;

import java.io.Serializable;
import java.util.Map;

public class Equipo extends Objeto implements Serializable {

    private int durabilidad;
    private boolean metalico;
    private boolean pesado;
    private boolean oxidado;
    private String tipo;


    public Equipo(){

    }

    public Equipo(String nombre, String clase, String descripcion, int salud, int ataque,
                  int defensa, int estamina, String tiempoCreacion,
                  Map<String, Integer> precio, int durabilidad, boolean metalico, boolean pesado,
                  boolean oxidado, String tipo) {

        super(nombre, clase, descripcion, salud, ataque, defensa, estamina, tiempoCreacion, precio);
        this.durabilidad = durabilidad;
        this.metalico = metalico;
        this.pesado = pesado;
        this.oxidado = oxidado;
        this.tipo = tipo;
    }

    public int getDurabilidad() {
        return durabilidad;
    }

    public void setDurabilidad(int durabilidad) {
        this.durabilidad = durabilidad;
    }

    public boolean isMetalico() {
        return metalico;
    }

    public void setMetalico(boolean metalico) {
        this.metalico = metalico;
    }

    public boolean isPesado() {
        return pesado;
    }

    public void setPesado(boolean pesado) {
        this.pesado = pesado;
    }

    public boolean isOxidado() {
        return oxidado;
    }

    public void setOxidado(boolean oxidado) {
        this.oxidado = oxidado;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}

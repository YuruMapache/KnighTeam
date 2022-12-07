package com.example.justajuan.model;

import java.io.Serializable;

public class Enemigo implements Serializable {

    private String nombre;
    private int salud;
    //private int cansancio;
    //private int motivacion;
    private int ataque;
    private int defensa;
    private double velocidadAtaque;

    public Enemigo() {
    }

    public Enemigo(String nombre, int salud, int ataque, int defensa, double velocidadAtaque) {
        this.nombre = nombre;
        this.salud = salud;
        this.ataque = ataque;
        this.defensa = defensa;
        this.velocidadAtaque = velocidadAtaque;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public int getSalud() {
        return salud;
    }

    public void setSalud(int salud) {
        this.salud = salud;
    }

    public int getAtaque() {
        return ataque;
    }

    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }

    public int getDefensa() {
        return defensa;
    }

    public void setDefensa(int defensa) {
        this.defensa = defensa;
    }

    public double getVelocidadAtaque() {
        return velocidadAtaque;
    }

    public void setVelocidadAtaque(double velocidadAtaque) {
        this.velocidadAtaque = velocidadAtaque;
    }
}

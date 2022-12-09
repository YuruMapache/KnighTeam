package com.example.justajuan.model;

import java.io.Serializable;

public class Enemigo implements Serializable {

    private String nombre;
    private int salud;
    private int ataque;
    private double velocidadAtaque;
    private int monedasGanas;

    public Enemigo() {
    }

    public Enemigo(String nombre, int salud, int ataque, double velocidadAtaque, int monedasGanas) {
        setNombre(nombre);
        setSalud(salud);
        setAtaque(ataque);
        setVelocidadAtaque(velocidadAtaque);
        setMonedasGanas(monedasGanas);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public double getVelocidadAtaque() {
        return velocidadAtaque;
    }

    public void setVelocidadAtaque(double velocidadAtaque) {
        this.velocidadAtaque = velocidadAtaque;
    }

    public int getMonedasGanas() {
        return monedasGanas;
    }

    public void setMonedasGanas(int monedasGanas) {
        this.monedasGanas = monedasGanas;
    }
}

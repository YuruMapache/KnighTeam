package com.example.justajuan.model;

import java.io.Serializable;

public class Caballero implements Serializable {

    private int salud;
    private int ataque;
    private int velocidadAtaque;
    private int estamina;
    private int monedas;

    public Caballero(int salud, int ataque, int velocidadAtaque){
        setSalud(100);
        setAtaque(5);
        setVelocidadAtaque(1);
        setEstamina(100);
        setMonedas(100);
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

    public int getVelocidadAtaque() {
        return velocidadAtaque;
    }

    public void setVelocidadAtaque(int velocidadAtaque) {
        this.velocidadAtaque = velocidadAtaque;
    }

    public int getEstamina() {
        return estamina;
    }

    public void setEstamina(int estamina) {
        this.estamina = estamina;
    }

    public int getMonedas() {
        return monedas;
    }

    public void setMonedas(int monedas) {
        this.monedas = monedas;
    }
}

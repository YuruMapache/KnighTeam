package com.example.justajuan.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Caballero implements Serializable {

    private int salud;
    private int salud_max;
    private int ataque;
    private int velocidadAtaque;
    private int estamina;
    private int monedas;
    private ArrayList<Objeto> equipado;



    public Caballero() {

    }

    public Caballero(int salud, int salud_max, int ataque, int velocidadAtaque, int estamina, int monedas, ArrayList<Objeto> equipado) {
        this.salud = salud;
        this.salud_max = salud_max;
        this.ataque = ataque;
        this.velocidadAtaque = velocidadAtaque;
        this.estamina = estamina;
        this.monedas = monedas;
        this.equipado = equipado;
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

    public int getSalud_max() {
        return salud_max;
    }

    public void setSalud_max(int salud_max) {
        this.salud_max = salud_max;
    }

    public ArrayList<Objeto> getEquipado() {
        return equipado;
    }

    public void setEquipado(ArrayList<Objeto> equipado) {
        this.equipado = equipado;
    }

}

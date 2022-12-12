package com.example.justajuan.model;

import android.os.CountDownTimer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Objeto implements Serializable {

    private String nombre;
    private String clase;
    private String descripcion;
    private int salud;
    private int ataque;
    private int velocidad;
    private int estamina;
    private int tiempo; //Tiempo que tarda en craftearse. Ideal en múltiplos de número de rondas
    private CountDownTimer contador;
    private Map<String, Integer> precio = new HashMap<>();
    private boolean esConsumible;
    private int idDrawable;
    private boolean acabado;
    private long tiempoQueFalta;


    public Objeto() {

    }

    public Objeto(String nombre,String clase, String descripcion, int salud, int ataque, int velocidad, int estamina, int tiempo, Map<String, Integer> precio, boolean esConsumible, int idDrawable) {
        setNombre(nombre);
        setClase(clase);
        setDescripcion(descripcion);
        setSalud(salud);
        setAtaque(ataque);
        setVelocidad(velocidad);
        setTiempo(tiempo);
        setPrecio(precio);
        setEstamina(estamina);
        setEsConsumible(esConsumible);
        setIdDrawable(idDrawable);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public int getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    public int getEstamina() {
        return estamina;
    }

    public void setEstamina(int estamina) {
        this.estamina = estamina;
    }

    public int getTiempo() {
        return tiempo;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }

    public CountDownTimer getContador() {
        return contador;
    }

    public void setContador(CountDownTimer contador) {
        this.contador = contador;
    }

    public Map<String, Integer> getPrecio() {
        return precio;
    }

    public void setPrecio(Map<String, Integer> precio) {
        this.precio = precio;
    }

    public boolean isEsConsumible() {
        return esConsumible;
    }

    public void setEsConsumible(boolean esConsumible) {
        this.esConsumible = esConsumible;
    }

    public int getIdDrawable() {
        return idDrawable;
    }

    public void setIdDrawable(int idDrawable) {
        this.idDrawable = idDrawable;
    }

    public boolean isAcabado() {
        return acabado;
    }

    public void setAcabado(boolean acabado) {
        this.acabado = acabado;
    }

    public long getTiempoQueFalta() {
        return tiempoQueFalta;
    }

    public void setTiempoQueFalta(long tiempoQueFalta) {
        this.tiempoQueFalta = tiempoQueFalta;
    }
}

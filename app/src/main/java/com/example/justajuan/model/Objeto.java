package com.example.justajuan.model;

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
    private int defensa;
    private int estamina;
    private String tiempoCreacion;
    private Map<String, Integer> precio= new HashMap<>();

    public Objeto(){

    }
    public Objeto(String nombre, String clase, String descripcion, int salud, int ataque, int defensa, int estamina, String tiempoCreacion, Map<String, Integer> precio) {
        setNombre(nombre);
        setClase(clase);
        setDescripcion(descripcion);
        setSalud(salud);
        setAtaque(ataque);
        setDefensa(defensa);
        setTiempoCreacion(tiempoCreacion);
        setPrecio(precio);
        setEstamina(estamina);
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

    public int getDefensa() {
        return defensa;
    }

    public void setDefensa(int defensa) {
        this.defensa = defensa;
    }

    public int getEstamina() {
        return estamina;
    }

    public void setEstamina(int estamina) {
        this.estamina = estamina;
    }

    public String getTiempoCreacion() {
        return tiempoCreacion;
    }

    public void setTiempoCreacion(String tiempoCreacion) {
        this.tiempoCreacion = tiempoCreacion;
    }

    public Map<String, Integer> getPrecio() {
        return precio;
    }

    public void setPrecio(Map<String, Integer> precio) {
        this.precio = precio;
    }
}

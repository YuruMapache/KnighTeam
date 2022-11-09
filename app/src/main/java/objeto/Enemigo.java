package objeto;

import java.io.Serializable;

public class Enemigo implements Serializable {

    private String nombre;
    private int salud;
    private int cansancio;
    private int motivacion;
    private int ataque;
    private int defensa;

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getSalud() {
        return salud;
    }

    public void setSalud(int salud) {
        this.salud = salud;
    }

    public int getCansancio() {
        return cansancio;
    }

    public void setCansancio(int cansancio) {
        this.cansancio = cansancio;
    }

    public int getMotivacion() {
        return motivacion;
    }

    public void setMotivacion(int motivacion) {
        this.motivacion = motivacion;
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

}

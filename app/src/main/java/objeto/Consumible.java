package objeto;

import java.io.Serializable;
import java.util.Map;

public class Consumible extends Objeto implements Serializable {

    private boolean veneno;
    private boolean paraCaballo;

    public Consumible(String nombre, String clase, String descripcion, int ataque,
                      int defensa, int cansancio, int motivacion, String tiempoCreacion,
                      Map<String, Integer> precio, boolean veneno, boolean paraCaballo) {
        super(nombre, clase, descripcion, ataque, defensa, cansancio, motivacion, tiempoCreacion, precio);
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

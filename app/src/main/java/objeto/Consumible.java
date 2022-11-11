package objeto;

import java.io.Serializable;
import java.util.Map;

public class Consumible extends Objeto implements Serializable {

    private boolean veneno;
    private boolean paraCaballo;


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

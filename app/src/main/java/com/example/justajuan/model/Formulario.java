package com.example.justajuan.model;

import java.util.HashMap;
import java.util.Map;

public class Formulario {

    private Map<Integer, Integer> preguntas;

    public Formulario() {
        this.preguntas = new HashMap<>();
    }

    public void setPregunta(int num, int res) {
        preguntas.put(num,res);
    }

    public Map<Integer, Integer> getPreguntas(){
        return preguntas;
    }


}

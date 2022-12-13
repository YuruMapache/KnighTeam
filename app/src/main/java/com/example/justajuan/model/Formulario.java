package com.example.justajuan.model;

import java.util.HashMap;
import java.util.Map;

public class Formulario {

    private Map<Integer, Integer> preguntas;
    private String preguntaCuestionario;


    public Formulario() {

    }

    public void setPregunta(int num, int res) {
        preguntas.put(num,res);
    }

    public Map<Integer, Integer> getPreguntas(){
        return preguntas;
    }

    public String getPreguntaCuestionario() {
        return preguntaCuestionario;
    }

    public void setPreguntaCuestionario(String preguntaCuestionario) {
        this.preguntaCuestionario = preguntaCuestionario;
    }
}

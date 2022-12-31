package com.example.justajuan.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Clase encargada de modelar un cuestionario.
 */
public class Cuestionario {
    private List<Pregunta> preguntas;

    /**
     * Constructor de la clase Cuestionario.
     * Inicializa una lista vacía de objetos Pregunta.
     */
    public Cuestionario() {
        this.preguntas = new ArrayList<>();
    }

    /**
     * Anade una pregunta al cuestionario.
     *
     * @param p Pregunta a anadir.
     */
    public void addPregunta(Pregunta p) {
        this.preguntas.add(p);
    }

    /**
     * Devuelve la pregunta con el indice introducido.
     *
     * @param index Indice de la pregunta.
     *
     * @return Pregunta elegida.
     */
    public Pregunta getPregunta(int index) {
        return preguntas.get(index);
    }

    /**
     * Devuekve la lista de objetos Pregunta del cuestionario.
     *
     * @return Lista de objetos Pregunta en el cuestionario.
     */
    public List<Pregunta> getPreguntas(){
        return preguntas;
    }

    /**
     * Devuelve un Map con el promedio de las preguntas de los cuestionarios.
     *
     * @param lista Lista de cuestionarios.
     *
     * @return Pares ID pregunta, media de la pregunta.
     */
    public static Map<Integer,Float> getResults(List<Cuestionario> lista){
        Map<Integer,Float> results = new HashMap<>();
        lista.forEach(form -> form.getPreguntas().forEach(elem ->{
            int id = elem.getId();
            float res = elem.getValor();
            Float idres = results.get(id);
            if(idres!=null) {
                results.put(id, (res + idres) / 2);
            }
        }));
        return results;
    }
}

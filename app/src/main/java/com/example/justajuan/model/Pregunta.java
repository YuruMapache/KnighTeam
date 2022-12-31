package com.example.justajuan.model;

/**
 * Clase encargada de modelar una pregunta.
 */
public class Pregunta {
    private final int id;
    private String descripcion;
    private int valor;

    /**
     * Constructor de la clase Pregunta.
     *
     * @param id          Identificador de la pregunta.
     * @param descripcion Texto de la pregunta
     */
    public Pregunta(int id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    /**
     * Constructor de la clase Pregunta.
     *
     * @param id    Identificador de la pregunta.
     * @param valor Valor de 0 a 4 marcado en la pregunta.
     */
    public Pregunta(int id, int valor) {
        this.id = id;
        if (!validateValue(valor)) {
            throw new IllegalArgumentException("El valor de la pregunta debe estar entre 0 y 5");
        }
        this.valor = valor;
    }

    /**
     * Devuelve el identificador de la pregunta.
     *
     * @return Identificador de la pregunta.
     */
    public int getId() {
        return id;
    }

    /**
     * Devuelve el valor de la pregunta.
     *
     * @return Valor de la pregunta.
     */
    public int getValor() {
        return valor;
    }

    public String getDescripcion() {
        return descripcion;
    }


    /**
     * Se encarga de validar el valor de la pregunta.
     *
     * @param val Valor de la pregunta
     * @return True si la pregunta es válida. False si no lo es.
     */
    private boolean validateValue(int val) {
        return 0 <= val && val <= 4;
    }
}

package com.example.justajuan.model;

public class Cuestionario {

    private String pregunta;
    private int muyDesacuerdo;
    private int desacuerdo;
    private int indiferente;
    private int deAcuerdo;
    private int muyAcuerdo;

    public Cuestionario(String pregunta, int muyDesacuerdo, int desacuerdo, int indiferente, int deAcuerdo, int muyAcuerdo) {
        this.pregunta = pregunta;
        this.muyDesacuerdo = muyDesacuerdo;
        this.desacuerdo = desacuerdo;
        this.indiferente = indiferente;
        this.deAcuerdo = deAcuerdo;
        this.muyAcuerdo = muyAcuerdo;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public int getMuyDesacuerdo() {
        return muyDesacuerdo;
    }

    public void setMuyDesacuerdo(int muyDesacuerdo) {
        this.muyDesacuerdo = muyDesacuerdo;
    }

    public int getDesacuerdo() {
        return desacuerdo;
    }

    public void setDesacuerdo(int desacuerdo) {
        this.desacuerdo = desacuerdo;
    }

    public int getIndiferente() {
        return indiferente;
    }

    public void setIndiferente(int indiferente) {
        this.indiferente = indiferente;
    }

    public int getDeAcuerdo() {
        return deAcuerdo;
    }

    public void setDeAcuerdo(int deAcuerdo) {
        this.deAcuerdo = deAcuerdo;
    }

    public int getMuyAcuerdo() {
        return muyAcuerdo;
    }

    public void setMuyAcuerdo(int muyAcuerdo) {
        this.muyAcuerdo = muyAcuerdo;
    }
}

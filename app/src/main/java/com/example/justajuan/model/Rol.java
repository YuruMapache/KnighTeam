package com.example.justajuan.model;

public enum Rol {
    CABALLERO(1),
    HERRERO(2),
    MAESTRO_CUADRAS(3),
    CURANDERO(4),
    DRUIDA(5);

    private int numRol;

    Rol (int numRol){
        this.numRol = numRol;
    }
}

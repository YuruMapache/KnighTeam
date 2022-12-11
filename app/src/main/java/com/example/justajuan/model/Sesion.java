package com.example.justajuan.model;

public class Sesion {

    private static Sesion sesion;
    private static User usuario;
    private static int numlobby;

    public static Sesion getInstance() {
        if (sesion == null) {
            sesion = new Sesion();
        }
        return sesion;
    }

    private Sesion() {
    }

    public static int getNumLobby() {
        return numlobby;
    }

    public static void setNumLobby(int numlobby) {
        Sesion.numlobby = numlobby;
    }

    public static User getUsuario() {
        return usuario;
    }

    public static void setUsuario(User usuario) {
        Sesion.usuario = usuario;
    }

    public Rol getRol() {
        return usuario.getRol();
    }

    //Del 0 al 4
    public void setRol(int rol) {
         usuario.setRol(Rol.values()[rol]);
    }

}



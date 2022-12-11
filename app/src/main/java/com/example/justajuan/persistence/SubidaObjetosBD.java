package com.example.justajuan.persistence;

import androidx.annotation.NonNull;

import com.example.justajuan.R;
import com.example.justajuan.model.Enemigo;
import com.example.justajuan.model.Material;
import com.example.justajuan.model.Objeto;
import com.example.justajuan.model.Sesion;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class SubidaObjetosBD {

    public static void setObjetos(){
        FirebaseDatabase fd = FirebaseDatabase.getInstance();
        DatabaseReference dr = fd.getReference().child("Objetos");

        ArrayList<Objeto> listaObjetos = new ArrayList<>();
        HashMap<String,Integer> materiales = new HashMap<String, Integer>();
        materiales.put("Hierro", 10);
        materiales.put("Madera",5);
        listaObjetos.add(new Objeto("Espada nvl1", "Herrero", "Espada simple para mejorar nuestro daño",
                0,2,0,0,1,materiales,false, R.drawable.espada_ajustada));
        materiales.clear();
        materiales.put("Hierro", 20);
        materiales.put("Madera",15);
        listaObjetos.add(new Objeto("Espada nvl2", "Herrero", "Espada mejorada para aumentar nuestro daño",
                0,10,0,0,2,materiales,false, R.drawable.espada_ajustada));
        materiales.clear();
        materiales.put("Hierro", 15);
        materiales.put("Madera",10);
        listaObjetos.add(new Objeto("Lanza nvl1", "Herrero", "Lanza simple para mejorar nuestro daño",
                0,5,0,0,1,materiales,false, R.drawable.lanza_ajustada));
        materiales.clear();
        materiales.put("Hierro", 30);
        materiales.put("Madera",20);
        listaObjetos.add(new Objeto("Lanza nvl2", "Herrero", "Lanza mejorada para aumentar nuestro daño",
                0,15,0,0,2,materiales,false, R.drawable.lanza_ajustada));
        materiales.clear();
        materiales.put("Hierro", 15);
        listaObjetos.add(new Objeto("Herradura nvl1", "Herrero", "Herradura simple para mejorar nuestra velocidad",
                0,0,1,0,1,materiales,false, R.drawable.herradura_ajustada));
        materiales.clear();
        materiales.put("Hierro", 30);
        listaObjetos.add(new Objeto("Herradura nvl2", "Herrero", "Herradura mejorada para aumentar nuestra velocidad",
                0,0,5,0,2,materiales,false, R.drawable.herradura_ajustada));
        materiales.clear();
        materiales.put("Hierro", 10);
        materiales.put("Madera", 5);
        listaObjetos.add(new Objeto("Armadura nvl1", "Herrero", "Armadura simple para mejorar nuestras defensas",
                25,0,0,0,1,materiales,false, R.drawable.armadura_ajustada));
        materiales.clear();
        materiales.put("Hierro", 20);
        materiales.put("Madera", 15);
        listaObjetos.add(new Objeto("Armadura nvl2", "Herrero", "Armadura mejorada para aumentar nuestras defensas",
                100,0,0,0,2,materiales,false, R.drawable.armadura_ajustada));
        materiales.clear();
        materiales.put("Tela", 10);
        listaObjetos.add(new Objeto("Vendas nvl1", "Curandero", "Simples vendas para recuperar nuestra salud",
                20,0,0,0,1,materiales,true, R.drawable.vendas_ajustada));
        materiales.clear();
        materiales.put("Tela", 15);
        materiales.put("Cuero", 5);
        listaObjetos.add(new Objeto("Vendas nvl2", "Curandero", "Vendas mejoradas para recuperar nuestra salud",
                50,0,0,0,1,materiales,true, R.drawable.vendas_ajustada));
        materiales.clear();
        materiales.put("Tela", 7);
        listaObjetos.add(new Objeto("Remedio casero nvl1", "Curandero", "Simples remedio para recuperar nuestra salud",
                10,0,0,0,1,materiales,true, R.drawable.remedio_casero_ajustado));
        materiales.clear();
        materiales.put("Tela", 10);
        materiales.put("Cuero", 3);
        listaObjetos.add(new Objeto("Remedio casero nvl2", "Curandero", "Remedio mejorado para recuperar nuestra salud",
                40,0,0,0,1,materiales,true, R.drawable.remedio_casero_ajustado));
        materiales.clear();
        materiales.put("Tela", 15);
        listaObjetos.add(new Objeto("Vigorizante nvl1", "Curandero", "Vigorizante simple para recuperar algo de estamina",
                0,0,0,20,1,materiales,true, R.drawable.vigorizante_ajustado));
        materiales.clear();
        materiales.put("Tela", 20);
        listaObjetos.add(new Objeto("Vigorizante nvl2", "Curandero", "Vigorizante para recuperar estamina",
                0,0,0,50,1,materiales,true, R.drawable.vigorizante_ajustado));
        materiales.clear();
        materiales.put("Agua bendita", 1);
        listaObjetos.add(new Objeto("Pocion ataque nvl1", "Druida", "Pocion para mejorar nuestro ataque",
                0,10,0,0,1,materiales,true, R.drawable.pocion_ajustada));
        materiales.clear();
        materiales.put("Agua bendita", 2);
        listaObjetos.add(new Objeto("Pocion ataque nvl2", "Druida", "Pocion para mejorar aun mas nuestro ataque",
                0,40,0,0,2,materiales,true, R.drawable.pocion_ajustada));
        materiales.clear();
        materiales.put("Agua bendita", 1);
        listaObjetos.add(new Objeto("Pocion salud nvl1", "Druida", "Pocion para mejorar nuestra salud",
                20,0,0,0,1,materiales,true, R.drawable.pocion_ajustada));
        materiales.clear();
        materiales.put("Agua bendita", 2);
        listaObjetos.add(new Objeto("Pocion salud nvl2", "Druida", "Pocion para mejorar aun mas nuestra salud",
                50,0,0,0,2,materiales,true, R.drawable.pocion_ajustada));
        materiales.clear();
        materiales.put("Agua bendita", 1);
        listaObjetos.add(new Objeto("Pocion velocidad nvl1", "Druida", "Pocion para mejorar nuestra velocidad",
                0,0,2,0,1,materiales,true, R.drawable.pocion_ajustada));
        materiales.clear();
        materiales.put("Agua bendita", 2);
        listaObjetos.add(new Objeto("Pocion velocidad nvl2", "Druida", "Pocion para mejorar aun mas nuestra velocidad",
                0,0,5,0,2,materiales,true, R.drawable.pocion_ajustada));
        materiales.clear();
        materiales.put("Agua bendita", 1);
        materiales.put("Madera", 10);
        listaObjetos.add(new Objeto("Amuleto nvl1", "Druida", "Amuleto para mejorar un poco todos los atributos",
                10,10,2,0,1,materiales,true, R.drawable.amuleto_ajustado));
        materiales.clear();
        materiales.put("Agua bendita", 2);
        materiales.put("Madera", 30);
        listaObjetos.add(new Objeto("Amuleto nvl2", "Druida", "Amuleto para mejorar todos los atributos",
                20,20,3,0,2,materiales,true, R.drawable.amuleto_ajustado));
        materiales.clear();
        materiales.put("Heno", 20);
        materiales.put("Cuero", 10);
        listaObjetos.add(new Objeto("Montura nvl1", "Maestro_Cuadras", "Montura simple para mejorar la velocidad",
                0,0,2,0,1,materiales,true, R.drawable.montura_ajustada));
        materiales.clear();
        materiales.put("Heno", 40);
        materiales.put("Cuero", 20);
        listaObjetos.add(new Objeto("Montura nvl2", "Maestro_Cuadras", "Montura mejorada para aumentar la velocidad",
                0,0,5,0,2,materiales,true, R.drawable.montura_ajustada));
        materiales.clear();



        dr.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Sesion sesion = Sesion.getInstance();

                for (int i=0; i<listaObjetos.size();i++) {

                    dr.child(listaObjetos.get(i).getNombre()).setValue(listaObjetos.get(i));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("DATABASE ERROR");
            }
        });

    }

    public static void subirEnemigos(){

        FirebaseDatabase fd = FirebaseDatabase.getInstance();
        DatabaseReference dr = fd.getReference().child("Enemigos");

        ArrayList<Enemigo> listaEnemigos = new ArrayList<>();

        listaEnemigos.add(new Enemigo("Campesino Paco",100,6,1,200));
        listaEnemigos.add(new Enemigo("Pepe el tortas",125,10,1,200));
        listaEnemigos.add(new Enemigo("Soberbia",175,20,2,200));
        listaEnemigos.add(new Enemigo("Avaricia",200,30,3,200));
        listaEnemigos.add(new Enemigo("Lujuria",250,40,5,200));
        listaEnemigos.add(new Enemigo("Gula",300,50,8,200));
        listaEnemigos.add(new Enemigo("Envidia",400,60,10,200));
        listaEnemigos.add(new Enemigo("Pereza",550,70,15,200));
        listaEnemigos.add(new Enemigo("Ira",700,80,20,200));
        listaEnemigos.add(new Enemigo("Mano Derecha Del Rey",1000,100,30,10000));

        dr.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Sesion sesion = Sesion.getInstance();

                for (int i=0; i<listaEnemigos.size();i++) {

                    dr.child(String.valueOf(i+1)).setValue(listaEnemigos.get(i));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("DATABASE ERROR");
            }
        });
    }
}

package com.example.justajuan.persistence;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.justajuan.model.Caballero;
import com.example.justajuan.model.Enemigo;
import com.example.justajuan.model.Formulario;
import com.example.justajuan.model.Material;
import com.example.justajuan.model.Objeto;
import com.example.justajuan.model.Rol;
import com.example.justajuan.model.Sesion;
import com.example.justajuan.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FirebaseDAO {

    public static void getConsumable(String id) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("user").child(id).child("veneno").getDatabase();
    }

    public static void setUser(String id, String name, String email, String password) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        String key = mDatabase.child("user").push().getKey();
        DatabaseReference mUser = mDatabase.child("user").child(key);
        mUser.child("name").child(id).setValue(name);
        mUser.child("email").child(id).setValue(email);
        mUser.child("password").child(id).setValue(password);
    }

    public static void setMateriales(String nlobby, ArrayList<Material> materiales) {
        FirebaseDatabase fd = FirebaseDatabase.getInstance();
        DatabaseReference dr = fd.getReference().child("Materiales").child(nlobby);

        dr.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Sesion sesion = Sesion.getInstance();

                for (int i = 0; i < materiales.size(); i++) {

                    dr.child(materiales.get(i).getName()).setValue(materiales.get(i));
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("DATABASE ERROR");
            }
        });

    }


    public static void setCaballero(String nlobby, Caballero caballero) {
        FirebaseDatabase fd = FirebaseDatabase.getInstance();
        DatabaseReference dr = fd.getReference().child("Caballero").child(nlobby);

        dr.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Sesion sesion = Sesion.getInstance();

                dr.setValue(caballero);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("DATABASE ERROR");
            }
        });

    }


    public static void setPlayer(String nlobby, User user) {
        FirebaseDatabase fd = FirebaseDatabase.getInstance();
        DatabaseReference dr = fd.getReference().child("Partida").child(nlobby);

        dr.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Sesion sesion = Sesion.getInstance();

                while (sesion.getRol() == null) {
                    String tmp = String.valueOf((int) (Math.random() * 5 + 1));
                    if (!snapshot.hasChild(tmp)) {
                        user.setRol(Rol.values()[Integer.parseInt(tmp) - 1]);
                        dr.child(tmp).setValue(user);
                        dr.child(tmp).child("combateListo").setValue(0);
                        dr.child(tmp).child("resultadosListos").setValue(0);
                        sesion.setRol(Integer.parseInt(tmp) - 1);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("DATABASE ERROR");
            }
        });
    }

    /**
     * Obtiene los atributos de un objeto.
     *
     * @param id    Identificador del objeto.
     * @param level Nivel del objeto a obtener.
     * @return Objeto de tipo Objeto.
     */
    public static Objeto getObjet(String id, int level) {
        FirebaseDatabase fd = FirebaseDatabase.getInstance();
        DatabaseReference dr = fd.getReference().child("Objetos").child(id).child(String.valueOf(level));
        Objeto obj = new Objeto();

        dr.get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            } else {
                DataSnapshot ds = task.getResult();
                obj.setSalud((Integer) ds.child("Salud").getValue());
                obj.setAtaque((Integer) ds.child("Ataque").getValue());
                obj.setVelocidad((Integer) ds.child("Velocidad").getValue());
                obj.setEstamina((Integer) ds.child("Estamina").getValue());
                obj.setTiempo((Integer) ds.child("Tiempo").getValue());
                //Obtener lista de precios
                Map<String, Integer> costes = new HashMap();
                for (DataSnapshot snapshot : ds.child("Precio").getChildren()) {
                    costes.put(snapshot.getKey(), (Integer) snapshot.getValue());
                }
                obj.setPrecio(costes);
            }
        });
        return obj;
    }

    public static void setForm(int nlobby, int user, int id, Formulario form) {
        FirebaseDatabase fd = FirebaseDatabase.getInstance();
        DatabaseReference dr = fd.getReference().child("Partida").child(String.valueOf(nlobby)).child(String.valueOf(user)).child("Formularios").child(String.valueOf(id));
        Map preguntas = form.getPreguntas();
        preguntas.forEach(
                (key, value)
                        -> dr.setValue(key, value)
        );
    }


    public static void deletePlayer(String nlobby, int id) {
        FirebaseDatabase fd = FirebaseDatabase.getInstance();
        DatabaseReference dr = fd.getReference().child("Partida");
        dr.child(nlobby).child(String.valueOf(id)).removeValue();
    }


}

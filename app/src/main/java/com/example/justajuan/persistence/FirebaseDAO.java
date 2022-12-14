package com.example.justajuan.persistence;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.justajuan.model.Caballero;
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
import java.util.concurrent.atomic.AtomicReference;

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
                        dr.child(tmp).child("numRonda").setValue(1);
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

    public static void setForm(int nlobby, int user, Formulario form, String tipo) {
        FirebaseDatabase fd = FirebaseDatabase.getInstance();
        DatabaseReference dr = fd.getReference().child("Cuestionarios").child(String.valueOf(nlobby)).child(String.valueOf(user)).child(tipo);
        Map preguntas = form.getPreguntas();
        preguntas.forEach(
                (key, value)
                        -> dr.child(String.valueOf(key)).setValue(value)
        );
    }

    public static Map<Integer, Map<Integer, Integer>> getResults(int nlobby) {
        FirebaseDatabase fd = FirebaseDatabase.getInstance();
        DatabaseReference dr = fd.getReference().child("Cuestionarios").child(String.valueOf(nlobby));
        Map<Integer, Map<Integer, Integer>> resultados = new HashMap<>();

        dr.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (int j = 1; j < 6; j++) {
                    for (int i = 0; i < 8; i++) {
                        resultados.get(i).put((Integer) snapshot.child(String.valueOf(j)).child("Intermedio").child(String.valueOf(i)).getValue(), resultados.get(i).get(snapshot.child(String.valueOf(j)).child("Intermedio").child(String.valueOf(i)).getValue()) + 1);
                    }
                }
                for (int j = 1; j < 6; j++) {
                    for (int i = 0; i < 8; i++) {
                        resultados.get(i+8).put((Integer) snapshot.child(String.valueOf(j)).child("Final").child(String.valueOf(i)).getValue(), resultados.get(i).get(snapshot.child(String.valueOf(j)).child("Final").child(String.valueOf(i)).getValue()) + 1);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        /*dr.get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            } else {
                DataSnapshot ds = task.getResult();*/

        return resultados;
    }


    public static void deletePlayer(String nlobby, int id) {
        FirebaseDatabase fd = FirebaseDatabase.getInstance();
        DatabaseReference dr = fd.getReference().child("Partida");
        dr.child(nlobby).child(String.valueOf(id)).removeValue();
    }


}

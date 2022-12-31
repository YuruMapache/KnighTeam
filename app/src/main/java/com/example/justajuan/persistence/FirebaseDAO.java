package com.example.justajuan.persistence;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.justajuan.model.Caballero;
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
        if(key!= null) {
            DatabaseReference mUser = mDatabase.child("user").child(key);
            mUser.child("name").child(id).setValue(name);
            mUser.child("email").child(id).setValue(email);
            mUser.child("password").child(id).setValue(password);
        }
    }

    public static void setMateriales(String nlobby, ArrayList<Material> materiales) {
        FirebaseDatabase fd = FirebaseDatabase.getInstance();
        DatabaseReference dr = fd.getReference().child("Materiales").child(nlobby);

        dr.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

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
                Integer salud = (Integer) ds.child("Salud").getValue();
                Integer ataque = (Integer) ds.child("Ataque").getValue();
                Integer velocidad = (Integer) ds.child("Velocidad").getValue();
                Integer estamina = (Integer) ds.child("Estamina").getValue();
                Integer tiempo = (Integer) ds.child("Tiempo").getValue();

                if (salud != null && ataque != null && velocidad != null && estamina != null && tiempo != null) {
                    obj.setSalud(salud);
                    obj.setAtaque(ataque);
                    obj.setVelocidad(velocidad);
                    obj.setEstamina(estamina);
                    obj.setTiempo(tiempo);
                }
                //Obtener lista de precios
                Map<String, Integer> costes = new HashMap<>();
                for (DataSnapshot snapshot : ds.child("Precio").getChildren()) {
                    costes.put(snapshot.getKey(), (Integer) snapshot.getValue());
                }
                obj.setPrecio(costes);
            }
        });
        return obj;
    }

    /*public static void setForm(int nlobby, int user, Formulario form, String tipo) {
        FirebaseDatabase fd = FirebaseDatabase.getInstance();
        DatabaseReference dr = fd.getReference().child("Cuestionarios").child(String.valueOf(nlobby)).child(String.valueOf(user)).child(tipo);
        Map preguntas = form.getPreguntas();
        preguntas.forEach(
                (key, value)
                        -> dr.child(String.valueOf(key)).setValue(value)
        );
    }*/


    public static void deletePlayer(int nlobby, int id) {
        FirebaseDatabase fd = FirebaseDatabase.getInstance();
        DatabaseReference dr = fd.getReference().child("Partida");
        dr.child(String.valueOf(nlobby)).child(String.valueOf(id)).removeValue();
    }


}

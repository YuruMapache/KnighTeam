package com.example.justajuan.persistence;

import android.app.Activity;
import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.justajuan.model.Rol;
import com.example.justajuan.model.Sesion;
import com.example.justajuan.model.User;
import com.google.firebase.FirebaseException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

    public static void setPlayer(String nlobby, User user) {
        FirebaseDatabase fd = FirebaseDatabase.getInstance();
        DatabaseReference dr = fd.getReference().child("Partida").child(nlobby);

        dr.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Sesion sesion = Sesion.getInstance();

                while (sesion.getRol() == null) {
                    String tmp = String.valueOf((int) (Math.random()*5 + 1));
                    if (!snapshot.hasChild(tmp)) {
                        user.setRol(Rol.values()[Integer.parseInt(tmp)-1]);
                        dr.child(tmp).setValue(user);
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


    public static void deletePlayer(String nlobby, String id) {
        FirebaseDatabase fd = FirebaseDatabase.getInstance();
        DatabaseReference dr = fd.getReference().child("Partida");
        dr.child(nlobby).child(String.valueOf(id)).removeValue();
    }


}

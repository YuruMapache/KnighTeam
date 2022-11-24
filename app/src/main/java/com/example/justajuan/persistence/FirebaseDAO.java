package com.example.justajuan.persistence;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseDAO {
    private String sala;

    public static void getConsumable(String id){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("user").child(id).child("veneno").getDatabase();
    }

    public static void setUser(String id, String name, String email, String password) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        String key = mDatabase.child("user").push().getKey();
        DatabaseReference mUser= mDatabase.child("user").child(key);
        mUser.child("name").child(id).setValue(name);
        mUser.child("email").child(id).setValue(email);
        mUser.child("password").child(id).setValue(password);
    }

    public static void setPlayer(int nlobby){
        FirebaseDatabase fd = FirebaseDatabase.getInstance();
        boolean ok = false;
        int tmp;
        while(!ok){
            tmp = (int) ((Math.random() * (5 - 1)) + 1);
            /* ref = fd.database().ref("users/ada");
            ref.once("value")
                    .then(function(snapshot) {
                var hasName = snapshot.hasChild("name"); // true
                var hasAge = snapshot.hasChild("age"); // false
            });*/
        }
    }

    public static void deletePlayer(int nlobby, String id){
        FirebaseDatabase fd = FirebaseDatabase.getInstance();
        DatabaseReference dr = fd.getReference().child("Partida");
        dr.child(String.valueOf(nlobby)).child(String.valueOf(id)).removeValue();
    }



}

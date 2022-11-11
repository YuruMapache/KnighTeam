package com.example.justajuan.persistence;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseDAO {
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
}

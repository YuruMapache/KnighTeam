package com.example.justajuan.persistence;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SalaDAO {
    public static void setListo(int numlobby){
        FirebaseDatabase fd = FirebaseDatabase.getInstance();
        DatabaseReference dr = fd.getReference().child("Partida").child(String.valueOf(numlobby)).child("1").child("Listo");
        dr.get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            } else {
                DataSnapshot ds = task.getResult();
                Log.d("Comprobaciones", String.valueOf(ds.getValue()));
                Log.d("Comprobaciones sumado", String.valueOf(Integer.valueOf(String.valueOf(ds.getValue()))+ 1));
                //dr.setValue(Integer.valueOf(String.valueOf(ds.getValue()))+ 1);
                dr.setValue(3);
                //Log.d("Comprobaciones", String.valueOf(dr.get().getResult()));
            }
        });
    }
}

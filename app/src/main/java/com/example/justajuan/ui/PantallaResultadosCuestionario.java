package com.example.justajuan.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.justajuan.R;
import com.example.justajuan.model.Cuestionario;
import com.example.justajuan.model.Material;
import com.example.justajuan.model.Sesion;
import com.example.justajuan.persistence.AdaptadorCuestionario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class PantallaResultadosCuestionario extends AppCompatActivity{

    private FirebaseDatabase fd;
    private DatabaseReference dr;
    private ArrayList<Cuestionario> listaCuestionario;
    private GridView preguntas;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_resultados_cuestionario);

        fd = FirebaseDatabase.getInstance();
        dr = fd.getReference().child("Cuestionarios").child(String.valueOf(Sesion.getInstance().getNumLobby()));

        preguntas = findViewById(R.id.ui_ListaPreguntas);

        dr.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Cuestionario cuestionario = postSnapshot.getValue(Cuestionario.class);
                    listaCuestionario.add(cuestionario);
                }

                AdaptadorCuestionario adaptadorCuestionario = new AdaptadorCuestionario(PantallaResultadosCuestionario.this, R.layout.gridview_preguntas, listaCuestionario);

                preguntas.setAdapter(adaptadorCuestionario);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this, R.style.AlertDialogTheme)
                .setMessage("¿Quieres cerrar la app?")

                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finishAffinity();
                        System.exit(0);
                    }
                })

                .setNegativeButton("No", null)
                .show();
    }
}

package com.example.justajuan.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.justajuan.R;
import com.example.justajuan.model.Sesion;
import com.example.justajuan.persistence.FirebaseDAO;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class PantallaResultadosCuestionario extends AppCompatActivity{

    FirebaseDatabase fd = FirebaseDatabase.getInstance();
    DatabaseReference dr = fd.getReference().child("Cuestionarios").child(String.valueOf(Sesion.getInstance().getNumLobby()));
    Map<Integer, Map<Integer, Integer>> resultados = new HashMap<>();
    LinearLayout bloque, respuestas, columna;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_resultados_cuestionario);

        LinearLayout llpreguntas = findViewById(R.id.LinearLayoutPreguntas);

        dr.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (int j = 1; j < 6; j++) {

                    Map map = new HashMap<Integer, Integer>();

                    for (int i = 0; i < 8; i++) {
                        map.put(i, snapshot.child(String.valueOf(j)).child("Intermedio").child(String.valueOf(i)).getValue());
                        map.put(i+8, snapshot.child(String.valueOf(j)).child("Final").child(String.valueOf(i)).getValue());
                    }
                    resultados.put(j, map);
                }

                for(int i = 0; i<16; i++){
                    bloque = (LinearLayout) llpreguntas.getChildAt(i+1);
                    respuestas = (LinearLayout) bloque.getChildAt(1);
                    for(int j=0; j<5; j++){
                        columna = (LinearLayout) respuestas.getChildAt(j);
                        ((TextView) columna.getChildAt(1)).setText(resultados.get(i).get(j));
                    }
                }

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

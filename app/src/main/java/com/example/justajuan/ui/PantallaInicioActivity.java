package com.example.justajuan.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.justajuan.R;
import com.example.justajuan.model.Sesion;
import com.example.justajuan.model.User;
import com.example.justajuan.persistence.SubidaObjetosBD;

import java.util.Objects;

public class PantallaInicioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pantalla_inicio);



        final Button botonCrearPartida = findViewById(R.id.botonCrearPartida);
        final Button botonUnirsePartida = findViewById(R.id.botonUnirsePartida);
        final Button botonTutorial = findViewById(R.id.botonTutorial);

        botonCrearPartida.setOnClickListener(view -> {
            // Pasa a la ventana de seleccion de plantillas.
            startActivity(new Intent(PantallaInicioActivity.this, PantallaPlantillasActivity.class));
        });

        botonUnirsePartida.setOnClickListener(view -> {
            // Pasa a la ventana de unirse a la sala creada
            startActivity(new Intent(PantallaInicioActivity.this, PantallaUnirsePartida.class));
        });

        botonTutorial.setOnClickListener(view -> {
            SubidaObjetosBD.setObjetos();
            //Pasa a las ventanas del tutorial
            startActivity(new Intent(PantallaInicioActivity.this, Tutorial.class));
        });

    }

    @Override
    public void onBackPressed(){
        new AlertDialog.Builder(this, R.style.AlertDialogTheme)
                .setMessage("¿Quieres cerrar la app?")

                .setPositiveButton("Si", (dialog, which) -> {
                    finishAffinity();
                    System.exit(0);
                })

                .setNegativeButton("No", null)
                .show();
    }
}
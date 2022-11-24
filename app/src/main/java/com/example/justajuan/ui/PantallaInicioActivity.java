package com.example.justajuan.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.justajuan.R;

public class PantallaInicioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pantalla_inicio);

        final Button botonCrearPartida = findViewById(R.id.botonCrearPartida);
        final Button botonUnirsePartida = findViewById(R.id.botonUnirsePartida);
        final Button botonTutorial = findViewById(R.id.botonTutorial);


        botonCrearPartida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Pasa a la ventana de seleccion de plantillas.
                startActivity(new Intent(PantallaInicioActivity.this, PantallaPlantillasActivity.class));
            }
        });

        botonUnirsePartida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Pasa a la ventana de unirse a la sala creada
                startActivity(new Intent(PantallaInicioActivity.this, PantallaUnirsePartida.class));
            }
        });

        botonTutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Pasa a las ventanas del tutorial
                startActivity(new Intent(PantallaInicioActivity.this, Tutorial1.class));
            }
        });
    }

    @Override
    public void onBackPressed(){
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
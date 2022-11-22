package com.example.justajuan;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

public class PantallaPlantillasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pantalla_plantillas);

        final Button botonSiguiente = findViewById(R.id.botonSiguiente);
        final ImageButton botonPlantSencilla = findViewById(R.id.botonInfoSencilla);
        final ImageButton botonPlantDificil = findViewById(R.id.botonInfoDificil);
        final ImageButton botonPlantEmpatia = findViewById(R.id.botonInfoEmpatia);
        final ImageButton botonPlantComunicacion = findViewById(R.id.botonInfoComunicacion);

        botonSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Pasa a la ventana de creacion de la partida
                startActivity(new Intent(PantallaPlantillasActivity.this, PantallaCreacionPartidaActivity.class));
            }
        });

        botonPlantSencilla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PantallaPlantillasActivity.this);

                builder.setCancelable(true);
                builder.setTitle("PLANTILLA SENCILLA");
                builder.setMessage("DURACIÓN: 1 HORA 3O MIN (TORNEO Y PAUSAS CORRESPONDIENTES)\n" +
                                   "Nº JUSTAS: 10\n" +
                                   "TIEMPO ENTRE JUSTAS: 6 MIN\n" +
                        "Útil para equipos que aún no hayan trabajado juntos o que no tengan experiencia con el juego.\n" +
                        "Se puede jugar con los jugadores presentes o de manera telemática.\n" +
                        "Para comprobar de forma más exhaustiva su capacidad de comunicación conviene la opción telemática.");

                builder.setPositiveButton("Entendido!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
            }
        });



    }
}
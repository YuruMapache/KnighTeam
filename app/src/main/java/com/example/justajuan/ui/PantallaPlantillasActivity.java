package com.example.justajuan.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.justajuan.R;

import java.util.Objects;

public class PantallaPlantillasActivity extends AppCompatActivity {

    private RadioGroup plantillas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pantalla_plantillas);

        Button botonSiguiente = findViewById(R.id.botonSiguiente);
        ImageButton botonPlantSencilla = findViewById(R.id.botonInfoSencilla);
        /*ImageButton botonPlantDificil = findViewById(R.id.botonInfoDificil);
        ImageButton botonPlantEmpatia = findViewById(R.id.botonInfoEmpatia);
        ImageButton botonPlantComunicacion = findViewById(R.id.botonInfoComunicacion);*/
        plantillas = findViewById(R.id.radioGroupPlantillas);

        botonSiguiente.setOnClickListener(view -> {

            if(plantillas.getCheckedRadioButtonId() == -1) {
                Toast.makeText(getApplicationContext(), "Seleccione una plantilla", Toast.LENGTH_SHORT).show();
                return;
            }

            // Pasa a la ventana de creacion de la partida
            startActivity(new Intent(PantallaPlantillasActivity.this, PantallaCreacionPartidaActivity.class));
        });

        botonPlantSencilla.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(PantallaPlantillasActivity.this, R.style.AlertDialogTheme);

            builder.setCancelable(true);
            builder.setTitle("PLANTILLA SENCILLA");
            builder.setMessage("DURACIÓN: 1 HORA 3O MIN (TORNEO Y PAUSAS CORRESPONDIENTES)\n" +
                               "Nº JUSTAS: 10\n" +
                               "TIEMPO ENTRE JUSTAS: 6 MIN\n" +
                    "Útil para equipos que aún no hayan trabajado juntos o que no tengan experiencia con el juego.\n" +
                    "Se puede jugar con los jugadores presentes o de manera telemática.\n" +
                    "Para comprobar de forma más exhaustiva su capacidad de comunicación conviene la opción telemática.");

            builder.setPositiveButton("Entendido!", (dialogInterface, i) -> dialogInterface.dismiss());
            builder.show();
        });



    }
}
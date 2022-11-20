package com.example.justajuan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

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


        botonCrearPartida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(PantallaInicioActivity.this, PantallaPlantillasActivity.class));
                // Pasa a la ventana seleccion de plantillas.
            }
        });

        botonUnirsePartida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(PantallaInicioActivity.this, Login.class));
                // Pasa a la ventana seleccion de plantillas.
            }
        });
    }
}
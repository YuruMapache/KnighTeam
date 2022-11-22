package com.example.justajuan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class Tutorial4 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_tutorial4);

        final Button botonAtras = findViewById(R.id.botonAtras);
        final Button botonTerminar = findViewById(R.id.botonTerminar);


        botonAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Tutorial4.this, Tutorial3.class));
                // Pasa a la ventana seleccion de plantillas.
            }
        });

        botonTerminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Tutorial4.this, MainActivity.class));
                // Pasa a la ventana seleccion de plantillas.
            }
        });
    }
}
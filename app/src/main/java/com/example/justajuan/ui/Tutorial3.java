package com.example.justajuan.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.justajuan.R;

public class Tutorial3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_tutorial3);

        final Button botonAtras = findViewById(R.id.botonAtras);
        final Button botonSiguiente = findViewById(R.id.botonSiguiente);


        botonAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Tutorial3.this, Tutorial2.class));
                // Pasa a la ventana seleccion de plantillas.
            }
        });

        botonSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Tutorial3.this, Tutorial4.class));
                // Pasa a la ventana seleccion de plantillas.
            }
        });
    }
}
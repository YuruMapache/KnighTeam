package com.example.justajuan.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.example.justajuan.R;

import java.util.Objects;

public class PantallaSplashInicioActivity extends AppCompatActivity {

    // Duración en milisegundos que se mostrará el splash
    private static final int DURACION_SPLASH = 1000; // 1 segundo

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pantalla_splash_inicio);

        new Handler().postDelayed(() -> {
            // Cuando pasen 1 segundo, pasamos a la actividad principal de la aplicación
            Intent intent = new Intent(PantallaSplashInicioActivity.this, PantallaInicioActivity.class);
            startActivity(intent);
            finish();
        }, DURACION_SPLASH);
    }
}
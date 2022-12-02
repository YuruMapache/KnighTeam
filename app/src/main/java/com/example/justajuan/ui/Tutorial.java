package com.example.justajuan.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.justajuan.R;

public class Tutorial extends AppCompatActivity {

    private AppCompatButton botonDesplAcciones;
    private AppCompatButton botonDesplTienda;
    private AppCompatButton botonDesplInventario;
    private AppCompatButton botonDesplDiario;
    private LinearLayout layoutTiempo;
    private LinearLayout layoutItems;
    private FrameLayout layoutRecursos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_tutorial);


        botonDesplAcciones = findViewById(R.id.botonAcciones);
        botonDesplTienda = findViewById(R.id.botonTienda);
        botonDesplDiario = findViewById(R.id.botonDiario);
        botonDesplInventario = findViewById(R.id.botonInventario);
        layoutTiempo = findViewById(R.id.linearLayoutTime);
        layoutItems = findViewById(R.id.linearLayoutItems);
        layoutRecursos = findViewById(R.id.frameLayout);


        layoutTiempo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog acciones = new Dialog(Tutorial.this);
                acciones.setContentView(R.layout.pop_up_tutorial2);
                acciones.setCancelable(true);
                acciones.show();
            }
        });

        layoutRecursos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog acciones = new Dialog(Tutorial.this);
                acciones.setContentView(R.layout.pop_up_tutorial1);
                acciones.setCancelable(true);
                acciones.show();
            }
        });

        layoutItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog acciones = new Dialog(Tutorial.this);
                acciones.setContentView(R.layout.pop_up_tutorial3);
                acciones.setCancelable(true);
                acciones.show();
            }
        });

        botonDesplAcciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog acciones = new Dialog(Tutorial.this);
                acciones.setContentView(R.layout.pop_up_tutorial4);
                acciones.setCancelable(true);
                acciones.show();
            }
        });

        botonDesplTienda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog acciones = new Dialog(Tutorial.this);
                acciones.setContentView(R.layout.pop_up_tutorial4);
                acciones.setCancelable(true);
                acciones.show();
            }
        });

        botonDesplDiario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog acciones = new Dialog(Tutorial.this);
                acciones.setContentView(R.layout.pop_up_tutorial4);
                acciones.setCancelable(true);
                acciones.show();
            }
        });

        botonDesplInventario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog acciones = new Dialog(Tutorial.this);
                acciones.setContentView(R.layout.pop_up_tutorial4);
                acciones.setCancelable(true);
                acciones.show();
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
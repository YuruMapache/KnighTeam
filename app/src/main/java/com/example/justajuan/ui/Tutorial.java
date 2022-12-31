package com.example.justajuan.ui;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.justajuan.R;

import java.util.Objects;

public class Tutorial extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_tutorial);


        AppCompatButton botonDesplAcciones = findViewById(R.id.botonAcciones);
        AppCompatButton botonDesplTienda = findViewById(R.id.botonTienda);
        AppCompatButton botonDesplDiario = findViewById(R.id.botonDiario);
        AppCompatButton botonDesplInventario = findViewById(R.id.botonInventario);
        LinearLayout layoutTiempo = findViewById(R.id.linearLayoutTime);
        LinearLayout layoutItems = findViewById(R.id.linearLayoutItems);
        FrameLayout layoutRecursos = findViewById(R.id.frameLayout);

/*
        layoutTiempo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Tutorial.this, R.style.PopUpTutorialTheme);

                builder.setCancelable(true);
                builder.setTitle("TIEMPO DISPONIBLE");
                builder.setMessage("En esta parte de la pantalla tendrás siempre disponible el tiempo restante para " +
                        "realizar tus diversas acciones.\n" + "Esto se ve reflejado como tiempo hasta vuestra próxima justa.");

                builder.setPositiveButton("¡Entendido!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
            }
        });
*/
        layoutTiempo.setOnClickListener(view -> {
            final Dialog acciones = new Dialog(Tutorial.this);
            acciones.setContentView(R.layout.pop_up_tutorial2);
            acciones.setCancelable(true);
            acciones.show();
        });

        /*
        layoutRecursos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Tutorial.this, R.style.PopUpTutorialTheme);

                builder.setCancelable(true);
                builder.setTitle("RECURSOS");
                builder.setMessage("Aquí podras ver los materiales disponibles,así como la cantidad que dispones de cada uno de ellos.\n"
                        + "El personaje te servirá como recordatorio de tu rol y además al final de cada toma de decisiones " +
                        "te dará los resultados.");

                builder.setPositiveButton("¡Entendido!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
            }
        });
        */

        layoutRecursos.setOnClickListener(view -> {
            final Dialog acciones = new Dialog(Tutorial.this);
            acciones.setContentView(R.layout.pop_up_tutorial1);
            acciones.setCancelable(true);
            acciones.show();
        });

        /*
        layoutItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Tutorial.this, R.style.PopUpTutorialTheme);

                builder.setCancelable(true);
                builder.setTitle("ITEMS");
                builder.setMessage("Desde aquí podrás ver las acciones que ya has comenzado y que están " +
                        "actualmente en progreso, además del tiempo hasta su finalización.");

                builder.setPositiveButton("¡Entendido!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
            }
        });
        */

        layoutItems.setOnClickListener(view -> {
            final Dialog acciones = new Dialog(Tutorial.this);
            acciones.setContentView(R.layout.pop_up_tutorial3);
            acciones.setCancelable(true);
            acciones.show();
        });

        /*
        botonDesplAcciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Tutorial.this, R.style.PopUpTutorialTheme);

                builder.setCancelable(true);
                builder.setTitle("ACCIONES");
                builder.setMessage("El botón Acciones te permitirá ver una lista de actividades disponibles para tu rol");

                builder.setPositiveButton("¡Entendido!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
            }
        });
        */

        botonDesplAcciones.setOnClickListener(view -> {
            final Dialog acciones = new Dialog(Tutorial.this);
            acciones.setContentView(R.layout.pop_up_tutorial4);
            acciones.setCancelable(true);
            acciones.show();
        });

        /*
        botonDesplTienda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Tutorial.this, R.style.PopUpTutorialTheme);

                builder.setCancelable(true);
                builder.setTitle("TIENDA");
                builder.setMessage("Con Comprar podrás acceder a un apartado para intercambiar " +
                        "diversos objetos disponibles para tu rol a cambio de un módico precio, más fácil que craftearlos a mano.");

                builder.setPositiveButton("¡Entendido!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
            }
        });
        */

        botonDesplTienda.setOnClickListener(view -> {
            final Dialog acciones = new Dialog(Tutorial.this);
            acciones.setContentView(R.layout.pop_up_tutorial4);
            acciones.setCancelable(true);
            acciones.show();
        });

        /*
        botonDesplDiario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Tutorial.this, R.style.PopUpTutorialTheme);

                builder.setCancelable(true);
                builder.setTitle("DIARIO");
                builder.setMessage("Desde Diario verás un resumen de los mensajes recibidos hasta el momento " +
                        "de los resultados de cada justa");

                builder.setPositiveButton("¡Entendido!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
            }
        });
        */

        botonDesplDiario.setOnClickListener(view -> {
            final Dialog acciones = new Dialog(Tutorial.this);
            acciones.setContentView(R.layout.pop_up_tutorial4);
            acciones.setCancelable(true);
            acciones.show();
        });

        /*
        botonDesplInventario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Tutorial.this, R.style.PopUpTutorialTheme);

                builder.setCancelable(true);
                builder.setTitle("INVENTARIO");
                builder.setMessage("En Inventario podrás ver todo lo que tienes almacenado y disponible.");

                builder.setPositiveButton("¡Entendido!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
            }
        });
        */

        botonDesplInventario.setOnClickListener(view -> {
            final Dialog acciones = new Dialog(Tutorial.this);
            acciones.setContentView(R.layout.pop_up_tutorial4);
            acciones.setCancelable(true);
            acciones.show();
        });


    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(Tutorial.this, PantallaInicioActivity.class);
        startActivity(i);
    }
}
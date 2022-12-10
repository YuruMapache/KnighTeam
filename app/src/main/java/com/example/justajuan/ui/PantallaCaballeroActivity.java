package com.example.justajuan.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.justajuan.R;
import com.example.justajuan.model.Material;
import com.example.justajuan.model.Sesion;
import com.example.justajuan.model.Time;
import com.example.justajuan.persistence.AdaptadorMateriales;
import com.example.justajuan.persistence.FirebaseDAO;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class PantallaCaballeroActivity extends AppCompatActivity {

    private Time glblTimer;      // Textview del tiempo restante del temporizador
    private AppCompatButton botonDesplAcciones;
    private AppCompatButton botonDesplTienda;
    private AppCompatButton botonDesplInventario;
    private AppCompatButton botonDesplDiario;
    private ArrayList<Material> listaMateriales= new ArrayList<>();
    private GridView vistaLista;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private AppCompatButton botonCombate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pantalla_caballero);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Materiales").child(Sesion.getNumLobby());


        vistaLista=(GridView) findViewById(R.id.textViewResume);

        AdaptadorMateriales adaptador= new AdaptadorMateriales(this,R.layout.activity_gridview_materiales,listaMateriales);

        botonCombate = findViewById(R.id.botonCombate);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaMateriales.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Material material = postSnapshot.getValue(Material.class);
                    if (material.getRol().contains("Caballero")) {
                        listaMateriales.add(material);
                    }
                }
                adaptador.setListaMateriales(listaMateriales);
                vistaLista.setAdapter(adaptador);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        TextView glblTimer = findViewById(R.id.timerTextView);
        new CountDownTimer(6000,1000) {

            public void onTick(long millisUntilFinished) {
                int minutes = (int) millisUntilFinished / 60000;
                int seconds = (int) millisUntilFinished % 60000 / 1000;
                String timeLeftText;
                timeLeftText = "" + minutes;
                timeLeftText += ":";
                if (seconds < 10) {
                    timeLeftText += "0";
                }
                timeLeftText += seconds;
                glblTimer.setText(timeLeftText);
            }

            public void onFinish() {
                startActivity(new Intent(PantallaCaballeroActivity.this, ResultadosCaballero.class));
                PantallaCaballeroActivity.this.finish();
            }

        }.start();

        botonDesplAcciones = findViewById(R.id.botonAcciones);
        botonDesplTienda = findViewById(R.id.botonTienda);
        botonDesplDiario = findViewById(R.id.botonDiario);
        botonDesplInventario = findViewById(R.id.botonInventario);

        botonDesplAcciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog acciones = new Dialog(PantallaCaballeroActivity.this);
                acciones.setContentView(R.layout.pop_up_acciones_alpha);
                acciones.setCancelable(true);
                acciones.show();
            }
        });

        botonDesplTienda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog acciones = new Dialog(PantallaCaballeroActivity.this);
                acciones.setContentView(R.layout.pop_up_tienda_alpha);
                acciones.setCancelable(true);
                acciones.show();
            }
        });

        botonDesplDiario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog acciones = new Dialog(PantallaCaballeroActivity.this);
                acciones.setContentView(R.layout.pop_up_diario);
                acciones.setCancelable(true);
                acciones.show();
            }
        });

        botonDesplInventario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog acciones = new Dialog(PantallaCaballeroActivity.this);
                acciones.setContentView(R.layout.pop_up_inventario);
                acciones.setCancelable(true);
                acciones.show();
            }
        });

        botonCombate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                botonCombate.setClickable(false);
                Sesion sesion = Sesion.getInstance();
                firebaseDatabase.getReference().child(sesion.getNumLobby()).child("1").

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
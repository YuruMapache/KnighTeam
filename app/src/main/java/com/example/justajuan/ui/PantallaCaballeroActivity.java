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
import com.example.justajuan.model.Caballero;
import com.example.justajuan.model.Enemigo;
import com.example.justajuan.model.Material;
import com.example.justajuan.model.Objeto;
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
    private Caballero caballero;

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

        //caballero=getCaballero();

        firebaseDatabase.getReference().child("Caballero").child(Sesion.getNumLobby()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                caballero=(Caballero) snapshot.getValue(Caballero.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        vistaLista=(GridView) findViewById(R.id.textRecursos);

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

                algoritmo(1);
                //startActivity(new Intent(PantallaCaballeroActivity.this, ResultadosCaballero.class));
                //PantallaCaballeroActivity.this.finish();
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


    public Caballero getCaballero(){
        Bundle extras= getIntent().getExtras();
        if (extras!=null){
            return (Caballero) extras.get("Caballero");
        }
        return null;
    }


    public void algoritmo(int nRonda){


        firebaseDatabase.getReference().child("Enemigos").child(String.valueOf(nRonda)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                   Enemigo enemigo= snapshot.getValue(Enemigo.class);
                   if (caballero.getVelocidadAtaque()>enemigo.getVelocidadAtaque()) {
                       while (caballero.getSalud() <= 0 || enemigo.getSalud() <= 0) {
                            enemigo.setSalud(enemigo.getSalud()-caballero.getAtaque());
                            if (enemigo.getSalud()<=0){
                                break;
                            }
                            caballero.setSalud(caballero.getSalud()-enemigo.getAtaque());
                       }
                   }
                   else{
                       while (caballero.getSalud() <= 0 || enemigo.getSalud() <= 0) {

                           caballero.setSalud(caballero.getSalud()-enemigo.getAtaque());
                           if (caballero.getSalud()<=0){
                               break;
                           }

                           enemigo.setSalud(enemigo.getSalud()-caballero.getAtaque());
                       }
                   }
                   ArrayList<Objeto> nuevoEquipado= new ArrayList<Objeto>();
                   for (int i=0; i<caballero.getEquipado().size();i++){
                       if (caballero.getEquipado().get(i).isEsConsumible()==false){
                           nuevoEquipado.add(caballero.getEquipado().get(i));
                       }
                   }
                   caballero.setEquipado(nuevoEquipado);
                    Intent i;
                   if (caballero.getSalud()>0){
                       if (nRonda<10) {
                           i = new Intent(PantallaCaballeroActivity.this, ResultadosCaballero.class);
                           i.putExtra("Caballero", caballero);
                           i.putExtra("nRonda", nRonda + 1);
                           for (int j = 0; j < listaMateriales.size(); j++) {
                               if (listaMateriales.get(j).getName().equals("Moneda")) {
                                   listaMateriales.get(j).setCantidad(listaMateriales.get(j).getCantidad() + enemigo.getMonedasGanas());
                                   FirebaseDAO.setMateriales(Sesion.getNumLobby(), listaMateriales);
                               }
                           }
                       }
                       else{
                           i= i = new Intent(PantallaCaballeroActivity.this, PantallaVictoria.class);
                       }

                   }
                   else{
                       i= new Intent(PantallaCaballeroActivity.this, PantallaDerrota.class);

                   }
                startActivity(i);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





    }


}
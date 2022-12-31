package com.example.justajuan.ui;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.justajuan.R;
import com.example.justajuan.adapter.AdaptadorAcciones;
import com.example.justajuan.adapter.AdaptadorMateriales;
import com.example.justajuan.adapter.AdaptadorProgreso;
import com.example.justajuan.adapter.AdaptadorTienda;
import com.example.justajuan.model.Material;
import com.example.justajuan.model.Objeto;
import com.example.justajuan.model.Sesion;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class PantallaHerreroActivity extends AppCompatActivity {

    private AppCompatButton botonAtras;
    private ArrayList<Material> listaMateriales;
    private GridView vistaLista;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private DatabaseReference partidaReference;
    private AppCompatButton botonCombate;
    private ValueEventListener listenerMateriales;
    private ValueEventListener listenerCombate;
    private ValueEventListener listenerJusta;
    private ArrayList<Objeto> listaObjetos;
    private int numRonda;
    private ArrayList<Objeto> objetosCreandose;
    private long tiempoRonda;
    private Material monedas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pantalla_herrero);

        Sesion sesion = Sesion.getInstance();
        listaMateriales = new ArrayList<>();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Materiales").child(String.valueOf(sesion.getNumLobby()));
        partidaReference = firebaseDatabase.getReference().child("Partida");

        vistaLista = (GridView) findViewById(R.id.textRecursos);

        botonCombate = findViewById(R.id.botonCombate);


        objetosCreandose = getObjetosCreandose();
        if (objetosCreandose == null) {
            objetosCreandose = new ArrayList<>();
        } else {
            for (Objeto i : objetosCreandose) {
                CountDownTimer contador = new CountDownTimer(i.getTiempoQueFalta(), 1000) {

                    @Override
                    public void onTick(long millisUntilFinished) {

                        i.setTiempoQueFalta(millisUntilFinished);

                    }

                    @Override
                    public void onFinish() {

                        i.setContador(null);
                        FirebaseDatabase.getInstance().getReference().child("Inventario").
                                child(String.valueOf(sesion.getNumLobby())).child(i.getNombre()).setValue(i);
                        objetosCreandose.remove(i);
                    }
                }.start();
                i.setContador(contador);
            }
        }

        AdaptadorProgreso adaptadorProgreso = new AdaptadorProgreso(this, R.layout.gridview_recursos_feudo, objetosCreandose);

        TextView glblTimer = findViewById(R.id.timerTextView);
        new CountDownTimer(360000, 1000) {

            public void onTick(long millisUntilFinished) {
                tiempoRonda = millisUntilFinished;
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

                GridView ui_listaObjetos = (GridView) findViewById(R.id.recursosFeudoGridView);
                ui_listaObjetos.setAdapter(adaptadorProgreso);
            }

            public void onFinish() {

            }


        }.start();


        AppCompatButton botonDesplAcciones = findViewById(R.id.botonAcciones);
        AppCompatButton botonDesplTienda = findViewById(R.id.botonTienda);
        AppCompatButton botonDesplDiario = findViewById(R.id.botonDiario);
        AppCompatButton botonDesplInventario = findViewById(R.id.botonInventario);

        botonDesplAcciones.setOnClickListener(view -> {
            final Dialog acciones = new Dialog(PantallaHerreroActivity.this);
            acciones.setContentView(R.layout.pop_up_acciones);
            acciones.setCancelable(true);
            acciones.show();
            listaObjetos = getListaObjetos();

            GridView ui_listaObjetos = (GridView) acciones.findViewById(R.id.ui_ListaObjetos);
            AdaptadorAcciones adaptadorAcciones = new AdaptadorAcciones(acciones.getContext(), R.layout.pop_up_acciones, listaObjetos, sesion.getNumLobby(), objetosCreandose, listaMateriales);
            ui_listaObjetos.setAdapter(adaptadorAcciones);

            botonAtras = acciones.findViewById(R.id.botonAtras);
            botonAtras.setOnClickListener(view1 -> acciones.hide());
        });

        botonDesplTienda.setOnClickListener(view -> {
            final Dialog acciones = new Dialog(PantallaHerreroActivity.this);
            acciones.setContentView(R.layout.pop_up_tienda);
            acciones.setCancelable(true);
            acciones.show();
            GridView gridViewTienda = (GridView) acciones.findViewById(R.id.gridView_Tienda);
            AdaptadorTienda adaptadorTienda = new AdaptadorTienda(acciones.getContext(), R.layout.gridview_tienda, listaMateriales, sesion.getNumLobby(), monedas);
            gridViewTienda.setAdapter(adaptadorTienda);

            botonAtras = acciones.findViewById(R.id.botonAtras);
            botonAtras.setOnClickListener(view12 -> acciones.hide());
        });

        botonDesplDiario.setOnClickListener(view -> {
            final Dialog acciones = new Dialog(PantallaHerreroActivity.this);
            acciones.setContentView(R.layout.pop_up_diario);
            acciones.setCancelable(true);
            acciones.show();

            firebaseDatabase.getReference().child("Diario").child(String.valueOf(sesion.getNumLobby())).child("Herrero").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    TextView diarioAcum = (TextView) acciones.findViewById(R.id.infoAcumulada);
                    diarioAcum.setText(snapshot.child("ResultadosAcumulados").getValue(String.class));

                    botonAtras = acciones.findViewById(R.id.botonAtras);
                    botonAtras.setOnClickListener(view13 -> acciones.hide());

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {


                }
            });

        });

        botonDesplInventario.setOnClickListener(view -> {
            final Dialog acciones = new Dialog(PantallaHerreroActivity.this);
            acciones.setContentView(R.layout.pop_up_inventario);
            acciones.setCancelable(true);
            acciones.show();

            botonAtras = acciones.findViewById(R.id.botonAtras);
            botonAtras.setOnClickListener(view14 -> acciones.hide());
        });

        partidaReference.child(String.valueOf(sesion.getNumLobby())).child("1").child("numRonda").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                numRonda = snapshot.getValue(Integer.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        Sesion sesion = Sesion.getInstance();
        AdaptadorMateriales adaptador = new AdaptadorMateriales(this, R.layout.activity_gridview_materiales, listaMateriales);

        listenerMateriales = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaMateriales.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Material material = postSnapshot.getValue(Material.class);
                    if (Objects.requireNonNull(material).getRol().contains("Herrero")) {
                        listaMateriales.add(material);
                    }
                    if (material.getName().equals("Moneda")) {
                        monedas = material;
                    }
                }
                adaptador.setListaMateriales(listaMateriales);
                vistaLista.setAdapter(adaptador);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        listenerCombate = partidaReference.child(String.valueOf(sesion.getNumLobby())).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                int cont = 0;
                boolean boton_press = false;
                for (int i = 1; i < 6; i++) {
                    Integer tmp = snapshot.child(String.valueOf(i)).child("combateListo").getValue(Integer.class);
                    if (tmp != null) {
                        if (tmp == 1) {
                            if (i == sesion.getRol().ordinal() + 1) {
                                boton_press = true;
                            }
                            cont++;
                        }
                    }
                }

                if (boton_press) {
                    botonCombate.setText(getString(R.string.boton_combate_pressed, cont));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Usuarios no están listos para combate", Toast.LENGTH_SHORT).show();
            }
        });

        listenerJusta = partidaReference.child(String.valueOf(sesion.getNumLobby())).child("1").child("justaGanada").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.getValue(Integer.class) != 0) {

                    Intent i;
                    if (snapshot.getValue(Integer.class) == 1) {

                        for (Objeto o : objetosCreandose) {
                            long diferenciaTiempo = o.getTiempoQueFalta() - tiempoRonda;
                            if (diferenciaTiempo <= 0) {
                                o.setContador(null);
                                FirebaseDatabase.getInstance().getReference().child("Inventario").
                                        child(String.valueOf(sesion.getNumLobby())).child(o.getNombre()).setValue(o);
                            } else {
                                o.setTiempoQueFalta(diferenciaTiempo);
                                o.setContador(null);
                            }
                        }


                        if (numRonda != 5) {
                            i = new Intent(PantallaHerreroActivity.this, ResultadosHerrero.class);
                            i.putExtra("listaObjetos", getListaObjetos());
                            i.putExtra("objetosCreandose", objetosCreandose);
                            i.putExtra("nRonda", numRonda);

                        } else {
                            i = new Intent(PantallaHerreroActivity.this, PantallaCuestionario.class);
                            i.putExtra("listaObjetos", getListaObjetos());
                            i.putExtra("objetosCreandose", objetosCreandose);
                            i.putExtra("nRonda", numRonda + 1);
                            i.putExtra("tipo", 0);
                        }
                        startActivity(i);

                    } else {
                        i = new Intent(PantallaHerreroActivity.this, PantallaCuestionario.class);
                        i.putExtra("listaObjetos", getListaObjetos());
                        i.putExtra("objetosCreandose", objetosCreandose);
                        i.putExtra("nRonda", numRonda + 1);
                        i.putExtra("tipo", 1);
                        startActivity(i);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void clickBotonCombate(View view) {
        Sesion sesion = Sesion.getInstance();
        DatabaseReference dr = partidaReference.child(String.valueOf(sesion.getNumLobby())).child(String.valueOf(sesion.getRol().ordinal()+1));
        dr.child("combateListo").setValue(1);
        dr.child("resultadosListos").setValue(0);
    }

    @Override
    protected void onStop() {
        super.onStop();
        databaseReference.removeEventListener(listenerMateriales);
        int numlobby = Sesion.getInstance().getNumLobby();
        partidaReference.child(String.valueOf(numlobby)).removeEventListener(listenerCombate);
        partidaReference.child(String.valueOf(numlobby)).child("1").child("justaGanada").removeEventListener(listenerJusta);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this, R.style.AlertDialogTheme)
                .setMessage("¿Quieres cerrar la app?")

                .setPositiveButton("Si", (dialog, which) -> {
                    finishAffinity();
                    System.exit(0);
                })

                .setNegativeButton("No", null)
                .show();
    }

    public ArrayList<Objeto> getListaObjetos() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            return (ArrayList<Objeto>) extras.getSerializable("listaObjetos");
        }
        return null;
    }

    public ArrayList<Objeto> getObjetosCreandose() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            return (ArrayList<Objeto>) extras.getSerializable("objetosCreandose");
        }
        return null;
    }
}
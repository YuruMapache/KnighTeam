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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.justajuan.R;
import com.example.justajuan.model.Material;
import com.example.justajuan.model.Objeto;
import com.example.justajuan.model.Sesion;
import com.example.justajuan.persistence.AdaptadorAcciones;
import com.example.justajuan.persistence.AdaptadorMateriales;
import com.example.justajuan.persistence.AdaptadorProgreso;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PantallaDruidaActivity extends AppCompatActivity {

    private AppCompatButton botonDesplAcciones;
    private AppCompatButton botonDesplTienda;
    private AppCompatButton botonDesplInventario;
    private AppCompatButton botonDesplDiario;
    private AppCompatButton botonAtras;
    private ArrayList<Material> listaMateriales= new ArrayList<>();
    private GridView vistaLista;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private DatabaseReference partidaReference;
    private AppCompatButton botonCombate;
    private ValueEventListener listenerMateriales;
    private ValueEventListener listenerCombate;
    private int numRonda;
    private ArrayList<Objeto> listaObjetos;
    private ArrayList<Objeto> objetosCreandose;
    private long tiempoRonda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pantalla_druida);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Materiales").child(String.valueOf(Sesion.getNumLobby()));
        partidaReference = firebaseDatabase.getReference().child("Partida");

        vistaLista=(GridView) findViewById(R.id.textRecursos);

        botonCombate = findViewById(R.id.botonCombate);

        objetosCreandose=getObjetosCreandose();
        if (objetosCreandose==null){
            objetosCreandose=new ArrayList<>();
        }
        else {
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
                                child(getCodigoSala()).child(i.getNombre()).setValue(i);
                        objetosCreandose.remove(i);
                    }
                }.start();
                i.setContador(contador);
            }
        }

        AdaptadorProgreso adaptadorProgreso= new AdaptadorProgreso(this,R.layout.gridview_recursos_feudo,objetosCreandose);


        TextView glblTimer = findViewById(R.id.timerTextView);
        new CountDownTimer(360000,1000) {

            public void onTick(long millisUntilFinished) {
                tiempoRonda=millisUntilFinished;
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

                GridView ui_listaObjetos= (GridView) findViewById(R.id.recursosFeudoGridView);
                ui_listaObjetos.setAdapter(adaptadorProgreso);
            }

            public void onFinish() {

                partidaReference.child(getCodigoSala()).child("1").child("justaGanada").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if(snapshot.getValue(Boolean.class) == true) {
                                for (Objeto i: objetosCreandose){
                                    i.getContador().cancel();
                                    i.setContador(null);
                                }
                            if(numRonda != 5) {
                                Intent i = new Intent(PantallaDruidaActivity.this, ResultadosDruida.class);
                                i.putExtra("codigo", getCodigoSala());
                                i.putExtra("listaObjetos", getListaObjetos());
                                i.putExtra("objetosCreandose",objetosCreandose);
                                startActivity(i);

                            } else {
                                Intent i = new Intent(PantallaDruidaActivity.this, PantallaCuestionario.class);
                                i.putExtra("codigo", getCodigoSala());
                                i.putExtra("listaObjetos", getListaObjetos());
                                i.putExtra("objetosCreandose",objetosCreandose);
                                startActivity(i);
                            }
                        } else {
                            Intent i = new Intent(PantallaDruidaActivity.this, PantallaDerrota.class);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

        }.start();


        botonDesplAcciones = findViewById(R.id.botonAcciones);
        botonDesplTienda = findViewById(R.id.botonTienda);
        botonDesplDiario = findViewById(R.id.botonDiario);
        botonDesplInventario = findViewById(R.id.botonInventario);

        botonDesplAcciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog acciones = new Dialog(PantallaDruidaActivity.this);
                acciones.setContentView(R.layout.pop_up_acciones_alpha);
                acciones.setCancelable(true);
                acciones.show();
                listaObjetos=getListaObjetos();

                GridView ui_listaObjetos= (GridView) acciones.findViewById(R.id.ui_ListaObjetos);
                AdaptadorAcciones adaptadorAcciones= new AdaptadorAcciones
                        (acciones.getContext(),R.layout.pop_up_acciones_alpha,listaObjetos,getCodigoSala(),objetosCreandose,listaMateriales);
                ui_listaObjetos.setAdapter(adaptadorAcciones);

                botonAtras = acciones.findViewById(R.id.botonAtras);
                botonAtras.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        acciones.hide();
                    }
                });
            }
        });

        botonDesplTienda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog acciones = new Dialog(PantallaDruidaActivity.this);
                acciones.setContentView(R.layout.pop_up_tienda_alpha);
                acciones.setCancelable(true);
                acciones.show();

                botonAtras = acciones.findViewById(R.id.botonAtras);
                botonAtras.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        acciones.hide();
                    }
                });
            }
        });

        botonDesplDiario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog acciones = new Dialog(PantallaDruidaActivity.this);
                acciones.setContentView(R.layout.pop_up_diario);
                acciones.setCancelable(true);
                acciones.show();

                firebaseDatabase.getReference().child("Diario").child(getCodigoSala()).child("Druida").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        TextView diarioAcum = (TextView) acciones.findViewById(R.id.infoAcumulada);
                        diarioAcum.setText(snapshot.child("ResultadosAcumulados").getValue(String.class));

                        botonAtras = acciones.findViewById(R.id.botonAtras);
                        botonAtras.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                acciones.hide();
                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        botonDesplInventario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog acciones = new Dialog(PantallaDruidaActivity.this);
                acciones.setContentView(R.layout.pop_up_inventario);
                acciones.setCancelable(true);
                acciones.show();

                botonAtras = acciones.findViewById(R.id.botonAtras);
                botonAtras.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        acciones.hide();
                    }
                });
            }
        });

        partidaReference.child(getCodigoSala()).child("1").child("numRonda").addListenerForSingleValueEvent(new ValueEventListener() {
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

        AdaptadorMateriales adaptador = new AdaptadorMateriales(this,R.layout.activity_gridview_materiales,listaMateriales);

        listenerMateriales = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaMateriales.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Material material = postSnapshot.getValue(Material.class);
                    if (material.getRol().contains("Druida")) {
                        listaMateriales.add(material);
                    }
                }
                adaptador.setListaMateriales(listaMateriales);
                vistaLista.setAdapter(adaptador);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error con los materiales", Toast.LENGTH_SHORT).show();
            }
        });

        listenerCombate = partidaReference.child(getCodigoSala()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                int listoCaballero = snapshot.child("1").child("combateListo").getValue(Integer.class);
                int listoHerrero = snapshot.child("2").child("combateListo").getValue(Integer.class);
                int listoMaestroCuadras = snapshot.child("3").child("combateListo").getValue(Integer.class);
                int listoCurandero = snapshot.child("4").child("combateListo").getValue(Integer.class);
                int listoDruida = snapshot.child("5").child("combateListo").getValue(Integer.class);

                int jugadores = (listoCaballero + listoHerrero + listoMaestroCuadras + listoCurandero + listoDruida);

                if(listoDruida == 1) {
                    botonCombate.setText(String.format("COMBATE (%s/5)", jugadores));
                }

                if (listoCaballero == 1 && listoHerrero == 1 && listoMaestroCuadras == 1 && listoCurandero == 1 && listoDruida == 1) {

                    for(Objeto i: objetosCreandose){
                        long diferenciaTiempo= i.getTiempoQueFalta()-tiempoRonda;
                        if (diferenciaTiempo<=0){
                            i.setContador(null);
                            FirebaseDatabase.getInstance().getReference().child("Inventario").
                                    child(getCodigoSala()).child(i.getNombre()).setValue(i);
                        }
                        else{
                            i.setTiempoQueFalta(diferenciaTiempo);
                            i.setContador(null);
                        }
                    }

                    partidaReference.child(getCodigoSala()).child("1").child("justaGanada").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if(snapshot.getValue(Boolean.class) == true) {

                                if(numRonda != 1) {
                                    Intent i = new Intent(PantallaDruidaActivity.this, ResultadosDruida.class);
                                    i.putExtra("codigo", getCodigoSala());
                                    i.putExtra("listaObjetos", getListaObjetos());
                                    i.putExtra("objetosCreandose",objetosCreandose);
                                    i.putExtra("numRonda", numRonda);
                                    startActivity(i);

                                } else {
                                    Intent i = new Intent(PantallaDruidaActivity.this, PantallaCuestionario.class);
                                    i.putExtra("codigo", getCodigoSala());
                                    i.putExtra("listaObjetos", getListaObjetos());
                                    i.putExtra("objetosCreandose",objetosCreandose);
                                    startActivity(i);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Usuarios no están listos para combate", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void clickBotonCombate(View view) {
        partidaReference.child(getCodigoSala()).child("5").child("combateListo").setValue(1);
        partidaReference.child(getCodigoSala()).child("5").child("resultadosListos").setValue(0);
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

    public String getCodigoSala() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            return extras.getString("codigo");
        }
        return null;
    }

    public ArrayList<Objeto> getListaObjetos(){
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            return (ArrayList<Objeto>) extras.getSerializable("listaObjetos");
        }
        return null;
    }

    public ArrayList<Objeto> getObjetosCreandose(){
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            return (ArrayList<Objeto>) extras.getSerializable("objetosCreandose");
        }
        return null;
    }

    @Override
    protected void onStop() {
        super.onStop();
        databaseReference.removeEventListener(listenerMateriales);
        partidaReference.child(getCodigoSala()).removeEventListener(listenerCombate);
    }


}
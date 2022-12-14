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
import com.example.justajuan.model.Caballero;
import com.example.justajuan.model.Enemigo;
import com.example.justajuan.model.Estadistico;
import com.example.justajuan.model.Material;
import com.example.justajuan.model.Objeto;
import com.example.justajuan.model.Sesion;
import com.example.justajuan.persistence.AdaptadorEstadisticas;
import com.example.justajuan.persistence.AdaptadorInventario;
import com.example.justajuan.persistence.AdaptadorMateriales;
import com.example.justajuan.persistence.FirebaseDAO;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PantallaCaballeroActivity extends AppCompatActivity {

    private AppCompatButton botonDesplAcciones;
    private AppCompatButton botonDesplTienda;
    private AppCompatButton botonDesplInventario;
    private AppCompatButton botonDesplDiario;
    private AppCompatButton botonAtras;
    private ArrayList<Material> listaMateriales = new ArrayList<>();
    private GridView vistaLista;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private DatabaseReference partidaReference;
    private AppCompatButton botonCombate;
    private Caballero caballero;
    private ValueEventListener listenerCombate;
    private ValueEventListener listenerMateriales;
    private int numRonda;
    private Material moneda;
    private boolean descansando=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pantalla_caballero);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Materiales").child(String.valueOf(Sesion.getNumLobby()));
        partidaReference = firebaseDatabase.getReference().child("Partida");
        partidaReference = firebaseDatabase.getReference().child("Partida");

        vistaLista = (GridView) findViewById(R.id.textRecursos);

        botonCombate = findViewById(R.id.botonCombate);



        caballero = getCaballero();
        TextView glblTimer = findViewById(R.id.timerTextView);
        ArrayList<Estadistico> estadisticos= new ArrayList<>();
        AdaptadorEstadisticas adaptadorEstadisticas = new AdaptadorEstadisticas(this, R.layout.gridview_recursos_feudo, estadisticos);
        GridView ui_listaObjetos = (GridView) findViewById(R.id.gridView_EstadisticasCaballero);

        new CountDownTimer(360000, 1000) {

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



                estadisticos.clear();
                estadisticos.add(new Estadistico("Salud", R.drawable.salud_ajustado, caballero.getSalud(), caballero.getSalud_max()));
                estadisticos.add(new Estadistico("Ataque", R.drawable.ataque_ajustado, caballero.getAtaque(), 120));
                estadisticos.add(new Estadistico("Velocidad de ataque", R.drawable.velocidad_ajustado, caballero.getVelocidadAtaque(), 35));
                estadisticos.add(new Estadistico("Estamina", R.drawable.estamina_ajustado, caballero.getEstamina(), 100));
                adaptadorEstadisticas.setListaEstadisticas(estadisticos);


                ui_listaObjetos.setAdapter(adaptadorEstadisticas);


            }

            public void onFinish() {

                algoritmo(numRonda);
            }

        }.start();

        botonDesplAcciones = findViewById(R.id.botonAcciones);
        botonDesplTienda = findViewById(R.id.botonTienda);
        botonDesplDiario = findViewById(R.id.botonDiario);
        botonDesplInventario = findViewById(R.id.botonInventario);

        botonDesplTienda.setText("Bar");


        partidaReference.child(getCodigoSala()).child("1").child("numRonda").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                numRonda = snapshot.getValue(Integer.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        botonDesplAcciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog acciones = new Dialog(PantallaCaballeroActivity.this);
                acciones.setContentView(R.layout.pop_up_acciones_caballero);
                acciones.setCancelable(true);
                acciones.show();

                AppCompatButton botonVida= (AppCompatButton) acciones.findViewById(R.id.botonMejorarVida);
                AppCompatButton botonAtaque= (AppCompatButton) acciones.findViewById(R.id.botonMejorarAtaque);
                AppCompatButton botonVelocidadAtaque= (AppCompatButton) acciones.findViewById(R.id.botonMejorarVelocidad);
                botonVida.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!descansando && caballero.getEstamina()>=30){
                            caballero.setSalud_max(caballero.getSalud_max()+100);
                            caballero.setEstamina(caballero.getEstamina()-30);
                            botonVida.setText("Salud Aumentada");
                        }
                    }
                });

                botonAtaque.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (!descansando && caballero.getEstamina()>=25){
                            caballero.setAtaque(caballero.getAtaque()+5);
                            caballero.setEstamina(caballero.getEstamina()-25);
                            botonAtaque.setText("Ataque Aumentado");
                        }

                    }
                });
                botonVelocidadAtaque.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!descansando && caballero.getEstamina()>=25){
                            caballero.setVelocidadAtaque(caballero.getVelocidadAtaque()+2);
                            caballero.setEstamina(caballero.getEstamina()-25);
                            botonVelocidadAtaque.setText("Velocidad Aumentada");
                        }
                    }
                });



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
                if (!descansando && moneda.getCantidad()>=22){
                    moneda.setCantidad(moneda.getCantidad()-22);
                    if (caballero.getEstamina()+50>=100){
                        caballero.setEstamina(100);
                    }else {
                        caballero.setEstamina(caballero.getEstamina() + 50);
                    }
                    descansando=true;
                }else if (descansando){
                        Toast.makeText(PantallaCaballeroActivity.this, "Ya estas descansando en el bar, no puedes realizar mas acciones", Toast.LENGTH_SHORT).show();
                    }
            }
        });

        botonDesplDiario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog acciones = new Dialog(PantallaCaballeroActivity.this);
                acciones.setContentView(R.layout.pop_up_diario);
                acciones.setCancelable(true);
                acciones.show();

                firebaseDatabase.getReference().child("Diario").child(getCodigoSala()).child("Caballero").addListenerForSingleValueEvent(new ValueEventListener() {
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
                final Dialog acciones = new Dialog(PantallaCaballeroActivity.this);
                acciones.setContentView(R.layout.pop_up_inventario);
                acciones.setCancelable(true);
                acciones.show();
                firebaseDatabase.getReference().child("Inventario").child(getCodigoSala()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        ArrayList<Objeto> inventario= new ArrayList<>();

                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                            Objeto objeto = postSnapshot.getValue(Objeto.class);
                            inventario.add(objeto);
                        }

                        GridView ui_listaObjetos= (GridView) acciones.findViewById(R.id.gridView_Inventario);
                        AdaptadorInventario adaptadorInventario= new AdaptadorInventario(acciones.getContext(),R.layout.pop_up_inventario,inventario,caballero);
                        ui_listaObjetos.setAdapter(adaptadorInventario);

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
    }

    @Override
    protected void onStart() {
        super.onStart();

        AdaptadorMateriales adaptador = new AdaptadorMateriales(this, R.layout.activity_gridview_materiales, listaMateriales);

        listenerMateriales = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaMateriales.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Material material = postSnapshot.getValue(Material.class);
                    if (material.getRol().contains("Caballero")) {
                        listaMateriales.add(material);
                        moneda=material;
                    }
                }
                adaptador.setListaMateriales(listaMateriales);
                vistaLista.setAdapter(adaptador);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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

                if (listoCaballero == 1) {
                    botonCombate.setText(String.format("COMBATE (%s/5)", jugadores));
                }

                if (listoCaballero == 1 && listoHerrero == 1 && listoMaestroCuadras == 1 && listoCurandero == 1 && listoDruida == 1) {
                    partidaReference.child(getCodigoSala()).child("1").child("combateListo").setValue(0);
                    algoritmo(numRonda);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Usuarios no están listos para combate", Toast.LENGTH_SHORT).show();
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


    public void algoritmo(int nRonda) {

        firebaseDatabase.getReference().child("Enemigos").child(String.valueOf(nRonda)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Enemigo enemigo = snapshot.getValue(Enemigo.class);
                if (caballero.getVelocidadAtaque() > enemigo.getVelocidadAtaque()) {
                    while (caballero.getSalud() > 0 && enemigo.getSalud() > 0) {
                        enemigo.setSalud(enemigo.getSalud() - caballero.getAtaque());
                        if (enemigo.getSalud() <= 0) {
                            break;
                        }
                        caballero.setSalud(caballero.getSalud() - enemigo.getAtaque());
                    }
                } else {
                    while (caballero.getSalud() > 0 && enemigo.getSalud() > 0) {

                        caballero.setSalud(caballero.getSalud() - enemigo.getAtaque());
                        if (caballero.getSalud() <= 0) {
                            break;
                        }

                        enemigo.setSalud(enemigo.getSalud() - caballero.getAtaque());
                    }
                }
                ArrayList<Objeto> nuevoEquipado = new ArrayList<Objeto>();
                for (int i = 0; i < caballero.getEquipado().size(); i++) {
                    if (caballero.getEquipado().get(i).isEsConsumible() == false) {
                        nuevoEquipado.add(caballero.getEquipado().get(i));
                    }
                }
                caballero.setEquipado(nuevoEquipado);
                Intent i;
                if (caballero.getSalud() > 0) {
                    if (nRonda < 10) {

                        if(nRonda != 5) {
                            i = new Intent(PantallaCaballeroActivity.this, ResultadosCaballero.class);
                            i.putExtra("codigo", getCodigoSala());
                            i.putExtra("Caballero", caballero);
                            i.putExtra("nRonda", nRonda + 1);

                            for (int j = 0; j < listaMateriales.size(); j++) {
                                if (listaMateriales.get(j).getName().equals("Moneda")) {
                                    listaMateriales.get(j).setCantidad(listaMateriales.get(j).getCantidad() + enemigo.getMonedasGanas());
                                }
                            }

                            FirebaseDAO.setMateriales(String.valueOf(Sesion.getNumLobby()), listaMateriales);
                            firebaseDatabase.getReference().child("Caballero").child(getCodigoSala()).setValue(caballero);
                            partidaReference.child(getCodigoSala()).child("1").child("numRonda").setValue(nRonda + 1);
                            partidaReference.child(getCodigoSala()).child("1").child("justaGanada").setValue(1);

                        } else {
                            i = new Intent(PantallaCaballeroActivity.this, PantallaCuestionario.class);
                            i.putExtra("codigo", getCodigoSala());
                            i.putExtra("Caballero", caballero);
                            i.putExtra("nRonda", nRonda + 1);
                            i.putExtra("rol", "1");
                        }




                    } else {
                        i = new Intent(PantallaCaballeroActivity.this, PantallaVictoria.class);
                    }

                } else {
                    partidaReference.child(getCodigoSala()).child("1").child("justaGanada").setValue(2);
                    i = new Intent(PantallaCaballeroActivity.this, PantallaCuestionario.class);
                    i.putExtra("codigo", getCodigoSala());
                    i.putExtra("nRonda", nRonda + 1);
                    i.putExtra("rol", "1");


                }

                startActivity(i);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    public void clickBotonCombate(View view) {
        partidaReference.child(getCodigoSala()).child("1").child("combateListo").setValue(1);
        partidaReference.child(getCodigoSala()).child("1").child("resultadosListos").setValue(0);
    }

    @Override
    protected void onStop() {
        super.onStop();
        databaseReference.removeEventListener(listenerMateriales);
        partidaReference.child(getCodigoSala()).removeEventListener(listenerCombate);

    }

    public String getCodigoSala() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            return extras.getString("codigo");
        }
        return null;
    }

    public Caballero getCaballero() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            return (Caballero) extras.getSerializable("caballero");
        }
        return null;
    }
}
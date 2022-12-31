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
import com.example.justajuan.adapter.AdaptadorEstadisticas;
import com.example.justajuan.adapter.AdaptadorInventario;
import com.example.justajuan.adapter.AdaptadorMateriales;
import com.example.justajuan.model.Caballero;
import com.example.justajuan.model.Enemigo;
import com.example.justajuan.model.Estadistico;
import com.example.justajuan.model.Material;
import com.example.justajuan.model.Objeto;
import com.example.justajuan.model.Sesion;
import com.example.justajuan.persistence.FirebaseDAO;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class PantallaCaballeroActivity extends AppCompatActivity {

    private AppCompatButton botonAtras;
    private ArrayList<Material> listaMateriales;
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
    private boolean descansando = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pantalla_caballero);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Materiales").child(String.valueOf(Sesion.getInstance().getNumLobby()));
        partidaReference = firebaseDatabase.getReference().child("Partida");

        vistaLista = (GridView) findViewById(R.id.textRecursos);

        botonCombate = findViewById(R.id.botonCombate);


        caballero = getCaballero();
        TextView glblTimer = findViewById(R.id.timerTextView);
        ArrayList<Estadistico> estadisticos = new ArrayList<>();
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

        AppCompatButton botonDesplAcciones = findViewById(R.id.botonAcciones);
        AppCompatButton botonDesplTienda = findViewById(R.id.botonTienda);
        AppCompatButton botonDesplDiario = findViewById(R.id.botonDiario);
        AppCompatButton botonDesplInventario = findViewById(R.id.botonInventario);

        botonDesplTienda.setText(R.string.boton_bar);


        partidaReference.child(String.valueOf(Sesion.getInstance().getNumLobby())).child("1").child("numRonda").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Integer tmp = snapshot.getValue(Integer.class);
                if(tmp !=null){
                    numRonda = tmp;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        botonDesplAcciones.setOnClickListener(view -> {
            final Dialog acciones = new Dialog(PantallaCaballeroActivity.this);
            acciones.setContentView(R.layout.pop_up_acciones_caballero);
            acciones.setCancelable(true);
            acciones.show();

            AppCompatButton botonVida = (AppCompatButton) acciones.findViewById(R.id.botonMejorarVida);
            AppCompatButton botonAtaque = (AppCompatButton) acciones.findViewById(R.id.botonMejorarAtaque);
            AppCompatButton botonVelocidadAtaque = (AppCompatButton) acciones.findViewById(R.id.botonMejorarVelocidad);
            botonVida.setOnClickListener(v -> {
                if (!descansando && caballero.getEstamina() >= 30) {
                    caballero.setSalud_max(caballero.getSalud_max() + 50);
                    caballero.setEstamina(caballero.getEstamina() - 30);
                    botonVida.setText(R.string.salud_aumentada);
                }
            });

            botonAtaque.setOnClickListener(v -> {

                if (!descansando && caballero.getEstamina() >= 25) {
                    caballero.setAtaque(caballero.getAtaque() + 5);
                    caballero.setEstamina(caballero.getEstamina() - 25);
                    botonAtaque.setText(R.string.ataque_aumentado);
                }

            });
            botonVelocidadAtaque.setOnClickListener(v -> {
                if (!descansando && caballero.getEstamina() >= 25) {
                    caballero.setVelocidadAtaque(caballero.getVelocidadAtaque() + 2);
                    caballero.setEstamina(caballero.getEstamina() - 25);
                    botonVelocidadAtaque.setText(R.string.velocidad_aumentada);
                }
            });


            botonAtras = acciones.findViewById(R.id.botonAtras);
            botonAtras.setOnClickListener(view13 -> acciones.hide());
        });

        botonDesplTienda.setOnClickListener(view -> {
            if (!descansando && moneda.getCantidad() >= 22) {
                moneda.setCantidad(moneda.getCantidad() - 22);
                caballero.setEstamina(Math.min(caballero.getEstamina() + 50, 100));
                descansando = true;
            } else if (descansando) {
                Toast.makeText(PantallaCaballeroActivity.this, "Ya estas descansando en el bar, no puedes realizar mas acciones", Toast.LENGTH_SHORT).show();
            }
        });

        botonDesplDiario.setOnClickListener(view -> {
            final Dialog acciones = new Dialog(PantallaCaballeroActivity.this);
            acciones.setContentView(R.layout.pop_up_diario);
            acciones.setCancelable(true);
            acciones.show();

            firebaseDatabase.getReference().child("Diario").child(String.valueOf(Sesion.getInstance().getNumLobby())).child("Caballero").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    TextView diarioAcum = (TextView) acciones.findViewById(R.id.infoAcumulada);
                    diarioAcum.setText(snapshot.child("ResultadosAcumulados").getValue(String.class));

                    botonAtras = acciones.findViewById(R.id.botonAtras);
                    botonAtras.setOnClickListener(view1 -> acciones.hide());

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });

        });

        botonDesplInventario.setOnClickListener(view -> {
            final Dialog acciones = new Dialog(PantallaCaballeroActivity.this);
            acciones.setContentView(R.layout.pop_up_inventario);
            acciones.setCancelable(true);
            acciones.show();
            firebaseDatabase.getReference().child("Inventario").child(String.valueOf(Sesion.getInstance().getNumLobby())).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    ArrayList<Objeto> inventario = new ArrayList<>();

                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        Objeto objeto = postSnapshot.getValue(Objeto.class);
                        inventario.add(objeto);
                    }

                    GridView ui_listaObjetos1 = (GridView) acciones.findViewById(R.id.gridView_Inventario);
                    AdaptadorInventario adaptadorInventario = new AdaptadorInventario(acciones.getContext(), R.layout.pop_up_inventario, inventario, caballero);
                    ui_listaObjetos1.setAdapter(adaptadorInventario);

                    botonAtras = acciones.findViewById(R.id.botonAtras);
                    botonAtras.setOnClickListener(view12 -> acciones.hide());

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Sesion sesion = Sesion.getInstance();

        listaMateriales = new ArrayList<>();
        AdaptadorMateriales adaptador = new AdaptadorMateriales(this, R.layout.activity_gridview_materiales, listaMateriales);

        listenerMateriales = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaMateriales.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Material material = postSnapshot.getValue(Material.class);
                    if (Objects.requireNonNull(material).getRol().contains("Caballero")) {
                        listaMateriales.add(material);
                        moneda = material;
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

                if (cont == 5) {
                    partidaReference.child(String.valueOf(sesion.getNumLobby())).child(String.valueOf(sesion.getRol().ordinal() + 1)).child("combateListo").setValue(0);
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

                .setPositiveButton("Si", (dialog, which) -> {
                    finishAffinity();
                    System.exit(0);
                })

                .setNegativeButton("No", null)
                .show();
    }


    public void algoritmo(int nRonda) {

        firebaseDatabase.getReference().child("Enemigos").child(String.valueOf(nRonda)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Enemigo enemigo = snapshot.getValue(Enemigo.class);
                if(enemigo!=null) {
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
                }
                ArrayList<Objeto> nuevoEquipado = new ArrayList<>();
                for (int i = 0; i < caballero.getEquipado().size(); i++) {
                    if (!caballero.getEquipado().get(i).isEsConsumible()) {
                        nuevoEquipado.add(caballero.getEquipado().get(i));
                    }
                }
                caballero.setEquipado(nuevoEquipado);
                Intent i;
                if (caballero.getSalud() > 0) {
                    if (nRonda < 10) {

                        if (nRonda != 5) {
                            i = new Intent(PantallaCaballeroActivity.this, ResultadosCaballero.class);
                            i.putExtra("Caballero", caballero);
                            i.putExtra("nRonda", nRonda + 1);

                            for (int j = 0; j < listaMateriales.size(); j++) {
                                if (listaMateriales.get(j).getName().equals("Moneda") && enemigo != null) {
                                    listaMateriales.get(j).setCantidad(listaMateriales.get(j).getCantidad() + enemigo.getMonedasGanas());
                                }
                            }

                            FirebaseDAO.setMateriales(String.valueOf(Sesion.getInstance().getNumLobby()), listaMateriales);
                            String numlobby = String.valueOf(Sesion.getInstance().getNumLobby());
                            firebaseDatabase.getReference().child("Caballero").child(numlobby).setValue(caballero);
                            partidaReference.child(numlobby).child("1").child("numRonda").setValue(nRonda + 1);
                            partidaReference.child(numlobby).child("1").child("justaGanada").setValue(1);

                        } else {
                            i = new Intent(PantallaCaballeroActivity.this, PantallaCuestionario.class);
                            i.putExtra("Caballero", caballero);
                            i.putExtra("nRonda", nRonda + 1);
                            i.putExtra("justaGanada", 1);
                            i.putExtra("tipo", 0);
                        }


                    } else {
                        i = new Intent(PantallaCaballeroActivity.this, PantallaVictoria.class);
                    }

                } else {
                    partidaReference.child(String.valueOf(Sesion.getInstance().getNumLobby())).child("1").child("justaGanada").setValue(2);
                    i = new Intent(PantallaCaballeroActivity.this, PantallaCuestionario.class);
                    i.putExtra("nRonda", nRonda + 1);
                    i.putExtra("tipo", 1);


                }

                startActivity(i);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    public void clickBotonCombate(View view) {
        Sesion sesion = Sesion.getInstance();
        DatabaseReference dr = partidaReference.child(String.valueOf(sesion.getNumLobby())).child(String.valueOf(sesion.getRol().ordinal() + 1));
        dr.child("combateListo").setValue(1);
        dr.child("resultadosListos").setValue(0);
    }

    @Override
    protected void onStop() {
        super.onStop();
        databaseReference.removeEventListener(listenerMateriales);
        partidaReference.child(String.valueOf(Sesion.getInstance().getNumLobby())).removeEventListener(listenerCombate);

    }

    public Caballero getCaballero() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            return (Caballero) extras.getSerializable("Caballero");
        }
        return null;
    }
}
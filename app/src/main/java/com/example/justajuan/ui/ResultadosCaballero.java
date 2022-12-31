package com.example.justajuan.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.justajuan.R;
import com.example.justajuan.model.Caballero;
import com.example.justajuan.model.Sesion;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class ResultadosCaballero extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference partidaReference;
    private DatabaseReference caballeroReference;
    private ValueEventListener listenerSiguiente;
    private TextView textoResultados;
    private String resultadosAcumulados;
    private AppCompatButton botonSiguiente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_resultados_caballero);

        firebaseDatabase = FirebaseDatabase.getInstance();
        partidaReference = firebaseDatabase.getReference().child("Partida");
        caballeroReference = firebaseDatabase.getReference().child("Caballero");

        textoResultados = findViewById(R.id.resultados);

        botonSiguiente = findViewById(R.id.botonSiguienteResult);


    }

    @Override
    protected void onStart() {
        super.onStart();

        Sesion sesion = Sesion.getInstance();
        partidaReference.child(String.valueOf(sesion.getNumLobby())).child("1").child("justaGanada").setValue(0);

        listenerSiguiente = partidaReference.child(String.valueOf(sesion.getNumLobby())).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                int cont = 0;
                boolean boton_press = false;
                for (int i = 1; i < 6; i++) {
                    Integer tmp = snapshot.child(String.valueOf(i)).child("resultadosListos").getValue(Integer.class);
                    if(tmp!=null) {
                        if (tmp == 1) {
                            if (i == sesion.getRol().ordinal() + 1) {
                                boton_press = true;
                            }
                            cont++;
                        }
                    }
                }

                if (boton_press) {
                    botonSiguiente.setText(getString(R.string.boton_siguiente_pressed,cont));
                }

                if (cont == 5) {
                    Intent i = new Intent(ResultadosCaballero.this, PantallaCaballeroActivity.class);
                    i.putExtra("Caballero",getCaballero());
                    startActivity(i);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Usuarios no están listos para pasar la ventana", Toast.LENGTH_SHORT).show();
            }
        });

        firebaseDatabase.getReference().child("Diario").child(String.valueOf(sesion.getNumLobby())).child("Caballero").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                resultadosAcumulados = snapshot.child("ResultadosAcumulados").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        caballeroReference.child(String.valueOf(sesion.getNumLobby())).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String resultados = String.format("¡Enhorabuena! ¡Has ganado la ronda! \n Tu ataque es: %s                      " +
                                "Tu estamina es: %s \n Tus monedas son: %s                   Tu salud es: %s \n Tu salud maxima es: %s    Tu velocidad de ataque es: %s \n",
                        snapshot.child("ataque").getValue(Integer.class), snapshot.child("estamina").getValue(Integer.class),
                        snapshot.child("monedas").getValue(Integer.class), snapshot.child("salud").getValue(Integer.class),
                        snapshot.child("salud_max").getValue(Integer.class), snapshot.child("velocidadAtaque").getValue(Integer.class));

                textoResultados.setText(resultados);

                if(getNumRonda() == 2) {
                    resultadosAcumulados = resultados;
                } else {
                    resultadosAcumulados = resultadosAcumulados + resultados;
                }

                firebaseDatabase.getReference().child("Diario").child(String.valueOf(sesion.getNumLobby())).child("Caballero").child("ResultadosAcumulados").setValue(resultadosAcumulados);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error al mostrar las estadisticas del caballero", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void avanzarResultados(View view) {
        Sesion sesion = Sesion.getInstance();
        DatabaseReference dr = partidaReference.child(String.valueOf(sesion.getNumLobby())).child(String.valueOf(sesion.getRol().ordinal()+1));
        dr.child("combateListo").setValue(0);
        dr.child("resultadosListos").setValue(1);
    }

    @Override
    protected void onStop() {
        super.onStop();
        partidaReference.child(String.valueOf(Sesion.getInstance().getNumLobby())).removeEventListener(listenerSiguiente);
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

    public int getNumRonda() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            return extras.getInt("nRonda");
        }
        return 0;
    }

    public Caballero getCaballero() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            return (Caballero) extras.getSerializable("Caballero");
        }
        return null;
    }

}
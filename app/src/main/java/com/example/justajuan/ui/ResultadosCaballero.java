package com.example.justajuan.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.justajuan.R;
import com.example.justajuan.model.Caballero;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
        getSupportActionBar().hide();
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

        listenerSiguiente = partidaReference.child(getCodigoSala()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                int listoCaballero = snapshot.child("1").child("resultadosListos").getValue(Integer.class);
                int listoHerrero = snapshot.child("2").child("resultadosListos").getValue(Integer.class);
                int listoMaestroCuadras = snapshot.child("3").child("resultadosListos").getValue(Integer.class);
                int listoCurandero = snapshot.child("4").child("resultadosListos").getValue(Integer.class);
                int listoDruida = snapshot.child("5").child("resultadosListos").getValue(Integer.class);

                int jugadores = (listoCaballero + listoHerrero + listoMaestroCuadras + listoCurandero + listoDruida);

                if (listoCaballero == 1) {
                    botonSiguiente.setText(String.format("SIGUIENTE (%s/5)", jugadores));
                }

                if (listoCaballero == 1 && listoHerrero == 1 && listoMaestroCuadras == 1 && listoCurandero == 1 && listoDruida == 1) {
                    Intent i = new Intent(ResultadosCaballero.this, PantallaCaballeroActivity.class);
                    i.putExtra("codigo", getCodigoSala());
                    i.putExtra("caballero",getCaballero());
                    startActivity(i);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Usuarios no están listos para pasar la ventana", Toast.LENGTH_SHORT).show();
            }
        });

        firebaseDatabase.getReference().child("Diario").child(getCodigoSala()).child("Caballero").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                resultadosAcumulados = snapshot.child("ResultadosAcumulados").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        caballeroReference.child(getCodigoSala()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String resultados = String.format("¡Enhorabuena! ¡Has ganado la ronda! \n Tu ataque es: %s                      " +
                                "Tu estamina es: %s \n Tus monedas son: %s                   Tu salud es: %s \n Tu salud maxima es: %s    Tu velocidad de ataque es: %s \n",
                        snapshot.child("ataque").getValue(Integer.class), snapshot.child("estamina").getValue(Integer.class),
                        snapshot.child("monedas").getValue(Integer.class), snapshot.child("salud").getValue(Integer.class),
                        snapshot.child("salud_max").getValue(Integer.class), snapshot.child("velocidadAtaque").getValue(Integer.class));

                textoResultados.setText(String.format(resultados));

                if(getNumRonda() == 2) {
                    resultadosAcumulados = resultados;
                } else {
                    resultadosAcumulados = resultadosAcumulados + resultados;
                }

                firebaseDatabase.getReference().child("Diario").child(getCodigoSala()).child("Caballero").child("ResultadosAcumulados").setValue(resultadosAcumulados);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error al mostrar las estadisticas del caballero", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void avanzarResultados(View view) {
        partidaReference.child(getCodigoSala()).child("1").child("combateListo").setValue(0);
        partidaReference.child(getCodigoSala()).child("1").child("resultadosListos").setValue(1);
    }

    @Override
    protected void onStop() {
        super.onStop();
        partidaReference.child(getCodigoSala()).removeEventListener(listenerSiguiente);
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
            return (Caballero) extras.getSerializable("caballero");
        }
        return null;
    }

}
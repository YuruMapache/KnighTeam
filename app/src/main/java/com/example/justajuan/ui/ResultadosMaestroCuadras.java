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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ResultadosMaestroCuadras extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference partidaReference;
    private DatabaseReference enemigoReference;
    private ValueEventListener listenerSiguiente;
    private AppCompatButton botonSiguiente;
    private TextView textoResultadosMaestroCuadras;
    private String resultadosAcumulados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_resultados_maestro_cuadras);

        firebaseDatabase = FirebaseDatabase.getInstance();
        partidaReference = firebaseDatabase.getReference().child("Partida");
        enemigoReference = firebaseDatabase.getReference().child("Enemigos");

        botonSiguiente = findViewById(R.id.botonSiguienteResult);
        textoResultadosMaestroCuadras = findViewById(R.id.resultados);

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

                if (listoMaestroCuadras == 1) {
                    botonSiguiente.setText(String.format("SIGUIENTE (%s/5)", jugadores));
                }

                if (listoCaballero == 1 && listoHerrero == 1 && listoMaestroCuadras == 1 && listoCurandero == 1 && listoDruida == 1) {
                    Intent i = new Intent(ResultadosMaestroCuadras.this, PantallaMaestroCuadrasActivity.class);
                    i.putExtra("codigo", getCodigoSala());
                    startActivity(i);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Usuarios no están listos para pasar la ventana", Toast.LENGTH_SHORT).show();
            }
        });

        firebaseDatabase.getReference().child("Diario").child(getCodigoSala()).child("Maestro_Cuadras").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                resultadosAcumulados = snapshot.child("ResultadosAcumulados").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        enemigoReference.child(String.valueOf(getNumRonda() + 1)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String resultados = String.format("¡Enhorabuena! ¡Has ganado la ronda! \n El siguiente enemigo se llama %s \n" +
                        "Su velocidad de ataque será %s \n", snapshot.child("nombre").getValue(), snapshot.child("velocidadAtaque").getValue());
                textoResultadosMaestroCuadras.setText(resultados);

                if(getNumRonda() == 2) {
                    resultadosAcumulados = resultados;
                } else {
                    resultadosAcumulados = resultadosAcumulados + resultados;
                }

                firebaseDatabase.getReference().child("Diario").child(getCodigoSala()).child("Maestro_Cuadras").child("ResultadosAcumulados").setValue(resultadosAcumulados);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error al mostrar la informacion del enemigo", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void avanzarResultados(View view) {
        partidaReference.child(getCodigoSala()).child("3").child("combateListo").setValue(0);
        partidaReference.child(getCodigoSala()).child("3").child("resultadosListos").setValue(1);
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
            return extras.getInt("numRonda");
        }
        return 0;
    }
}
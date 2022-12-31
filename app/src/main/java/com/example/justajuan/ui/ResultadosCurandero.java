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
import com.example.justajuan.model.Objeto;
import com.example.justajuan.model.Sesion;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class ResultadosCurandero extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference partidaReference;
    private DatabaseReference caballeroReference;
    private ValueEventListener listenerSiguiente;
    private AppCompatButton botonSiguiente;
    private TextView textoResultadosCurandero;
    private String resultadosAcumulados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_resultados_curandera);

        firebaseDatabase = FirebaseDatabase.getInstance();
        partidaReference = firebaseDatabase.getReference().child("Partida");
        caballeroReference = firebaseDatabase.getReference().child("Caballero");

        botonSiguiente = findViewById(R.id.botonSiguienteResult);
        textoResultadosCurandero = findViewById(R.id.resultados);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Sesion sesion = Sesion.getInstance();
        listenerSiguiente = partidaReference.child(String.valueOf(sesion.getNumLobby())).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                int cont = 0;
                boolean boton_press = false;
                for (int i = 1; i < 6; i++) {
                    Integer tmp = snapshot.child(String.valueOf(i)).child("resultadosListos").getValue(Integer.class);
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
                    botonSiguiente.setText(getString(R.string.boton_siguiente_pressed, cont));
                }

                if (cont == 5) {
                    Intent i = new Intent(ResultadosCurandero.this, PantallaCuranderoActivity.class);
                    i.putExtra("objetosCreandose", getObjetosCreandose());
                    i.putExtra("listaObjetos", getListaObjetos());
                    startActivity(i);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Usuarios no están listos para pasar la ventana", Toast.LENGTH_SHORT).show();
            }
        });

        firebaseDatabase.getReference().child("Diario").child(String.valueOf(sesion.getNumLobby())).child("Curandero").addListenerForSingleValueEvent(new ValueEventListener() {
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

                String resultados = String.format("¡Enhorabuena! ¡Has ganado la ronda! \n El caballero tiene una salud actual de %s después de la justa \n"
                        , snapshot.child("salud").getValue());

                textoResultadosCurandero.setText(resultados);

                if (getNumRonda() == 2) {
                    resultadosAcumulados = resultados;
                } else {
                    resultadosAcumulados = resultadosAcumulados + resultados;
                }

                firebaseDatabase.getReference().child("Diario").child(String.valueOf(sesion.getNumLobby())).child("Curandero").child("ResultadosAcumulados").setValue(resultadosAcumulados);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error al mostrar la informacion del enemigo", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void avanzarResultados(View view) {
        Sesion sesion = Sesion.getInstance();
        DatabaseReference dr = partidaReference.child(String.valueOf(sesion.getNumLobby())).child(String.valueOf(sesion.getRol().ordinal() + 1));
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
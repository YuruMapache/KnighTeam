package com.example.justajuan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class PantallaJugador extends AppCompatActivity {

    private EditText nombreJugador;
    private EditText numSala;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference, salaReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pantalla_jugador);

        final Button botonJugar = findViewById(R.id.botonJugar);

        nombreJugador = findViewById(R.id.nombreUsuario);
        numSala = findViewById(R.id.numeroSala);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        salaReference = firebaseDatabase.getReference().child("Partida");


        botonJugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String getNombre = nombreJugador.getText().toString();
                String getNumSala = numSala.getText().toString();
                HashMap<String,String> Clase = new HashMap<>();
                salaReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                        if(datasnapshot.hasChild(getNumSala)) {
                            Clase.put(getNombre," ");

                            databaseReference.child("Partida/"+ getNumSala)
                                    .setValue(Clase);

                            Intent i = new Intent(PantallaJugador.this, EsperaLoginActivity.class);
                            // Pasa a la ventana seleccion de plantilla y personaje.
                            i.putExtra("codigo",getNumSala);
                            i.putExtra("nombreUsuario", getNombre);
                            startActivity(i);
                        } else {
                            Toast.makeText(getApplicationContext(), "Código de sala incorrecto. Intentalo de nuevo", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });
    }
}
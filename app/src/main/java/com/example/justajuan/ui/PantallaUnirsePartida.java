package com.example.justajuan.ui;

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

import com.example.justajuan.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class PantallaUnirsePartida extends AppCompatActivity {

    private EditText nombreJugador;
    private EditText textSala;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference, salaReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pantalla_unirse_partida);

        final Button botonJugar = findViewById(R.id.botonJugar);

        nombreJugador = findViewById(R.id.nombreUsuario);
        textSala = findViewById(R.id.numeroSala);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        salaReference = firebaseDatabase.getReference().child("Partida");


        botonJugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String getNombre = nombreJugador.getText().toString();
                String numSala = textSala.getText().toString();
                HashMap<String, String> Clase = new HashMap<>();
                salaReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                        if (datasnapshot.hasChild(numSala)) {
                            if (datasnapshot.child(numSala).getChildrenCount() < 5) {

                                Clase.put("Rol", " ");

                                databaseReference.child("Partida/" + numSala + "/" + getNombre)
                                        .setValue(Clase);

                                Intent i = new Intent(PantallaUnirsePartida.this, PantallaEsperaLoginActivity.class);
                                // Pasa a la ventana seleccion de plantilla y personaje.
                                i.putExtra("codigo", numSala);
                                i.putExtra("nombreUsuario", getNombre);
                                startActivity(i);
                            } else if (datasnapshot.child(numSala).getChildrenCount() > 5) {
                                Toast.makeText(getApplicationContext(), "La sala está llena", Toast.LENGTH_LONG).show();
                            }
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
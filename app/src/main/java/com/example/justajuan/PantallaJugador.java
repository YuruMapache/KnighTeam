package com.example.justajuan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class PantallaJugador extends AppCompatActivity {

    private EditText nombreJugador;
    private EditText numSala;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private DataSnapshot dataSnapshot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pantalla_jugador);

        final Button botonJugar = findViewById(R.id.botonJugar);

        nombreJugador.findViewById(R.id.nombreUsuario);
        numSala.findViewById(R.id.numeroSala);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();


        botonJugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String getNombre = nombreJugador.getText().toString();
                String getNumSala = numSala.getText().toString();

                databaseReference.child

                startActivity(new Intent(PantallaJugador.this, Knight.class));
                //Pasa a la ventana seleccion de plantilla y personaje.
            }
        });
    }
}

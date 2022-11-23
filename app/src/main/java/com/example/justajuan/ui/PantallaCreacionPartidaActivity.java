package com.example.justajuan.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.justajuan.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


public class PantallaCreacionPartidaActivity extends AppCompatActivity {

    private EditText nombre;
    private EditText edad;
    private Spinner genero;
    private Button botonCrearPartida;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pantalla_creacion_partida);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        nombre = findViewById(R.id.nombreJugador);
        edad = findViewById(R.id.edadJugador);
        genero = findViewById(R.id.eleccionGenero);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.genero, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        genero.setAdapter(adapter);

        botonCrearPartida = findViewById(R.id.botonCrearJugador);

        botonCrearPartida.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String nombreARegistrar = nombre.getText().toString();
                String edadARegistrar = edad.getText().toString();
                String generoARegistrar = genero.getSelectedItem().toString();

                int codigoAleatorio = (int) ((Math.random() * 100000));
                String tokenSala = String.valueOf(codigoAleatorio);

                HashMap<String, String> Registro = new HashMap<>();
                Registro.put("Edad", edadARegistrar);
                Registro.put("Genero", generoARegistrar);
                Registro.put("Rol", " ");

                databaseReference.child("Partida/" + tokenSala + "/" + nombreARegistrar)
                        .setValue(Registro);
                Intent i = new Intent(PantallaCreacionPartidaActivity.this, PantallaEsperaLoginActivity.class);
                i.putExtra("codigo", tokenSala);
                i.putExtra("nombreUsuario", nombreARegistrar);
                startActivity(i);
                //Pasa a la ventana seleccion de plantilla y personaje.
            }
        });
    }
}
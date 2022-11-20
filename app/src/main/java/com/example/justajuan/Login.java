package com.example.justajuan;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


public class Login extends AppCompatActivity {

    private EditText nombre;
    private EditText email;
    private Button botonCrearJugador;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        nombre = findViewById(R.id.nombreJugador);
        email = findViewById(R.id.emailJugador);
        botonCrearJugador = findViewById(R.id.botonCrearJugador);

        botonCrearJugador.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String getNombre = nombre.getText().toString();
                String getEmail = email.getText().toString();

                HashMap<String, Object> usuarios = new HashMap<>();
                usuarios.put("nombre", getNombre);
                usuarios.put("email", getEmail);

                databaseReference.child("Usuarios")
                        .setValue(usuarios);
                {
                }
                startActivity(new Intent(Login.this, Knight.class));
                //Pasa a la ventana seleccion de plantilla y personaje.
            }
        });
    }
}
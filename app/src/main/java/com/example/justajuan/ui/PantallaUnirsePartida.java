package com.example.justajuan.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.justajuan.R;
import com.example.justajuan.model.Sesion;
import com.example.justajuan.model.User;
import com.example.justajuan.persistence.FirebaseDAO;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class PantallaUnirsePartida extends AppCompatActivity {

    private EditText nombreJugador;
    private EditText edadJugador;
    private Spinner genero;
    private EditText textSala;
    private DatabaseReference salaReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pantalla_unirse_partida);

        nombreJugador = findViewById(R.id.nombreUsuario);
        edadJugador = findViewById(R.id.edadJugador);
        genero = findViewById(R.id.eleccionGenero);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.genero, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        genero.setAdapter(adapter);

        textSala = findViewById(R.id.numeroSala);
        Button botonJugar = findViewById(R.id.botonJugar);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        salaReference = firebaseDatabase.getReference().child("Partida");


        botonJugar.setOnClickListener(view -> {

            String nombreJugadorARegistrar = nombreJugador.getText().toString();
            String edadARegistrar = edadJugador.getText().toString();
            String generoARegistrar = genero.getSelectedItem().toString();
            String numSala = textSala.getText().toString();

            if (nombreJugadorARegistrar.matches("") || edadARegistrar.matches("") || numSala.matches("")) {
                Toast.makeText(getApplicationContext(), "Revise si los datos introducidos son correctos", Toast.LENGTH_SHORT).show();
                return;
            }

            /*if(Integer.parseInt(edadARegistrar) < 18) {
                Toast.makeText(getApplicationContext(), "Edad mínima 18 años", Toast.LENGTH_SHORT).show();
                return;
            }*/

            //Crear usuario
            User user = new User();
            user.setNombre(nombreJugadorARegistrar);
            user.setEdad(Integer.parseInt(edadARegistrar));
            user.setGenero(generoARegistrar);
            Sesion.getInstance().setUsuario(user);
            Sesion.getInstance().setNumLobby(Integer.parseInt(numSala));

            salaReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                    if (datasnapshot.hasChild(numSala)) {
                        if (datasnapshot.child(numSala).getChildrenCount() < 5) {

                            FirebaseDAO.setPlayer(numSala, user);

                            Intent i = new Intent(PantallaUnirsePartida.this, PantallaEsperaLoginActivity.class);
                            i.putExtra("nombreUsuario", nombreJugadorARegistrar);
                            i.putExtra("edad", edadARegistrar);
                            i.putExtra("genero", generoARegistrar);
                            i.putExtra("codigo", numSala);
                            startActivity(i);
                        } else if (datasnapshot.child(numSala).getChildrenCount() >= 5) {
                            Toast.makeText(getApplicationContext(), "La sala está llena", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Código de sala incorrecto. Intentalo de nuevo", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getApplicationContext(), "Error del evento Listener", Toast.LENGTH_SHORT).show();
                }
            });


        });
    }
}
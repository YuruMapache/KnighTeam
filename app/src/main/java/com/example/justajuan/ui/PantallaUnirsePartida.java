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
import com.example.justajuan.model.Sesion;
import com.example.justajuan.model.User;
import com.example.justajuan.persistence.FirebaseDAO;
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
    private Button botonJugar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pantalla_unirse_partida);

        nombreJugador = findViewById(R.id.nombreUsuario);
        textSala = findViewById(R.id.numeroSala);
        botonJugar = findViewById(R.id.botonJugar);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        salaReference = firebaseDatabase.getReference().child("Partida");


        botonJugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nombreJugadorARegistrar = nombreJugador.getText().toString();
                String numSala = textSala.getText().toString();

                //Crear usuario
                User user = new User();
                user.setNombre(nombreJugadorARegistrar);
                Sesion.getInstance().setUsuario(user);
                Sesion.getInstance().setNumLobby(Integer.parseInt(numSala));
                HashMap<String, String> Registro = new HashMap<>();
                salaReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                        if (datasnapshot.hasChild(numSala)) {
                            if (datasnapshot.child(numSala).getChildrenCount() < 5) {

                                //Registro.put("Rol", " ");

                                //databaseReference.child("Partida/" + numSala + "/" + nombreJugadorARegistrar).setValue(Registro);

                                FirebaseDAO.setPlayer(Integer.parseInt(numSala),user);

                                Intent i = new Intent(PantallaUnirsePartida.this, PantallaEsperaLoginActivity.class);
                                i.putExtra("codigo", numSala);
                                i.putExtra("nombreUsuario", nombreJugadorARegistrar);
                                startActivity(i);
                            } else if (datasnapshot.child(numSala).getChildrenCount() > 5) {
                                Toast.makeText(getApplicationContext(), "La sala está llena", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Código de sala incorrecto. Intentalo de nuevo", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getApplicationContext(), "Error del evento Listener", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
    }
}
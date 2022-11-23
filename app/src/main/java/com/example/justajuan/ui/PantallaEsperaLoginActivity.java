package com.example.justajuan.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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


public class PantallaEsperaLoginActivity extends AppCompatActivity {

    private TextView creacionSala;
    private TextView jugadoresTotales;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference, partidaReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pantalla_espera_login);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        partidaReference = firebaseDatabase.getReference().child("Partida");

        creacionSala = findViewById(R.id.salaCreada);
        jugadoresTotales = findViewById(R.id.esperaJugadores);

        creacionSala.setText(String.format("¡Sala creada! El código es %s", getCodigoSala()));
        int n = (int) ((Math.random() * (5 - 1)) + 1);
        partidaReference.child(getCodigoSala()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int numJugadores;
                if (dataSnapshot.exists()) {
                    numJugadores = (int) dataSnapshot.getChildrenCount();
                    jugadoresTotales.setText(String.format("Esperando jugadores... (%s/5)", numJugadores));

                    if (numJugadores == 5) {
                        System.out.println(n);
                        switch (n) {
                            case 1:
                                startActivity(new Intent(PantallaEsperaLoginActivity.this, PantallaHerreroActivity.class));
                                break;
                            case 2:
                                startActivity(new Intent(PantallaEsperaLoginActivity.this, PantallaCuranderoActivity.class));
                                break;
                            case 3:
                                startActivity(new Intent(PantallaEsperaLoginActivity.this, PantallaDruidaActivity.class));
                                break;
                            case 4:
                                startActivity(new Intent(PantallaEsperaLoginActivity.this, PantallaCaballeroActivity.class));
                                break;
                            case 5:
                                startActivity(new Intent(PantallaEsperaLoginActivity.this, PantallaMaestroCuadrasActivity.class));
                                break;
                            default:
                                break;
                        }

                        //startActivity(new Intent(PantallaEsperaLoginActivity.this, PantallaGestorRolesActivity.class));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error al crear la partida", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public String getCodigoSala() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            return extras.getString("codigo");
        }
        return null;
    }
}
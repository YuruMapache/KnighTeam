package com.example.justajuan.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.justajuan.R;
import com.example.justajuan.model.Caballero;
import com.example.justajuan.model.Rol;
import com.example.justajuan.model.Sesion;
import com.example.justajuan.persistence.FirebaseDAO;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;


public class PantallaEsperaLoginActivity extends AppCompatActivity {

    private TextView jugadoresTotales;
    private DatabaseReference partidaReference;
    private ValueEventListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pantalla_espera_login);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        partidaReference = firebaseDatabase.getReference().child("Partida");

        TextView creacionSala = findViewById(R.id.salaCreada);
        jugadoresTotales = findViewById(R.id.esperaJugadores);

        creacionSala.setText(getString(R.string.sala_espera, Sesion.getInstance().getNumLobby()));

    }

    @Override
    protected void onStart() {
        super.onStart();

        Sesion sesion = Sesion.getInstance();
        listener = partidaReference.child(String.valueOf(sesion.getNumLobby())).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int numJugadores;
                Intent i;
                if (dataSnapshot.exists()) {
                    numJugadores = (int) dataSnapshot.getChildrenCount();
                    jugadoresTotales.setText(String.format("Esperando jugadores... (%s/5)", numJugadores));

                    if (numJugadores == 5) {
                        switch (Sesion.getInstance().getRol().toString()) {
                            case "HERRERO":
                                i = new Intent(PantallaEsperaLoginActivity.this, PantallaGestorRolesActivity.class);
                                i.putExtra("codigo", sesion.getNumLobby());
                                i.putExtra("rol", Rol.HERRERO);
                                break;
                            case "CURANDERO":
                                i = new Intent(PantallaEsperaLoginActivity.this, PantallaGestorRolesActivity.class);
                                i.putExtra("codigo", sesion.getNumLobby());
                                i.putExtra("rol", Rol.CURANDERO);
                                break;
                            case "DRUIDA":
                                i = new Intent(PantallaEsperaLoginActivity.this, PantallaGestorRolesActivity.class);
                                i.putExtra("codigo", String.valueOf(sesion.getNumLobby()));
                                i.putExtra("rol", Rol.DRUIDA);
                                break;
                            case "CABALLERO":
                                Caballero caballero = new Caballero(100, 100, 5, 1, 100, 100, new ArrayList<>());
                                FirebaseDAO.setCaballero(String.valueOf(Sesion.getInstance().getNumLobby()),caballero);
                                i = new Intent(PantallaEsperaLoginActivity.this, PantallaGestorRolesActivity.class);
                                i.putExtra("codigo", sesion.getNumLobby());
                                i.putExtra("rol", Rol.CABALLERO);
                                i.putExtra("Caballero",caballero);
                                break;
                            case "MAESTRO_CUADRAS":
                                i = new Intent(PantallaEsperaLoginActivity.this, PantallaGestorRolesActivity.class);
                                i.putExtra("codigo", sesion.getNumLobby());
                                i.putExtra("rol", Rol.MAESTRO_CUADRAS);
                                break;
                            default:
                                i = new Intent(PantallaEsperaLoginActivity.this, PantallaGestorRolesActivity.class);
                                break;
                        }
                        startActivity(i);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error al crear la partida", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        back();
    }

    /**
     * Eliminamos el nombre del jugador en la sala y volvemos al menu para elegir otra sala al pulsar el boton "back"
     */
    public void back() {
        Sesion sesion = Sesion.getInstance();
        FirebaseDAO.deletePlayer(sesion.getNumLobby(), sesion.getRol().ordinal()+1);
        Intent intent = new Intent(PantallaEsperaLoginActivity.this, PantallaInicioActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        partidaReference.child(String.valueOf(Sesion.getInstance().getNumLobby())).removeEventListener(listener);
    }

}
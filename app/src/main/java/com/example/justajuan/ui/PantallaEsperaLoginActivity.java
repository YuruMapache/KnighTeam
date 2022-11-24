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
import com.example.justajuan.model.Rol;
import com.example.justajuan.model.Sesion;
import com.example.justajuan.persistence.FirebaseDAO;
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

        Sesion.getInstance().setNumLobby(Integer.parseInt(getCodigoSala()));

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

        partidaReference.child(getCodigoSala()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int numJugadores;
                if (dataSnapshot.exists()) {
                    numJugadores = (int) dataSnapshot.getChildrenCount();
                    jugadoresTotales.setText(String.format("Esperando jugadores... (%s/5)", numJugadores));
                    Intent i;

                    if (numJugadores == 5) {
                        switch (Sesion.getInstance().getRol().toString()) {
                            case "HERRERO":
                                i = new Intent(PantallaEsperaLoginActivity.this, PantallaGestorRolesActivity.class);
                                i.putExtra("codigo", getCodigoSala());
                                i.putExtra("rol", Rol.HERRERO);
                                break;
                            case "CURANDERO":
                                i = new Intent(PantallaEsperaLoginActivity.this, PantallaGestorRolesActivity.class);
                                i.putExtra("codigo", getCodigoSala());
                                i.putExtra("rol", Rol.CURANDERO);
                                break;
                            case "DRUIDA":
                                i = new Intent(PantallaEsperaLoginActivity.this, PantallaGestorRolesActivity.class);
                                i.putExtra("codigo", getCodigoSala());
                                i.putExtra("rol", Rol.DRUIDA);
                                break;
                            case "CABALLERO":
                                i = new Intent(PantallaEsperaLoginActivity.this, PantallaGestorRolesActivity.class);
                                i.putExtra("codigo", getCodigoSala());
                                i.putExtra("rol", Rol.CABALLERO);
                                break;
                            case "MAESTRO_CUADRAS":
                                i = new Intent(PantallaEsperaLoginActivity.this, PantallaGestorRolesActivity.class);
                                i.putExtra("codigo", getCodigoSala());
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
        FirebaseDAO.deletePlayer(sesion.getNumLobby(), sesion.getUsuario().getNombre());
        Intent intent = new Intent(PantallaEsperaLoginActivity.this, PantallaUnirsePartida.class);
        startActivity(intent);
    }


    public String getNombreUsuario() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            return extras.getString("nombreUsuario");
        }
        return null;
    }

    public String getEdad() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            return extras.getString("edad");
        }
        return null;
    }

    public String getGenero() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            return extras.getString("genero");
        }
        return null;
    }

    public String getCodigoSala() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            return extras.getString("codigo");
        }
        return null;
    }

}
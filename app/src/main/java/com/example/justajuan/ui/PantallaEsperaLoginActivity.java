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

import java.util.HashMap;


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
        int pantallaAleatoria = (int) ((Math.random() * (5 - 1)) + 1);
        HashMap<String, String> Registro = new HashMap<>();

        partidaReference.child(getCodigoSala()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int numJugadores;
                if (dataSnapshot.exists()) {
                    numJugadores = (int) dataSnapshot.getChildrenCount();
                    jugadoresTotales.setText(String.format("Esperando jugadores... (%s/5)", numJugadores));
                    Intent i;

                    if (numJugadores == 5) {
                        switch (pantallaAleatoria) {
                            case 1:
                                Registro.put("Rol", "HERRERO");

                                databaseReference.child("Partida/" + getCodigoSala() + "/" + getNombreUsuario())
                                        .setValue(Registro);
                                i = new Intent(PantallaEsperaLoginActivity.this, PantallaHerreroActivity.class);
                                i.putExtra("rol", Rol.HERRERO);
                                break;
                            case 2:
                                Registro.put("Rol", "CURANDERO");

                                databaseReference.child("Partida/" + getCodigoSala() + "/" + getNombreUsuario())
                                        .setValue(Registro);
                                i = new Intent(PantallaEsperaLoginActivity.this, PantallaCuranderoActivity.class);
                                i.putExtra("rol", Rol.CURANDERO);

                                break;
                            case 3:
                                Registro.put("Rol", "DRUIDA");

                                databaseReference.child("Partida/" + getCodigoSala() + "/" + getNombreUsuario())
                                        .setValue(Registro);
                                i = new Intent(PantallaEsperaLoginActivity.this, PantallaHerreroActivity.class);
                                i.putExtra("rol", Rol.DRUIDA);
                                break;
                            case 4:
                                Registro.put("Rol", "CABALLERO");

                                databaseReference.child("Partida/" + getCodigoSala() + "/" + getNombreUsuario())
                                        .setValue(Registro);
                                i = new Intent(PantallaEsperaLoginActivity.this, PantallaCaballeroActivity.class);
                                i.putExtra("rol", Rol.CABALLERO);

                                break;
                            case 5:
                                Registro.put("Rol", "MAESTRO_CUADRAS");

                                databaseReference.child("Partida/" + getCodigoSala() + "/" + getNombreUsuario())
                                        .setValue(Registro);
                                i = new Intent(PantallaEsperaLoginActivity.this, PantallaMaestroCuadrasActivity.class);
                                i.putExtra("rol", Rol.MAESTRO_CUADRAS);

                                break;
                            default:
                                i = new Intent(PantallaEsperaLoginActivity.this, PantallaEsperaLoginActivity.class);
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

    //GETTERS
    public String getCodigoSala() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            return extras.getString("codigo");
        }
        return null;
    }

    public String getNombreUsuario() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            return extras.getString("nombreUsuario");
        }
        return null;
    }


}
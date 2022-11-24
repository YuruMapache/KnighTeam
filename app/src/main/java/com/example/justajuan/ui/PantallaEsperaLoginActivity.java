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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


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

        //int pantallaAleatoria = (int) ((Math.random() * (5 - 1)) + 1);

        ArrayList<String> listaRoles = new ArrayList<>();
        listaRoles.add(Rol.CABALLERO.name());
        listaRoles.add(Rol.HERRERO.name());
        listaRoles.add(Rol.MAESTRO_CUADRAS.name());
        listaRoles.add(Rol.CURANDERO.name());
        listaRoles.add(Rol.DRUIDA.name());

        Collections.shuffle(listaRoles);

        HashMap<String, String> registro = new HashMap<>();

        partidaReference.child(getCodigoSala()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int numJugadores;
                if (dataSnapshot.exists()) {
                    numJugadores = (int) dataSnapshot.getChildrenCount();
                    jugadoresTotales.setText(String.format("Esperando jugadores... (%s/5)", numJugadores));

                    if (numJugadores == 5) {
                        for (DataSnapshot hijo : dataSnapshot.getChildren()) {
                            for (String rol : listaRoles) {
                                registro.put("Edad", getEdad());
                                registro.put("Genero", getGenero());
                                registro.put("Rol", rol);

                                databaseReference.child("Partida/" + getCodigoSala() + "/" + getNombreUsuario())
                                        .setValue(registro);
                                break;
                            }
                        }
                            /* switch (listaRoles.get(i)) {
                                case 1:
                                    Registro.put("Edad", getEdad());
                                    Registro.put("Genero", getGenero());
                                    Registro.put("Rol", "HERRERO");

                                    databaseReference.child("Partida/" + getCodigoSala() + "/" + getNombreUsuario())
                                            .setValue(Registro);
                                    Intent i1 = new Intent(PantallaEsperaLoginActivity.this, PantallaHerreroActivity.class);
                                    i1.putExtra("rol", Rol.HERRERO);
                                    startActivity(i1);
                                    break;
                                case 2:
                                    Registro.put("Edad", getEdad());
                                    Registro.put("Genero", getGenero());
                                    Registro.put("Rol", "CURANDERO");

                                    databaseReference.child("Partida/" + getCodigoSala() + "/" + getNombreUsuario())
                                            .setValue(Registro);
                                    Intent i2 = new Intent(PantallaEsperaLoginActivity.this, PantallaCuranderoActivity.class);
                                    i2.putExtra("rol", Rol.CURANDERO);
                                    startActivity(i2);
                                    break;
                                case 3:
                                    Registro.put("Edad", getEdad());
                                    Registro.put("Genero", getGenero());
                                    Registro.put("Rol", "DRUIDA");

                                    databaseReference.child("Partida/" + getCodigoSala() + "/" + getNombreUsuario())
                                            .setValue(Registro);
                                    Intent i3 = new Intent(PantallaEsperaLoginActivity.this, PantallaDruidaActivity.class);
                                    i3.putExtra("rol", Rol.DRUIDA);
                                    startActivity(i3);
                                    break;
                                case 4:
                                    Registro.put("Edad", getEdad());
                                    Registro.put("Genero", getGenero());
                                    Registro.put("Rol", "CABALLERO");

                                    databaseReference.child("Partida/" + getCodigoSala() + "/" + getNombreUsuario())
                                            .setValue(Registro);
                                    Intent i4 = new Intent(PantallaEsperaLoginActivity.this, PantallaCaballeroActivity.class);
                                    i4.putExtra("rol", Rol.CABALLERO);
                                    startActivity(i4);
                                    break;
                                case 5:
                                    Registro.put("Edad", getEdad());
                                    Registro.put("Genero", getGenero());
                                    Registro.put("Rol", "MAESTRO_CUADRAS");

                                    databaseReference.child("Partida/" + getCodigoSala() + "/" + getNombreUsuario())
                                            .setValue(Registro);
                                    Intent i5 = new Intent(PantallaEsperaLoginActivity.this, PantallaMaestroCuadrasActivity.class);
                                    i5.putExtra("rol", Rol.MAESTRO_CUADRAS);
                                    startActivity(i5);
                                    break;
                                default:
                                    break;
                            }*/

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error al crear la partida", Toast.LENGTH_SHORT).show();
            }
        });
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
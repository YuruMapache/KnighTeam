package com.example.justajuan.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.justajuan.R;
import com.example.justajuan.model.Rol;
import com.example.justajuan.model.Sesion;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Timer;
import java.util.TimerTask;

public class PantallaGestorRolesActivity extends AppCompatActivity {

    // Duración en milisegundos que se mostrará el splash
    private final int DURACION_SPLASH = 6000; // 1 segundo
    private TextView rolSeleccionado;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference, partidaReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pantalla_gestor_roles);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        partidaReference = firebaseDatabase.getReference().child("Partida");

        rolSeleccionado = findViewById(R.id.rolEscogido);
        rolSeleccionado.setText(String.format("TU ROL ASIGNADO ES:\n %s", Sesion.getInstance().getRol().toString()));

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                partidaReference.child(getCodigoSala()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        int numJugadores;
                        if (dataSnapshot.exists()) {
                            numJugadores = (int) dataSnapshot.getChildrenCount();
                            Intent i;
                            if (numJugadores == 5) {
                                switch (Sesion.getInstance().getRol().toString()) {
                                    case "HERRERO":
                                        i = new Intent(PantallaGestorRolesActivity.this, PantallaHerreroActivity.class);
                                        i.putExtra("codigo", getCodigoSala());
                                        i.putExtra("rol", Rol.HERRERO);
                                        break;
                                    case "CURANDERO":
                                        i = new Intent(PantallaGestorRolesActivity.this, PantallaCuranderoActivity.class);
                                        i.putExtra("codigo", getCodigoSala());
                                        i.putExtra("rol", Rol.CURANDERO);
                                        break;
                                    case "DRUIDA":
                                        i = new Intent(PantallaGestorRolesActivity.this, PantallaDruidaActivity.class);
                                        i.putExtra("codigo", getCodigoSala());
                                        i.putExtra("rol", Rol.DRUIDA);
                                        break;
                                    case "CABALLERO":
                                        i = new Intent(PantallaGestorRolesActivity.this, PantallaCaballeroActivity.class);
                                        i.putExtra("codigo", getCodigoSala());
                                        i.putExtra("rol", Rol.CABALLERO);
                                        break;
                                    case "MAESTRO_CUADRAS":
                                        i = new Intent(PantallaGestorRolesActivity.this, PantallaMaestroCuadrasActivity.class);
                                        i.putExtra("codigo", getCodigoSala());
                                        i.putExtra("rol", Rol.MAESTRO_CUADRAS);
                                        break;
                                    default:
                                        i = new Intent(PantallaGestorRolesActivity.this, PantallaGestorRolesActivity.class);
                                        break;
                                }
                                startActivity(i);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getApplicationContext(), "Error al mostrar el rol", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }, DURACION_SPLASH);
    }

    @Override
    public void onBackPressed() {
        //back();
    }

    public String getCodigoSala() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            return extras.getString("codigo");
        }
        return null;
    }

    public String getRol() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            return extras.getString("rol");
        }
        return null;
    }
}
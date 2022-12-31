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
import com.example.justajuan.model.Objeto;
import com.example.justajuan.model.Sesion;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class PantallaGestorRolesActivity extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference, partidaReference;
    private ArrayList<Objeto> listaObjetos;
    private ArrayList<Objeto> temporal;
    private Caballero caballero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pantalla_gestor_roles);

        listaObjetos = new ArrayList<>();
        temporal = new ArrayList<>();
        Sesion sesion = Sesion.getInstance();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        partidaReference = firebaseDatabase.getReference().child("Partida");

        TextView rolSeleccionado = findViewById(R.id.rolEscogido);
        rolSeleccionado.setText(String.format("TU ROL ASIGNADO ES:\n %s", Sesion.getInstance().getRol().toString()));

        TextView descripcionRol = findViewById(R.id.descripcionRol);
        switch (sesion.getRol()){
            case DRUIDA:
                descripcionRol.setText(R.string.objetivo_druida);
                break;
            case HERRERO:
                descripcionRol.setText(R.string.objetivo_herrero);
                break;
            case CABALLERO:
                descripcionRol.setText(R.string.objetivo_caballero);
                break;
            case CURANDERO:
                descripcionRol.setText(R.string.objetivo_curandero);
                break;
            case MAESTRO_CUADRAS:
                descripcionRol.setText(R.string.objetivo_maestro_cuadras);
                break;
            default:
                break;
        }

        databaseReference.child("Objetos").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Objeto objeto = postSnapshot.getValue(Objeto.class);
                    listaObjetos.add(objeto);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        partidaReference.child(String.valueOf(sesion.getNumLobby())).child("1").child("justaGanada").setValue(0);
        partidaReference.child(String.valueOf(sesion.getNumLobby())).child("1").child("combateListo").setValue(0);


        // Duración en milisegundos que se mostrará el splash
        // 1 segundo
        int DURACION_SPLASH = 6000;
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                partidaReference.child(String.valueOf(sesion.getNumLobby())).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        int numJugadores;
                        if (dataSnapshot.exists()) {
                            numJugadores = (int) dataSnapshot.getChildrenCount();
                            Intent i;
                            if (numJugadores == 5) {
                                switch (Sesion.getInstance().getRol().toString()) {
                                    case "HERRERO":
                                        for (int j=0; j<listaObjetos.size();j++){
                                            if (listaObjetos.get(j).getClase().equals("Herrero")){
                                                temporal.add(listaObjetos.get(j));
                                            }
                                        }
                                        i = new Intent(PantallaGestorRolesActivity.this, PantallaHerreroActivity.class);
                                        i.putExtra("listaObjetos",temporal);
                                        startActivity(i);
                                        break;
                                    case "CURANDERO":
                                        for (int j=0; j<listaObjetos.size();j++){
                                            if (listaObjetos.get(j).getClase().equals("Curandero")){
                                                temporal.add(listaObjetos.get(j));
                                            }
                                        }
                                        i = new Intent(PantallaGestorRolesActivity.this, PantallaCuranderoActivity.class);
                                        i.putExtra("listaObjetos",temporal);
                                        startActivity(i);
                                        break;
                                    case "DRUIDA":
                                        for (int j=0; j<listaObjetos.size();j++){
                                            if (listaObjetos.get(j).getClase().equals("Druida")){
                                                temporal.add(listaObjetos.get(j));
                                            }
                                        }
                                        i = new Intent(PantallaGestorRolesActivity.this, PantallaDruidaActivity.class);
                                        i.putExtra("listaObjetos",temporal);
                                        startActivity(i);
                                        break;
                                    case "CABALLERO":

                                        databaseReference = firebaseDatabase.getReference().child("Caballero").child(String.valueOf(sesion.getNumLobby()));

                                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                Intent j;
                                                caballero = snapshot.getValue(Caballero.class);
                                                Objects.requireNonNull(caballero).setEquipado(new ArrayList<>());
                                                partidaReference.child(String.valueOf(sesion.getNumLobby())).child("1").child("justaGanada").setValue(0);
                                                j = new Intent(PantallaGestorRolesActivity.this, PantallaCaballeroActivity.class);
                                                j.putExtra("Caballero", caballero);
                                                startActivity(j);

                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });

                                        break;
                                    case "MAESTRO_CUADRAS":
                                        for (int j=0; j<listaObjetos.size();j++){
                                            if (listaObjetos.get(j).getClase().equals("Maestro_Cuadras")){
                                                temporal.add(listaObjetos.get(j));
                                            }
                                        }
                                        i = new Intent(PantallaGestorRolesActivity.this, PantallaMaestroCuadrasActivity.class);
                                        i.putExtra("listaObjetos",temporal);
                                        startActivity(i);
                                        break;
                                    default:
                                        i = new Intent(PantallaGestorRolesActivity.this, PantallaGestorRolesActivity.class);
                                        startActivity(i);
                                        break;
                                }
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
}
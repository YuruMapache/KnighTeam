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
import com.example.justajuan.model.Caballero;
import com.example.justajuan.model.Objeto;
import com.example.justajuan.model.Rol;
import com.example.justajuan.model.Sesion;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class PantallaGestorRolesActivity extends AppCompatActivity {

    // Duración en milisegundos que se mostrará el splash
    private final int DURACION_SPLASH = 6000; // 1 segundo
    private TextView rolSeleccionado;
    private TextView descripcionRol;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference, partidaReference;
    private ArrayList<Objeto> listaObjetos= new ArrayList<>();
    private ArrayList<Objeto> temporal= new ArrayList<>();
    private Caballero caballero;

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

        descripcionRol = findViewById(R.id.descripcionRol);
        if(Sesion.getInstance().getRol().toString().equals("HERRERO")){
            descripcionRol.setText(String.format("Durante el transcurso de la partida tu rol será decisivo para aumentar la salud, daño o velocidad del caballero."));
        }
        else if(Sesion.getInstance().getRol().toString().equals("CABALLERO")){
            descripcionRol.setText(String.format("El rol que cuenta con los atributos, tu mision es vencer al oponente con la ayuda de tu equipo."));
        }
        else if(Sesion.getInstance().getRol().toString().equals("MAESTRO_CUADRAS")){
            descripcionRol.setText(String.format("Durante el transcurso de la partida tu rol será decisivo para aumentar la velocidad, asi como cuidar al caballo."));
        }
        else if(Sesion.getInstance().getRol().toString().equals("CURANDERO")){
            descripcionRol.setText(String.format("Durante el transcurso de la partida tu rol será decisivo para regenerar la vida del caballero y curarle de sus heridas"));
        }
        else{
            descripcionRol.setText(String.format("Durante el transcurso de la partida tu rol será decisivo para aumentar la velocidad, salud y ataque maximos."));
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
                                        for (int j=0; j<listaObjetos.size();j++){
                                            if (listaObjetos.get(j).getClase().equals("Herrero")){
                                                temporal.add(listaObjetos.get(j));
                                            }
                                        }
                                        i = new Intent(PantallaGestorRolesActivity.this, PantallaHerreroActivity.class);
                                        i.putExtra("codigo", getCodigoSala());
                                        i.putExtra("rol", Rol.HERRERO);
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
                                        i.putExtra("codigo", getCodigoSala());
                                        i.putExtra("rol", Rol.CURANDERO);
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
                                        i.putExtra("codigo", getCodigoSala());
                                        i.putExtra("rol", Rol.DRUIDA);
                                        i.putExtra("listaObjetos",temporal);
                                        startActivity(i);
                                        break;
                                    case "CABALLERO":

                                        databaseReference = firebaseDatabase.getReference().child("Caballero").child(getCodigoSala());

                                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                Intent j;
                                                caballero = snapshot.getValue(Caballero.class);
                                                caballero.setEquipado(new ArrayList<>());
                                                partidaReference.child(getCodigoSala()).child("1").child("justaGanada").setValue(0);
                                                j = new Intent(PantallaGestorRolesActivity.this, PantallaCaballeroActivity.class);
                                                j.putExtra("codigo", getCodigoSala());
                                                j.putExtra("rol", Rol.CABALLERO);
                                                j.putExtra("caballero", caballero);
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
                                        i.putExtra("codigo", getCodigoSala());
                                        i.putExtra("rol", Rol.MAESTRO_CUADRAS);
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
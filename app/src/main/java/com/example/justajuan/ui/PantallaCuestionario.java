package com.example.justajuan.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.justajuan.R;
import com.example.justajuan.model.Caballero;
import com.example.justajuan.model.Cuestionario;
import com.example.justajuan.model.Formulario;
import com.example.justajuan.model.Objeto;
import com.example.justajuan.model.Sesion;
import com.example.justajuan.persistence.AdaptadorCuestionario;
import com.example.justajuan.persistence.AdaptadorCuestionarioPreguntas;
import com.example.justajuan.persistence.FirebaseDAO;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PantallaCuestionario extends AppCompatActivity {

    private TextView tituloCuestionario;
    private GridView preguntas;
    private ArrayList<Cuestionario> preguntasCuestionario;
    private AppCompatButton botonSiguiente;
    private DatabaseReference dr;
    FirebaseDatabase fd;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pantalla_cuestionario);

        fd= FirebaseDatabase.getInstance();

        tituloCuestionario = findViewById(R.id.tituloCuestionario);
        preguntas = findViewById(R.id.ui_ListaPreguntas);
        botonSiguiente = findViewById(R.id.botonSiguiente);

        if (getNRonda() == 5) {
            dr = fd.getReference().child("CuestionariosPreguntas").child("Intermedio");
            tituloCuestionario.setText("CUESTIONARIO INTERMEDIO");

        } else {
            dr = fd.getReference().child("CuestionariosPreguntas").child("Final");
            tituloCuestionario.setText("CUESTIONARIO FINAL");
        }


        dr.addListenerForSingleValueEvent(new ValueEventListener() {

            ArrayList<Cuestionario> preguntasCuestionario = new ArrayList();

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Cuestionario cuestionario = postSnapshot.getValue(Cuestionario.class);
                    preguntasCuestionario.add(cuestionario);
                }

                AdaptadorCuestionarioPreguntas adaptadorCuestionarioPreguntas = new AdaptadorCuestionarioPreguntas(PantallaCuestionario.this, R.layout.gridview_preguntas_cuestionario, preguntasCuestionario);

                preguntas.setAdapter(adaptadorCuestionarioPreguntas);

                botonSiguiente.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {



                        boolean respondido = false;

                        for (Cuestionario c : preguntasCuestionario) {

                            if (c.getMuyDesacuerdo() + c.getDesacuerdo() + c.getIndiferente() + c.getDeAcuerdo() + c.getMuyAcuerdo() != 1) {
                                respondido = true;
                            }

                        }

                        if (respondido) {
                            Toast.makeText(PantallaCuestionario.this, "Por favor rellena todos los campos", Toast.LENGTH_SHORT).show();
                        } else {

                            for (int i = 0; i < preguntasCuestionario.size(); i++) {
                                dr.child(String.valueOf(i)).setValue(preguntasCuestionario.get(i));
                            }
                            Intent i;
                            if (getNRonda() != 5 && getJustaGanada()==0) {
                                i = new Intent(PantallaCuestionario.this, PantallaDerrota.class);
                                startActivity(i);

                            } else if(getNRonda() >= 10) {
                                i = new Intent(PantallaCuestionario.this, PantallaVictoria.class);
                                startActivity(i);

                            } else {

                                switch (Integer.parseInt(getRol())) {
                                    case 1:
                                        i = new Intent(PantallaCuestionario.this, ResultadosCaballero.class);
                                        i.putExtra("codigo", getCodigoSala());
                                        i.putExtra("nRonda", getNRonda() + 1);
                                        i.putExtra("Caballero", getCaballero());
                                        break;
                                    case 2:
                                        i = new Intent(PantallaCuestionario.this, ResultadosHerrero.class);
                                        i.putExtra("codigo", getCodigoSala());
                                        i.putExtra("nRonda", getNRonda() + 1);
                                        i.putExtra("objetosCreandose",getObjetosCreandose());
                                        i.putExtra("listaObjetos",getListaObjetos());
                                        break;
                                    case 3:
                                        i = new Intent(PantallaCuestionario.this, ResultadosMaestroCuadras.class);
                                        i.putExtra("codigo", getCodigoSala());
                                        i.putExtra("nRonda", getNRonda() + 1);
                                        i.putExtra("objetosCreandose",getObjetosCreandose());
                                        i.putExtra("listaObjetos",getListaObjetos());
                                        break;
                                    case 4:
                                        i = new Intent(PantallaCuestionario.this, ResultadosCurandera.class);
                                        i.putExtra("codigo", getCodigoSala());
                                        i.putExtra("nRonda", getNRonda() + 1);
                                        i.putExtra("objetosCreandose",getObjetosCreandose());
                                        i.putExtra("listaObjetos",getListaObjetos());
                                        break;
                                    case 5:
                                        i = new Intent(PantallaCuestionario.this, ResultadosDruida.class);
                                        i.putExtra("codigo", getCodigoSala());
                                        i.putExtra("nRonda", getNRonda() + 1);
                                        i.putExtra("objetosCreandose",getObjetosCreandose());
                                        i.putExtra("listaObjetos",getListaObjetos());
                                        break;
                                    default:
                                        i = new Intent(PantallaCuestionario.this, PantallaCuestionario.class);
                                        break;
                                }
                                startActivity(i);
                            }
                        }
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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

    public Caballero getCaballero() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            return (Caballero) extras.getSerializable("caballero");
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

    public int getNRonda() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            return extras.getInt("nRonda");
        }
        return 0;
    }

    public ArrayList<Objeto> getListaObjetos(){
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            return (ArrayList<Objeto>) extras.getSerializable("listaObjetos");
        }
        return null;
    }

    public ArrayList<Objeto> getObjetosCreandose(){
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            return (ArrayList<Objeto>) extras.getSerializable("objetosCreandose");
        }
        return null;
    }
    public int getJustaGanada(){
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            return extras.getInt("justaGanada");
        }
        return 0;
    }


    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this, R.style.AlertDialogTheme)
                .setMessage("¿Quieres cerrar la app?")

                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finishAffinity();
                        System.exit(0);
                    }
                })

                .setNegativeButton("No", null)
                .show();
    }


}

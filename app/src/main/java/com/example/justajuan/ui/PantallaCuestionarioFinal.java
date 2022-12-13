package com.example.justajuan.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.justajuan.R;
import com.example.justajuan.model.Formulario;
import com.example.justajuan.model.Sesion;
import com.example.justajuan.persistence.FirebaseDAO;

public class PantallaCuestionarioFinal extends AppCompatActivity {

    TextView pregunta1;
    TextView pregunta2;
    TextView pregunta3;
    TextView pregunta4;
    TextView pregunta5;
    TextView pregunta6;
    TextView pregunta7;
    TextView pregunta8;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pantalla_cuestionario_final);

        pregunta1 = findViewById(R.id.textView1);
        pregunta1.getText().toString();
        pregunta2 = findViewById(R.id.textView2);
        pregunta2.getText().toString();
        pregunta3 = findViewById(R.id.textView3);
        pregunta3.getText().toString();
        pregunta4 = findViewById(R.id.textView4);
        pregunta4.getText().toString();
        pregunta5 = findViewById(R.id.textView5);
        pregunta5.getText().toString();
        pregunta6 = findViewById(R.id.textView6);
        pregunta6.getText().toString();
        pregunta7 = findViewById(R.id.textView7);
        pregunta7.getText().toString();
        pregunta8 = findViewById(R.id.textView8);
        pregunta8.getText().toString();


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

    public void enviarRespuestas(View view) {
        boolean ok = true;
        RadioGroup rg;
        LinearLayout block;
        Formulario form = new Formulario();
        int selectedId;
        LinearLayout llpreguntas = findViewById(R.id.LinearLayoutPreguntas);
        for (int i = 0; i < llpreguntas.getChildCount() - 1; i++) {
            block = (LinearLayout) llpreguntas.getChildAt(i);
            rg = (RadioGroup) block.getChildAt(1);
            int radioButtonID = rg.getCheckedRadioButtonId();
            View radioButton = rg.findViewById(radioButtonID);
            selectedId  = rg.indexOfChild(radioButton);
            if (selectedId == -1) {
                ok = false;
                Toast.makeText(PantallaCuestionarioFinal.this, "Por favor rellena todos los campos", Toast.LENGTH_SHORT).show();
                break;
            } else {
                form.setPregunta(i, selectedId);
            }
        }
        if (ok) {
            Sesion sesion = Sesion.getInstance();
            FirebaseDAO.setForm(sesion.getNumLobby(), sesion.getRol().ordinal()+1, form, "Final");
            Intent i = new Intent(PantallaCuestionarioFinal.this, PantallaResultadosCuestionario.class);
            startActivity(i);
        }
    }
}
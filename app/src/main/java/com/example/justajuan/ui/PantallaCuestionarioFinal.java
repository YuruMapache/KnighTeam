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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pantalla_cuestionario_final);


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
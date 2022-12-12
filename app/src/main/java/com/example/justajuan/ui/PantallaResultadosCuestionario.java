package com.example.justajuan.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.justajuan.R;
import com.example.justajuan.model.Sesion;
import com.example.justajuan.persistence.FirebaseDAO;
import com.google.api.Distribution;

import java.util.Map;

public class PantallaResultadosCuestionario extends AppCompatActivity{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        LinearLayout llpreguntas = findViewById(R.id.LinearLayoutPreguntas);
        LinearLayout bloque, respuestas, columna;
        Map<Integer, Map<Integer, Integer>> resultados = FirebaseDAO.getResults(Sesion.getInstance().getNumLobby());
        for(int i = 0; i<16; i++){
            bloque = (LinearLayout) llpreguntas.getChildAt(i+1);
            respuestas = (LinearLayout) bloque.getChildAt(1);
            for(int j=0; j<5; j++){
                columna = (LinearLayout) respuestas.getChildAt(j);
                ((TextView) columna.getChildAt(1)).setText(resultados.get(i).get(j));
            }
        }
        setContentView(R.layout.activity_resultados_cuestionario);
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

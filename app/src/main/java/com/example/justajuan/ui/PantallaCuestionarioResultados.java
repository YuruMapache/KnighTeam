package com.example.justajuan.ui;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import com.example.justajuan.R;

import java.util.Map;
import java.util.Objects;


public class PantallaCuestionarioResultados extends AppCompatActivity{

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pantalla_cuestionario_resultados);

        Intent intent = getIntent();
        Map<Integer,Float> res = (Map<Integer, Float>) intent.getSerializableExtra("res");

        ScrollView scroll = findViewById(R.id.scroll_preguntas);
        scroll.addView(creaResultados(res));

    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this, R.style.AlertDialogTheme)
                .setMessage("¿Quieres cerrar la app?")

                .setPositiveButton("Si", (dialog, which) -> {
                    finishAffinity();
                    System.exit(0);
                })

                .setNegativeButton("No", null)
                .show();
    }

    /**
     * Crea el bloque de los resultados a mostrar.
     *
     * @param resultados Map de pares id y media.
     *
     * @return LinearLayout de los resultados.
     */
    private LinearLayout creaResultados(Map<Integer,Float> resultados) {
        LinearLayout cuestionarioLayout = new LinearLayout(this);
        cuestionarioLayout.setOrientation(LinearLayout.VERTICAL);
        cuestionarioLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        cuestionarioLayout.setGravity(Gravity.CENTER_VERTICAL);

        resultados.forEach((key, value) -> cuestionarioLayout.addView(creaPregunta(key,value)));

        cuestionarioLayout.addView(crearBloqueBotones());
        return cuestionarioLayout;
    }


    /**
     * Crea el bloque final de los resultados con botones.
     *
     * @return LinearLayout de los botones.
     */
    private LinearLayout crearBloqueBotones() {
        // Crea el LinearLayout y establece sus propiedades
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);

        // Crea el AppCompatButton y establece sus propiedades
        AppCompatButton botonSiguiente = new AppCompatButton(this);
        botonSiguiente.setId(View.generateViewId());
        botonSiguiente.setLayoutParams(new LinearLayout.LayoutParams(500, 150));
        botonSiguiente.setBackgroundResource(R.drawable.rock_button);
        botonSiguiente.setText(R.string.menu_principal);
        botonSiguiente.setTextColor(ContextCompat.getColor(this, R.color.black));
        botonSiguiente.setTypeface(null, Typeface.BOLD);
        botonSiguiente.setOnClickListener(v -> {
            Intent i = new Intent(PantallaCuestionarioResultados.this, PantallaInicioActivity.class);
            startActivity(i);
        });

        // Añade el botón al LinearLayout
        linearLayout.addView(botonSiguiente);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        return linearLayout;
    }


    /**
     * Crea un bloque de resultados con su texto y media.
     *
     * @param key ID de la pregunta.
     * @param value Media de la pregunta.
     *
     * @return Bloque de la pregunta.
     */
    private LinearLayout creaPregunta(int key, float value) {
        LinearLayout preguntaLayout = new LinearLayout(this);
        preguntaLayout.setOrientation(LinearLayout.VERTICAL);

        String[] preguntas = getResources().getStringArray(R.array.preguntas);
        preguntaLayout.addView(crearTextoPregunta(preguntas[key]));
        preguntaLayout.addView(crearTextoMedia(value));

        return preguntaLayout;
    }

    /**
     * Crea el texto de la pregunta.
     *
     * @param pregtext Texto de la pregunta.
     * @return TextView del texto.
     */
    private TextView crearTextoPregunta(String pregtext) {
        TextView textViewPregunta = new TextView(this);
        textViewPregunta.setText(pregtext);
        textViewPregunta.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        textViewPregunta.setGravity(Gravity.CENTER);
        textViewPregunta.setTextColor(Color.BLACK);
        textViewPregunta.setTypeface(null, Typeface.BOLD);

        return textViewPregunta;
    }

    /**
     * Crea el texto de la pregunta.
     *
     * @param medtext Texto de la pregunta.
     * @return TextView del texto.
     */
    private TextView crearTextoMedia(float medtext) {
        TextView textViewPregunta = new TextView(this);
        textViewPregunta.setText(String.valueOf(medtext));
        textViewPregunta.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        textViewPregunta.setGravity(Gravity.CENTER);
        textViewPregunta.setTextColor(Color.BLACK);

        return textViewPregunta;
    }
}

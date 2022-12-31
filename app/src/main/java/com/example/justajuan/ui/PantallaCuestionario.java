package com.example.justajuan.ui;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import com.example.justajuan.R;
import com.example.justajuan.model.Cuestionario;
import com.example.justajuan.model.GestorVistas;
import com.example.justajuan.model.Pregunta;
import com.example.justajuan.model.Sesion;
import com.example.justajuan.model.User;
import com.example.justajuan.persistence.CuestionarioDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Clase encargada de modelar la vista de los cuestionarios.
 */
public class PantallaCuestionario extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pantalla_cuestionario);

        ArrayList<Pregunta> pregarray = new ArrayList<>();
        String[] preguntas = getResources().getStringArray(R.array.preguntas);
        for (int i = 0; i < preguntas.length; i++) {
            pregarray.add(new Pregunta(i, preguntas[i]));
        }

        ScrollView scroll = findViewById(R.id.scroll_preguntas);
        switch (getIntent().getIntExtra("tipo", 1)) {
            case 0:
                scroll.addView(creaCuestionario(pregarray.subList(0, 8)));
                break;
            case 1:
                scroll.addView(creaCuestionario(pregarray.subList(8, 16)));
                break;
            default:
                break;
        }

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

    public void enviarRespuestas(View view) {
        boolean ok = true;
        RadioGroup rg;
        LinearLayout block;
        Cuestionario form = new Cuestionario();
        int selectedId;
        ScrollView scroll = findViewById(R.id.scroll_preguntas);
        LinearLayout llpreguntas = (LinearLayout) scroll.getChildAt(0);
        for (int i = 0; i < llpreguntas.getChildCount()-1; i++) {
            block = (LinearLayout) llpreguntas.getChildAt(i);
            rg = (RadioGroup) block.getChildAt(1);
            int radioButtonID = rg.getCheckedRadioButtonId();
            View radioButton = rg.findViewById(radioButtonID);
            selectedId = rg.indexOfChild(radioButton);
            if (selectedId == -1) {
                ok = false;
                Toast.makeText(this, "Por favor rellena todos los campos", Toast.LENGTH_SHORT).show();
                break;
            } else {
                Pregunta tmp = new Pregunta(block.getId(), selectedId);
                form.addPregunta(tmp);
            }
        }
        if (ok) {
            Sesion sesion = Sesion.getInstance();
            CuestionarioDAO.setForm(sesion.getNumLobby(), sesion.getRol().ordinal() + 1, form);
            if(getIntent().getIntExtra("tipo", 0)==1){
                CuestionarioDAO.getResults(sesion.getNumLobby(),this);
            }else{
                Bundle bundle = getIntent().getExtras();
                bundle.remove("tipo");
                GestorVistas.cambioVista(this,bundle,Sesion.getInstance().getRol());
            }

        }
    }

    /**
     * Crea el bloque del cuestionario a mostrar.
     *
     * @param pregarray Array de preguntas a mostrar.
     * @return LinearLayout del cuestionario.
     */
    private LinearLayout creaCuestionario(List<Pregunta> pregarray) {
        LinearLayout cuestionarioLayout = new LinearLayout(this);
        cuestionarioLayout.setOrientation(LinearLayout.VERTICAL);
        cuestionarioLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        cuestionarioLayout.setGravity(Gravity.CENTER_VERTICAL);

        for (Pregunta pregunta : pregarray) {
            cuestionarioLayout.addView(creaPregunta(pregunta));
        }
        cuestionarioLayout.addView(crearBloqueBotones());
        return cuestionarioLayout;
    }

    /**
     * Crea el bloque final del cuestionario con botones.
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
        botonSiguiente.setText(R.string.boton_siguiente);
        botonSiguiente.setTextColor(ContextCompat.getColor(this, R.color.black));
        botonSiguiente.setTypeface(null, Typeface.BOLD);
        botonSiguiente.setOnClickListener(this::enviarRespuestas);

        // Añade el botón al LinearLayout
        linearLayout.addView(botonSiguiente);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        return linearLayout;
    }


    /**
     * Crea un bloque de una pregunta con su texto y opciones.
     *
     * @param pregunta Pregunta a crear.
     * @return Bloque de la pregunta.
     */
    private LinearLayout creaPregunta(Pregunta pregunta) {
        LinearLayout preguntaLayout = new LinearLayout(this);
        preguntaLayout.setOrientation(LinearLayout.VERTICAL);
        preguntaLayout.setId(pregunta.getId());

        preguntaLayout.addView(crearTextoPregunta(pregunta.getDescripcion()));
        preguntaLayout.addView(crearRadioGroupValoraciones());

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
     * Crea un RadioGroup de las valoraciones al LinearLayout introducido.
     *
     * @return RadioGroup de las valoraciones.
     */
    private RadioGroup crearRadioGroupValoraciones() {
        String[] valoraciones = getResources().getStringArray(R.array.valoraciones);
        RadioGroup radioGroup = new RadioGroup(this);
        radioGroup.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        radioGroup.setOrientation(LinearLayout.HORIZONTAL);
        radioGroup.setGravity(Gravity.CENTER);

        for (String valoracion : valoraciones) {
            RadioButton rb = new RadioButton(this);
            rb.setLayoutParams(new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT));
            rb.setPadding(4, 0, 4, 0);
            rb.setText(valoracion);
            radioGroup.addView(rb);
        }
        return radioGroup;
    }
}
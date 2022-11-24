package com.example.justajuan.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.justajuan.R;
import com.example.justajuan.model.Time;

public class PantallaCuranderoActivity extends AppCompatActivity {

    private Time glblTimer;      // Textview del tiempo restante del temporizador
    private Button botonAcciones = findViewById(R.id.relativeLayout3);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pantalla_curandero);

        glblTimer = new Time(findViewById(R.id.timerTextView));
        glblTimer.startTimer();

        botonAcciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog acciones = new Dialog(PantallaCuranderoActivity.this, android.R.style.Theme_Black_NoTitleBar);
                acciones.setContentView(R.layout.acciones_pop_up_alpha);
                acciones.setCancelable(true);
                acciones.show();
            }
        });

    }


}
package com.example.justajuan.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.justajuan.R;
import com.example.justajuan.model.Time;

public class PantallaCuranderoActivity extends AppCompatActivity {

    private Time glblTimer;      // Textview del tiempo restante del temporizador

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
    }
}
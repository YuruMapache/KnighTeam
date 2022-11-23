package com.example.justajuan.ui;

import android.os.Bundle;
import android.os.CountDownTimer;

import androidx.appcompat.app.AppCompatActivity;

import com.example.justajuan.R;
import com.example.justajuan.model.Time;

public class PantallaCaballeroActivity extends AppCompatActivity {

    private CountDownTimer globalTimer;
    private Time glblTimer;      // Textview del tiempo restante del temporizador

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_caballero);
        glblTimer = new Time(findViewById(R.id.timerTextView));
        glblTimer.startTimer();

    }

}
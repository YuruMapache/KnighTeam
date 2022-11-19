package com.example.justajuan.model;

import android.os.CountDownTimer;
import android.widget.TextView;

public class Time {
    private static final long ROUND_TIME = 60000 * 6;      // Millis, equivale a un minuto
    private final TextView textView;
    private CountDownTimer globalTimer;

    public Time(TextView textView) {
        this.textView = textView;
    }

    /**
     * Inicializa la cuenta atras del temporizador global, cada segundo llama a diferentes funciones para calcular los beneficios y el cansancio por segundo
     * En el caso de quedar en bancarrota el temporizador se para.
     * Al finalizar activa la cuenta atras del "tiempo de descanso".
     */
    public void startTimer() {
        globalTimer = new CountDownTimer(ROUND_TIME, 1000) {
            @Override
            public void onTick(long l) {        // Funcionamiento por segundo
                updateTimer(textView, l);
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

    /**
     * Actualiza la vista del temporizador con el tiempo restante
     *
     * @param tempView view del temporizador
     * @param time     tiempo restante
     */
    public void updateTimer(TextView tempView, long time) {
        int minutes = (int) time / 60000;
        int seconds = (int) time % 60000 / 1000;

        String timeLeftText;
        timeLeftText = "" + minutes;
        timeLeftText += ":";
        if (seconds < 10) {
            timeLeftText += "0";
        }
        timeLeftText += seconds;
        tempView.setText(timeLeftText);
    }

    public void stopTimer(){
        globalTimer.cancel();
    }
}

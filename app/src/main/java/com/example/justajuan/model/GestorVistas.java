package com.example.justajuan.model;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.justajuan.ui.ResultadosCaballero;
import com.example.justajuan.ui.ResultadosCurandero;
import com.example.justajuan.ui.ResultadosDruida;
import com.example.justajuan.ui.ResultadosHerrero;
import com.example.justajuan.ui.ResultadosMaestroCuadras;

public class GestorVistas {

    public static void cambioVista(Activity activity, Bundle bundle, Rol rol){
        Intent i;
        switch (rol){
            case HERRERO:
                i = new Intent(activity, ResultadosHerrero.class);
                i.putExtras(bundle);
                break;
            case CURANDERO:
                i = new Intent(activity, ResultadosCurandero.class);
                i.putExtras(bundle);
                break;
            case DRUIDA:
                i = new Intent(activity, ResultadosDruida.class);
                i.putExtras(bundle);
                break;
            case CABALLERO:
                i = new Intent(activity, ResultadosCaballero.class);
                i.putExtras(bundle);
                break;
            case MAESTRO_CUADRAS:
                i = new Intent(activity, ResultadosMaestroCuadras.class);
                i.putExtras(bundle);
                break;
            default:
                break;

        }
    }
}

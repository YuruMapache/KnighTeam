package com.example.justajuan.persistence;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.justajuan.R;
import com.example.justajuan.model.Estadistico;
import com.example.justajuan.model.Material;

import java.util.ArrayList;


public class AdaptadorEstadisticas extends ArrayAdapter {
    private ArrayList<Estadistico> listaEstadisticas = new ArrayList<>();

    public AdaptadorEstadisticas(Context context, int textViewResourceId, ArrayList objects) {
        super(context, textViewResourceId, objects);
        listaEstadisticas = objects;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.gridview_recursos_feudo, null);

        ImageView imageView = (ImageView) v.findViewById(R.id.imagenObjetoCreandose);
        TextView textoNombre = (TextView) v.findViewById(R.id.nombreObjetoCreandose);
        ProgressBar barraProgreso = (ProgressBar) v.findViewById(R.id.barraProgresoObjeto);
        TextView textoValor = (TextView) v.findViewById(R.id.porcentajeProgreso);

        imageView.setImageResource(listaEstadisticas.get(position).getIdDrawable());
        textoNombre.setText(listaEstadisticas.get(position).getNombre());
        textoNombre.setTextSize(15);
        textoValor.setText(listaEstadisticas.get(position).getValorActual() + "/" + listaEstadisticas.get(position).getValorMax());
        textoValor.setTextSize(15);

        int porcentaje= (int) (listaEstadisticas.get(position).getValorActual()*100/listaEstadisticas.get(position).getValorMax());
        barraProgreso.setProgress(porcentaje);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        barraProgreso.setLayoutParams(params);

        return v;

    }

    public void setListaEstadisticas(ArrayList objects) {
        listaEstadisticas = objects;
    }
}
package com.example.justajuan.persistence;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.justajuan.R;
import com.example.justajuan.model.Cuestionario;

import java.util.ArrayList;

public class AdaptadorCuestionario extends ArrayAdapter {

    private ArrayList<Cuestionario> listaPreguntas = new ArrayList<>();

    public AdaptadorCuestionario(Context context, int textViewResourceId, ArrayList objects){
        super(context,textViewResourceId,objects);
        listaPreguntas = objects;
    }

    @Override
    public int getCount(){
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.gridview_stats_preguntas, null);

        TextView pregunta = v.findViewById(R.id.pregunta);
        TextView respMdes = v.findViewById(R.id.respMuyDes);
        TextView respDes = v.findViewById(R.id.respDes);
        TextView respInd = v.findViewById(R.id.respInd);
        TextView respDac = v.findViewById(R.id.respDac);
        TextView respMdac = v.findViewById(R.id.respMuyDac);

        pregunta.setText(listaPreguntas.get(position).getPregunta());
        respMdes.setText(listaPreguntas.get(position).getMuyDesacuerdo());
        respDes.setText(listaPreguntas.get(position).getDesacuerdo());
        respInd.setText(listaPreguntas.get(position).getIndiferente());
        respDac.setText(listaPreguntas.get(position).getDeAcuerdo());
        respMdac.setText(listaPreguntas.get(position).getMuyAcuerdo());

        return v;

    }
}

package com.example.justajuan.persistence;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.justajuan.R;
import com.example.justajuan.model.Cuestionario;

import java.util.ArrayList;

public class AdaptadorCuestionarioPreguntas extends ArrayAdapter {

    private ArrayList<Cuestionario> listaPreguntas =new ArrayList<>();

    public AdaptadorCuestionarioPreguntas(Context context, int textViewResourceId, ArrayList objects){
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
        v = inflater.inflate(R.layout.gridview_preguntas_cuestionario, null);

        TextView nombrePregunta = (TextView) v.findViewById(R.id.pregunta);
        nombrePregunta.setText(listaPreguntas.get(position).getPregunta());
        RadioGroup radioPreguntas = v.findViewById(R.id.radioPreguntas);

        radioPreguntas.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch(checkedId){
                    case 0:
                        listaPreguntas.get(position).clear();
                        listaPreguntas.get(position).setMuyDesacuerdo(1);
                        break;
                    case 1:
                        listaPreguntas.get(position).clear();
                        listaPreguntas.get(position).setDesacuerdo(1);
                        break;
                    case 2:
                        listaPreguntas.get(position).clear();
                        listaPreguntas.get(position).setIndiferente(1);
                        break;
                    case 3:
                        listaPreguntas.get(position).clear();
                        listaPreguntas.get(position).setDeAcuerdo(1);
                        break;
                    case 4:
                        listaPreguntas.get(position).clear();
                        listaPreguntas.get(position).setMuyAcuerdo(1);
                        break;
                }
            }
        });


        return v;

    }
}

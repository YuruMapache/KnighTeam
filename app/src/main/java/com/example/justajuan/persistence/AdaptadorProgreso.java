package com.example.justajuan.persistence;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.justajuan.R;
import com.example.justajuan.model.Material;
import com.example.justajuan.model.Objeto;

import java.util.ArrayList;


public class AdaptadorProgreso extends ArrayAdapter {
    private ArrayList<Objeto> listaObjetos =new ArrayList<>();

    public AdaptadorProgreso(Context context, int textViewResourceId, ArrayList objects){
        super(context,textViewResourceId,objects);
        listaObjetos=objects;
    }
    @Override
    public int getCount(){
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.gridview_recursos_feudo, null);
        TextView textoNombre = (TextView) v.findViewById(R.id.nombreObjetoCreandose);
        ImageView imageView = (ImageView) v.findViewById(R.id.imagenObjetoCreandose);
        TextView porcentajeTexto = (TextView) v.findViewById(R.id.porcentajeProgreso);
        ProgressBar barraProgreso=(ProgressBar) v.findViewById(R.id.barraProgresoObjeto) ;
        imageView.setImageResource(listaObjetos.get(position).getIdDrawable());
        textoNombre.setText(listaObjetos.get(position).getNombre());
        int total= 360000*listaObjetos.get(position).getTiempo();
        int porcentaje= (int) (listaObjetos.get(position).getTiempoQueFalta()*100)/total;
        porcentajeTexto.setText(String.valueOf(porcentaje)+"%");
        barraProgreso.setProgress(porcentaje);




        return v;

    }
    public void setListaObjetos(ArrayList objects){
        listaObjetos=objects;
    }






}
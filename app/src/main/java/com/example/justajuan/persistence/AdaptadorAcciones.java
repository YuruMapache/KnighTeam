package com.example.justajuan.persistence;

import android.content.Context;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;

import com.example.justajuan.R;
import com.example.justajuan.model.Material;
import com.example.justajuan.model.Objeto;
import com.example.justajuan.model.Sesion;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firestore.v1.StructuredAggregationQuery;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AdaptadorAcciones extends ArrayAdapter {
    private ArrayList<Objeto> listaObjetos;
    private String numLobby;

    public AdaptadorAcciones(Context context, int textViewResourceId, ArrayList objects,String numLobby){
        super(context,textViewResourceId,objects);
        listaObjetos=objects;
        this.numLobby=numLobby;
    }
    @Override
    public int getCount(){
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.gridview_acciones, null);
        ImageView imageView = (ImageView) v.findViewById(R.id.ImagenObjeto);
        TextView textoNombre = (TextView) v.findViewById(R.id.NombreObjeto);
        GridLayout listaMateriales = (GridLayout) v.findViewById(R.id.ListaMateriales);
        AppCompatButton boton= (AppCompatButton) v.findViewById(R.id.botonCrearObjeto);
        imageView.setImageResource(listaObjetos.get(position).getIdDrawable());
        textoNombre.setText(listaObjetos.get(position).getNombre());
        int j=0;
        GridLayout.LayoutParams params;
        for (String i : listaObjetos.get(position).getPrecio().keySet()){
            TextView tv= new TextView(getContext());
            tv.setText(i);
            params = new GridLayout.LayoutParams(GridLayout.spec(j,GridLayout.CENTER),GridLayout.spec(0,GridLayout.CENTER));
            tv.setLayoutParams(params);
            listaMateriales.addView(tv);
            TextView cantidad= new TextView(getContext());
            cantidad.setText(listaObjetos.get(position).getPrecio().get(i));
            params= new GridLayout.LayoutParams(GridLayout.spec(j,GridLayout.CENTER),GridLayout.spec(1,GridLayout.CENTER));
            cantidad.setLayoutParams(params);
            listaMateriales.addView(cantidad);
            j++;
        }



        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CountDownTimer contador= new CountDownTimer(360000*listaObjetos.get(position).getTiempo(),1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {

                        Objeto objeto= listaObjetos.get(position);
                        objeto.setContador(null);
                        FirebaseDatabase.getInstance().getReference().child("Inventario").
                                child(numLobby).child(listaObjetos.get(position).getNombre()).setValue(objeto);
                    }
                };

                listaObjetos.get(position).setContador(contador);
                listaObjetos.get(position).getContador().start();

            }
        });
        return v;

    }
    public void setListaObjetos(ArrayList objects){
        listaObjetos=objects;
    }






}

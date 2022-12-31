package com.example.justajuan.adapter;

import android.content.Context;
import android.os.CountDownTimer;
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
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class AdaptadorAcciones extends ArrayAdapter<Objeto> {
    private ArrayList<Objeto> listaObjetos;
    private final String numLobby;
    private final ArrayList<Objeto> objetoCreandose;
    private final ArrayList<Material> materiales;

    public AdaptadorAcciones(Context context, int textViewResourceId, ArrayList<Objeto> objects, int numLobby, ArrayList<Objeto> objetoCreandose,ArrayList<Material> materiales){
        super(context,textViewResourceId,objects);
        listaObjetos=objects;
        this.numLobby= String.valueOf(numLobby);
        this.objetoCreandose=objetoCreandose;
        this.materiales=materiales;
    }
    @Override
    public int getCount(){
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v;
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
            cantidad.setText(String.valueOf(listaObjetos.get(position).getPrecio().get(i)));
            params= new GridLayout.LayoutParams(GridLayout.spec(j,GridLayout.CENTER),GridLayout.spec(1,GridLayout.CENTER));
            cantidad.setLayoutParams(params);
            listaMateriales.addView(cantidad);
            j++;
        }



        boton.setOnClickListener(v1 -> {
            AtomicBoolean flag = new AtomicBoolean(true);
            listaObjetos.get(position).getPrecio().forEach((key, value) -> {
                for (Material m : materiales) {
                    if (m.getName().equals(key) && m.getCantidad() < value) {
                        flag.set(false);
                    }
                }

            });


            if (flag.get()) {
                CountDownTimer contador = new CountDownTimer(180000L * listaObjetos.get(position).getTiempo(), 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                        listaObjetos.get(position).setTiempoQueFalta(millisUntilFinished);

                    }

                    @Override
                    public void onFinish() {

                        Objeto objeto = listaObjetos.get(position);
                        objeto.setContador(null);
                        FirebaseDatabase.getInstance().getReference().child("Inventario").
                                child(numLobby).child(listaObjetos.get(position).getNombre()).setValue(objeto);
                        objetoCreandose.remove(listaObjetos.get(position));
                    }
                };

                listaObjetos.get(position).getPrecio().forEach((key, value) -> {
                    for (Material m : materiales) {
                        if (m.getName().equals(key)) {
                            m.setCantidad(m.getCantidad()-value);
                        }
                    }

                });


                for(Material m: materiales) {
                    FirebaseDatabase.getInstance().getReference().child("Materiales").child(numLobby).child(m.getName()).setValue(m);
                }
                boton.setText(R.string.creando);
                listaObjetos.get(position).setContador(contador);
                listaObjetos.get(position).getContador().start();
                objetoCreandose.add(listaObjetos.get(position));


            }
        });
        return v;

    }
    public void setListaObjetos(ArrayList<Objeto> objects){
        listaObjetos=objects;
    }






}

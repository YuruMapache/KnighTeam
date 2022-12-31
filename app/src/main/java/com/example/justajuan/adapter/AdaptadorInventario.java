package com.example.justajuan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;

import com.example.justajuan.R;
import com.example.justajuan.model.Caballero;
import com.example.justajuan.model.Objeto;

import java.util.ArrayList;


public class AdaptadorInventario extends ArrayAdapter<Objeto> {
    private ArrayList<Objeto> listaObjetos;
    private final Caballero caballero;

    public AdaptadorInventario(Context context, int textViewResourceId, ArrayList<Objeto> objects, Caballero caballero){
        super(context,textViewResourceId,objects);
        listaObjetos=objects;
        this.caballero=caballero;
    }
    @Override
    public int getCount(){
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.gridview_inventario, null);
        TextView textoNombre = (TextView) v.findViewById(R.id.nombreInventario);
        ImageView imageView = (ImageView) v.findViewById(R.id.imagenInventario);
        AppCompatButton boton= (AppCompatButton) v.findViewById(R.id.botonObjetoInventario);
        imageView.setImageResource(listaObjetos.get(position).getIdDrawable());
        textoNombre.setText(listaObjetos.get(position).getNombre());

        boton.setOnClickListener(v1 -> {
            if(caballero.getEquipado().contains(listaObjetos.get(position))){
                boton.setText(R.string.equipado);
            }
            boton.getText().toString();
            for (int i=0; i<caballero.getEquipado().size();i++){
                boton.setText(R.string.equipar);
                if (caballero.getEquipado().get(i).getNombre().equals(listaObjetos.get(position).getNombre())){
                    caballero.getEquipado().remove(i);
                }
            }
        });
        return v;
    }

    public void setListaObjetos(ArrayList<Objeto> objects){
        listaObjetos=objects;
    }
}
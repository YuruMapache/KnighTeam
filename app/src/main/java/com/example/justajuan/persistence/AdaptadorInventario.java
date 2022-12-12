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

import androidx.appcompat.widget.AppCompatButton;

import com.example.justajuan.R;
import com.example.justajuan.model.Caballero;
import com.example.justajuan.model.Material;
import com.example.justajuan.model.Objeto;

import java.util.ArrayList;


public class AdaptadorInventario extends ArrayAdapter {
    private ArrayList<Objeto> listaObjetos =new ArrayList<>();
    private Caballero caballero;

    public AdaptadorInventario(Context context, int textViewResourceId, ArrayList objects, Caballero caballero){
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

        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.gridview_inventario, null);
        TextView textoNombre = (TextView) v.findViewById(R.id.nombreInventario);
        ImageView imageView = (ImageView) v.findViewById(R.id.imagenInventario);
        AppCompatButton boton= (AppCompatButton) v.findViewById(R.id.botonObjetoInventario);
        imageView.setImageResource(listaObjetos.get(position).getIdDrawable());
        textoNombre.setText(listaObjetos.get(position).getNombre());

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (boton.getText().toString().equals("Equipar")) {
                    boton.setText("Equipado");
                    caballero.getEquipado().add(listaObjetos.get(position));
                    caballero.setAtaque(caballero.getAtaque()+listaObjetos.get(position).getAtaque());
                    caballero.setSalud(caballero.getSalud()+listaObjetos.get(position).getSalud());
                    caballero.setEstamina(caballero.getEstamina()+listaObjetos.get(position).getEstamina());
                }else{
                    for (int i=0; i<caballero.getEquipado().size();i++){
                        boton.setText("Equipar");
                        if (caballero.getEquipado().get(i).getNombre().equals(listaObjetos.get(position).getNombre())){
                            caballero.getEquipado().remove(i);
                        }
                    }
                }
            }
        });






        return v;

    }
    public void setListaObjetos(ArrayList objects){
        listaObjetos=objects;
    }






}
package com.example.justajuan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import objeto.Objeto;

public class PantallaHerreroActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
     DatabaseReference database = FirebaseDatabase.getInstance("https://justa-juan-default-rtdb.europe-west1.firebasedatabase.app/").getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // remove title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pantalla_herrero);
        final Button botonPrueba= findViewById(R.id.acciones_boton);




        botonPrueba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Nombre="AAAA";
                Objeto obj= new Objeto();
                //obj.setNombre("Obsidiana");
                obj.setClase("Herrero");
                obj.setDescripcion("Material comun");
                obj.setSalud(3);
                obj.setAtaque(1);
                obj.setDefensa(0);
                obj.setCansancio(-1);
                obj.setMotivacion(7);
                obj.setTiempoCreacion("5 min");
                String token="Objeto/"+Nombre;
                database.child(token).setValue(obj);
            }
        });

    }


/*

    public void basicReadWrite() {
        // [START write_message]
        // Write a message to the database




        // [END write_message]

        // [START read_message]
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }
*/



}
package com.example.justajuan.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.justajuan.R;
import com.example.justajuan.model.Rol;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PantallaGestorRolesActivity extends AppCompatActivity {

    private TextView rolSeleccionado;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pantalla_gestor_roles);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        rolSeleccionado = findViewById(R.id.rolEscogido);
        rolSeleccionado.setText(String.format("TU ROL ASIGNADO ES:\n %s", getRol()));


    }


    public String getRol() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            return extras.getString("rol");
        }
        return null;
    }
}
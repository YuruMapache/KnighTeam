package com.example.justajuan.persistence;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.example.justajuan.model.Cuestionario;
import com.example.justajuan.model.Pregunta;
import com.example.justajuan.ui.PantallaCuestionarioResultados;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CuestionarioDAO {
    private final static int TOTAL_USERS = 5;

    /**
     * Escribe los resultados del cuestionario en la base de datos.
     *
     * @param nlobby Numero de sala
     * @param rol    Rol
     * @param cues   Cuestionario a guardar
     */
    public static void setForm(int nlobby, int rol, Cuestionario cues) {
        FirebaseDatabase fd = FirebaseDatabase.getInstance();
        DatabaseReference dr = fd.getReference().child("Cuestionarios").child(String.valueOf(nlobby)).child(String.valueOf(rol));
        List<Pregunta> lista = cues.getPreguntas();
        for (Pregunta p : lista) {
            dr.child(String.valueOf(p.getId())).setValue(p.getValor());
        }
    }

    public static void getResults(int numlobby, final Activity activity) {
        Map<Integer, Float> res = new HashMap<>();
        FirebaseDatabase fd = FirebaseDatabase.getInstance();
        DatabaseReference dr = fd.getReference().child("Cuestionarios").child(String.valueOf(numlobby));

        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long count = dataSnapshot.getChildrenCount();
                if (count == TOTAL_USERS) {
                    for (DataSnapshot c : dataSnapshot.getChildren()) {
                        for (DataSnapshot dat : c.getChildren()) {
                            int key = Integer.parseInt(Objects.requireNonNull(dat.getKey()));
                            float value = (long) dat.getValue();
                            if (res.containsKey(key)) {
                                Float val = res.get(key);
                                if (val != null) {
                                    res.put(key, value + val);
                                }
                            } else {
                                res.put(key, value);
                            }
                        }
                    }
                }
                res.forEach((key, value) -> res.put(key, value / 5));
                Intent intent = new Intent(activity, PantallaCuestionarioResultados.class);
                intent.putExtras(activity.getIntent().getExtras());
                intent.putExtra("res", (Serializable) res);
                activity.startActivity(intent);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error
            }
        });
    }


    /*//EN DESARROLLO
    public static List<Cuestionario> getAllForms(int nlobby) {
        FirebaseDatabase fd = FirebaseDatabase.getInstance();
        DatabaseReference dr = fd.getReference().child("Cuestionarios").child(String.valueOf(nlobby));
        final List<Cuestionario> lista = new ArrayList<>();
        dr.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                snapshot.getChildren().forEach(userFormSnap -> {
                    Cuestionario tmpc = new Cuestionario();
                    userFormSnap.getChildren().forEach(childSnapshot -> {
                        Pregunta tmpp = new Pregunta(Integer.parseInt(childSnapshot.getKey()), (Integer) childSnapshot.getValue());
                        tmpc.addPregunta(tmpp);
                    });
                    lista.add(tmpc);
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Mostrar un mensaje de error
            }
        });
        return lista;
    }

    //EN DESARROLLO
    public static void getFormDescription(int id) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference preguntasRef = db.collection("preguntas");

        Map<String, Object> pregunta = new HashMap<>();
        pregunta.put("texto", "¿Cuál es tu color favorito?");

        preguntasRef.add(pregunta)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        // Pregunta agregada con éxito
                    }
                })
                .addOnFailureListener(e -> {
                    // Error al agregar la pregunta
                });

    }

    //EN DESARROLLO
    public static void getPreguntas(int pini, int pfin) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference preguntasRef = db.collection("preguntas");

        Map<String, Object> pregunta = new HashMap<>();
        pregunta.put("texto", "¿Cuál es tu color favorito?");

        preguntasRef.add(pregunta)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        // Pregunta agregada con éxito
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Error al agregar la pregunta
                    }
                });
    }

    //EN DESARROLLO
    public static void getPreguntasByIdRange(int minId, int maxId) {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        CollectionReference preguntasRef = firestore.collection("Preguntas");
        preguntasRef.document("1")
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    // El documento se ha obtenido correctamente
                    // Puedes acceder a los campos del documento usando documentSnapshot.get("campo")
                    // Por ejemplo, para obtener el campo "nombre", puedes hacer lo siguiente:
                    String nombre = (String) documentSnapshot.get("Descripcion");
                })
                .addOnFailureListener(e -> {
                    Log.d("FIRESTORE", "FALLO EN FIRESTORE");
                    // Ha habido un error al obtener el documento
                });

    }

    //EN DESAROLLO
    public static String getPreguntaDescription(FirebaseFirestore firestore, String key) {
        DocumentReference preguntaRef = firestore.collection("Preguntas").document(key);
        String description = "";

        preguntaRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    String description1 = document.getString("Descripcion");
                } else {
                    Log.d("FIRESTORE DEBUG", "Document with ID " + key + " does not exist");
                }
            } else {
                Log.w("FIRESTORE DEBUG", "Error getting document with ID " + key, task.getException());
            }
        });

        return description;
    }


    public static void setPreguntas(Context context) {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        CollectionReference preguntasRef = firestore.collection("Preguntas");

        Resources res = context.getResources();
        String[] preguntas = res.getStringArray(R.array.preguntas);

        for (int i = 0; i < preguntas.length; i++) {
            String id = String.valueOf(i);

            // Crea un objeto Map con los datos de la pregunta
            Map<String, Object> preguntaMap = new HashMap<>();
            preguntaMap.put("Descripcion", preguntas[i]);

            firestore.collection("Preguntas").document(id).set(preguntaMap)
                    .addOnSuccessListener(aVoid -> Log.d("FIRESTORE DEBUG", "Documento escrito con éxito"))
                    .addOnFailureListener(e -> Log.w("FIRESTORE DEBUG", "Error al escribir el documento", e));

        }

    }*/
}

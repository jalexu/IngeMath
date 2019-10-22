package com.estudiates.ingemath.presentador;

import android.util.Log;

import com.estudiates.ingemath.modelo.Estudiante;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AlmacenandoEnFirebase {

    private static final String TAG = "Almacenando";
    private List<String> datosEstudiante;

    private static final String ESTUDIANTES_NODE = "Estudiantes";
    private DatabaseReference databaseReference;

    public void guardandoEnFireBase(Estudiante estudiante){
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference(); //obtiene la direccion de la base de datos



        Estudiante estudiantes = new Estudiante(estudiante.getNombre(), estudiante.getGenenero(), estudiante.getFechaNacimiento(),
                estudiante.getNumeroTelefonico(), estudiante.getCorreo());

        databaseReference.child(ESTUDIANTES_NODE).child(user.getUid()).setValue(estudiantes);


    }

    public List<String> traerDatosEstudiante(){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        datosEstudiante = new ArrayList<>();

        databaseReference.child(ESTUDIANTES_NODE).child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                datosEstudiante.clear();
                if(dataSnapshot.exists()){
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        Estudiante estudiante = snapshot.getValue(Estudiante.class);
                        Log.w(TAG, "Nombre estudiante"+ estudiante.getNombre());
                        datosEstudiante.add(estudiante.getNombre());
                        datosEstudiante.add(estudiante.getGenenero());
                        datosEstudiante.add(estudiante.getFechaNacimiento());
                        datosEstudiante.add(estudiante.getNumeroTelefonico());
                        datosEstudiante.add(estudiante.getCorreo());

                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return datosEstudiante;
    }
}

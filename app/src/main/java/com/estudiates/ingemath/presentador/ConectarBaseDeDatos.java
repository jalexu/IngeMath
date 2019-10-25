package com.estudiates.ingemath.presentador;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.Executor;

public class ConectarBaseDeDatos {

    private boolean inicioSesion = false;
    private boolean conectarFirebase = false;

    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private DatabaseReference databaseReference;
    private FirebaseUser user;


    public ConectarBaseDeDatos() {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        user = firebaseAuth.getCurrentUser();
    }


    public boolean conectarFirebase(){

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if(firebaseUser!=null){
                    conectarFirebase = true;
                }else{
                    conectarFirebase = false;
                }
            }
        };

        return conectarFirebase;

    }


    public boolean iniciarSesion(String correo, String contrasena){

        firebaseAuth.signInWithEmailAndPassword(correo,contrasena).addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    inicioSesion = true;
                    //Toast.makeText(MainActivity.class, "Atenticación éxitosa", Toast.LENGTH_SHORT).show();
                }else{
                    inicioSesion = false;
                    //Toast.makeText(MainActivity.this, "Usuario no registra", Toast.LENGTH_SHORT).show();
                }

            }
        });
        return inicioSesion;
    }


    public FirebaseDatabase getFirebaseDatabase() {
        return firebaseDatabase;
    }

    public String getUserId() {
        return user.getUid();
    }
}

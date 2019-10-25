package com.estudiates.ingemath;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.estudiates.ingemath.presentador.ConectarBaseDeDatos;
import com.estudiates.ingemath.utils.Validaciones;
import com.estudiates.ingemath.vista.CardsActivity;
import com.estudiates.ingemath.vista.CrearCuenta;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {



    private Button btnCrearCuenta;
    private static final String TAG = "Viene del Main Activity";
    private Button btnIniciarSesion;
    private EditText edCorreo, edContrasena;
    private String sCorreo, sContrasena;

    private ConectarBaseDeDatos conectarBaseDeDatos = new ConectarBaseDeDatos();
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        btnIniciarSesion=findViewById(R.id.btnIngreso);

        firebaseAuth = FirebaseAuth.getInstance();

        conectarFirebase();

        btnCrearCuenta = findViewById(R.id.btnregistrarseInicioSesion);


        //conectarBaseDeDatos.conectarFirebase();

        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tomarDatos();
                convertir();
                validarIniciarSesion();
                //irASesionInicida();
            }
        });

        btnCrearCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irAcrearCuenta();
            }
        });
    }

    public void irAcrearCuenta(){
        Intent intent = new Intent(this, CrearCuenta.class);
        startActivity(intent);
    }

    private void iniciarSesion(final String correo, final String contrasena){
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        firebaseAuth.signInWithEmailAndPassword(correo,contrasena).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Atenticación éxitosa", Toast.LENGTH_SHORT).show();
                    irASesionInicida();
                }else{
                    Toast.makeText(MainActivity.this, "Usuario no resgistrado", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void conectarFirebase(){

        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if(firebaseUser!=null){
                    Log.w(TAG, "onAuthStateChanged - logeado "+ firebaseUser.getUid());
                    Log.w(TAG, "onAuthStateChanged - logeado "+ firebaseUser.getEmail());
                }else{
                    Log.w(TAG, "onAuthStateChanged - Sesión ");
                }
            }
        };

    }

    public void irASesionInicida(){
        Intent intent = new Intent(this, CardsActivity.class);
        startActivity(intent);
    }


    private  void validarIniciarSesion(){

        if(Validaciones.validarCorreo(this.sCorreo)&&
                Validaciones.validarContrasena(this.sContrasena)){

            //conectarBaseDeDatos.iniciarSesion(sCorreo,sContrasena);
            iniciarSesion(sCorreo,sContrasena);

        }else if(!Validaciones.validarCorreo(this.sCorreo) &&
                Validaciones.validarContrasena(this.sContrasena)){

            Toast.makeText(this,"Correo no válido",Toast.LENGTH_SHORT).show();

        }else if(Validaciones.validarCorreo(this.sCorreo) &&
                !Validaciones.validarContrasena(this.sContrasena)){

            Toast.makeText(this,"Contraseña no valida",Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(this,"Usuario y contraseña son obligatorios para ingresar",Toast.LENGTH_LONG).show();
        }

    }


    private void tomarDatos(){
        this.edCorreo = findViewById(R.id.usuarioInicioSesion);
        this.edContrasena = findViewById(R.id.contrasenaInicioSesion);
    }

    private void convertir(){
        this.sCorreo = edCorreo.getText().toString();
        this.sContrasena = edContrasena.getText().toString();

    }

    @Override
    protected void onStart() {
        super.onStart();
        //conectarBaseDeDatos.getFirebaseAuth().addAuthStateListener(conectarBaseDeDatos.getAuthStateListener());
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //conectarBaseDeDatos.getFirebaseAuth().removeAuthStateListener(conectarBaseDeDatos.getAuthStateListener());
        firebaseAuth.removeAuthStateListener(authStateListener);
    }


}

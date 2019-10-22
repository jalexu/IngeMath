package com.estudiates.ingemath.vista;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.estudiates.ingemath.MainActivity;
import com.estudiates.ingemath.R;
import com.estudiates.ingemath.modelo.Estudiante;
import com.estudiates.ingemath.presentador.AlmacenandoEnFirebase;
import com.estudiates.ingemath.utils.Validaciones;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CrearCuenta extends AppCompatActivity {

    private static final String TAG = "CrearCuentaActivity";
    private FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    //lisener esta pendiente de cambio de sesion
    private  FirebaseAuth.AuthStateListener authStateListener;

    Calendar calendario = Calendar.getInstance();
    private Estudiante estudiante;

    private Button btnCancelar, btnRegistroFormularioRegistro;

    private EditText edNombres, edFechaNacimiento, edTelefono, edEmail, edContrasena, edConfirmarContrasena;
    private Spinner edGenero;
    private String sNombres, sFechaNacimiento,sGenero, sTelefono, sEmail, sContrasena, sConfirmarContrasena;
    private static final String[] paths = {"","Femenino", "Masculino"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_cuenta);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Se apunta firebase ala conexion a la base de datos
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        edGenero = findViewById(R.id.genero);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(CrearCuenta.this,
                android.R.layout.simple_spinner_dropdown_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        edGenero.setAdapter(adapter);

        edFechaNacimiento = findViewById(R.id.fechaNacimiento);
        btnRegistroFormularioRegistro = findViewById(R.id.registrarseFormularioIncripcion);

        edFechaNacimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(CrearCuenta.this, date, calendario
                        .get(Calendar.YEAR), calendario.get(Calendar.MONTH),
                        calendario.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if(firebaseUser != null){
                    Log.w(TAG,"Usuario Registrado"+ firebaseUser.getEmail());
                }else{
                    Log.w(TAG,"Usuario no registra");
                }
            }
        };

        btnCancelar = findViewById(R.id.cancelarIncripcion);

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnRegistroFormularioRegistro.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                tomarDatos();
                convertir();
                crearEstudianteValidadoCampos();
            }
        });
    }


    private  void crearEstudianteValidadoCampos(){

        if(sEmail.isEmpty() && sContrasena.isEmpty()){
            Toast.makeText(this,"Correo y contraseña son obligatorios!",Toast.LENGTH_SHORT).show();

        }else if(Validaciones.validarContrasena(this.sContrasena)) {

            if(Validaciones.validarCorreo(this.sEmail)){
                registrarEnBaseDeDatos();

            }else if(!Validaciones.validarCorreo(this.sEmail)){
                Toast.makeText(this,"Correo no válido",Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(this,"Contraseña no válida",Toast.LENGTH_SHORT).show();
        }

    }


    private void registrarEnBaseDeDatos() {
        //Craear usuario y contraseña en firebase
        firebaseAuth.createUserWithEmailAndPassword(sEmail,sContrasena)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            guardarEstudiante();
                            Toast.makeText(CrearCuenta.this,"Cuenta creada éxitosamente",Toast.LENGTH_LONG).show();
                            AlmacenandoEnFirebase almacenandoEnFirebase = new AlmacenandoEnFirebase();
                            almacenandoEnFirebase.guardandoEnFireBase(estudiante);
                            //pararProceso();
                            regresarAlMain();
                        }else{
                            Toast.makeText(CrearCuenta.this,"Usuario ya existe, por favor reestablezca su contraseña.",Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }


    private void guardarEstudiante(){
        estudiante = new Estudiante(sNombres,sGenero,sFechaNacimiento,sTelefono,sEmail);

    }


    private void tomarDatos(){
        edNombres = findViewById(R.id.nombresCliente);
        edGenero = findViewById(R.id.genero);
        edFechaNacimiento = findViewById(R.id.fechaNacimiento);
        edTelefono = findViewById(R.id.telefonoCliente);
        edEmail=findViewById(R.id.correoCliente);
        edContrasena = findViewById(R.id.contrasena1Cliente);
        edConfirmarContrasena = findViewById(R.id.contrasena2Cliente);

    }
    private void convertir(){
        sNombres = edNombres.getText().toString();
        sGenero = edGenero.getSelectedItem().toString();
        sFechaNacimiento = edFechaNacimiento.getText().toString();
        sTelefono = edTelefono.getText().toString();
        sEmail=edEmail.getText().toString();
        sContrasena = edContrasena.getText().toString();
        sConfirmarContrasena = edConfirmarContrasena.getText().toString();
    }



    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            calendario.set(Calendar.YEAR, year);
            calendario.set(Calendar.MONTH, monthOfYear);
            calendario.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            actualizarInput();
        }

    };

    private void actualizarInput() {
        String formatoDeFecha = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        edFechaNacimiento.setText(sdf.format(calendario.getTime()));
    }

    public void regresarAlMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}

package com.estudiates.ingemath;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.estudiates.ingemath.vista.CrearCuenta;

public class MainActivity extends AppCompatActivity {

    private Button btnCrearCuenta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        btnCrearCuenta = findViewById(R.id.btnregistrarseInicioSesion);

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
}

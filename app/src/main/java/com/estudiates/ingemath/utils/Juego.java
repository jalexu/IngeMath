package com.estudiates.ingemath.utils;

import android.media.Image;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.estudiates.ingemath.R;
import com.estudiates.ingemath.modelo.Images;

import java.util.ArrayList;
import java.util.Collections;

public class Juego extends AppCompatActivity {

    ImageButton el0, el1, el2, el3, el4, el5;

    //los botones del menú
    Button reiniciar, salir;

    //las imagenes
    Images imagenes[];

    //se guardan duplicadas en un array
    ImageButton [] botonera = new ImageButton[6];

    //imagen de fondo;
    int fondo;

    //para barajar
    //el vector que recoge el resultado del "barajamiento" (o "barajación" o "barajancia" o como leshes se diga)
    ArrayList<Integer> arrayBarajado;

    //COMPARACIÓN
    //los botones que se han pulsado y se comparan
    ImageButton primero;
    //posiciones de las imágenes a comparar en el vector de imágenes
    int numeroPrimero, numeroSegundo;
    //durante un segundo se bloquea el juego y no se puede pulsar ningún botón
    boolean bloqueo = false;

    //para controlar la pausa de un segundo
    final Handler handler = new Handler();

    //finalmente, el número de aciertos y la puntuación
    int aciertos=0;
    int puntuacion=0;
    TextView textoPuntuacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);
        cargarImagenes();
        botonesMenu();
        iniciar();
    }


    public void cargarImagenes(){
        Images image1 = new Images(R.drawable.funcion1,1);
        Images image2 = new Images(R.drawable.funcion2,1);
        Images image3 = new Images(R.drawable.funcion3,1);
        Images image4 = new Images(R.drawable.problema1,2);
        Images image5 = new Images(R.drawable.problema2,2);
        Images image6 = new Images(R.drawable.problema3,2);
        imagenes = new Images[]{

                image1,
                image2,
                image3,
                image4,
                image5,
                image6

        };

        fondo = R.drawable.ingemath1;
    }

    public ArrayList<Integer> barajar(int longitud) {

        ArrayList resultadoA = new ArrayList<Integer>();

        for(int i=0; i<longitud; i++)
            resultadoA.add(i % longitud);
        Collections.shuffle(resultadoA);
        return  resultadoA;
    }


    public void cargarBotones(){
        el0 = (ImageButton) findViewById(R.id.boton00);
        botonera[0] = el0;
        el1 = (ImageButton) findViewById(R.id.boton01);
        botonera[1] = el1;
        el2 = (ImageButton) findViewById(R.id.boton02);
        botonera[2] = el2;
        el3 = (ImageButton) findViewById(R.id.boton03);
        botonera[3] = el3;
        el4 = (ImageButton) findViewById(R.id.boton04);
        botonera[4] = el4;
        el5 = (ImageButton) findViewById(R.id.boton05);
        botonera[5] = el5;

        textoPuntuacion = (TextView)findViewById(R.id.textoPuntuacion);
        textoPuntuacion.setText("Puntuación: " + puntuacion);
    }

    public void botonesMenu(){
        reiniciar = (Button) findViewById(R.id.Reiniciar);
        salir = (Button) findViewById(R.id.Salir);
        reiniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciar();
            }
        });
        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    public void comprobar(int i, final ImageButton imgb){
        if(primero==null){//ningún botón ha sido pulsado
            //el botón primero será el que acabamos de pulsar
            primero = imgb;
            /*le asignamos la imagen del vector imágenes situada
            en la posición vectorBarajado[i], que tendrá un valor entre 0 y 7*/
            primero.setScaleType(ImageView.ScaleType.CENTER_CROP);
            primero.setImageResource(imagenes[arrayBarajado.get(i)].getImagen());
            //bloqueamos el botón
            primero.setEnabled(false);
            //almacenamos el valor de vectorBarajado[i]
            numeroPrimero=imagenes[arrayBarajado.get(i)].getPuntaje();
        }else{//ya hay un botón descubierto
            //bloqueamos todos los demás
            bloqueo=true;
            //el botón segundo será el que acabamos de pulsar
            /*le asignamos la imagen del vector imágenes situada
            en la posición vectorBarajado[i], que tendrá un valor entre 0 y 7*/
            imgb.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imgb.setImageResource(imagenes[arrayBarajado.get(i)].getImagen());
            //bloqueamos el botón
            imgb.setEnabled(false);
            //almacenamos el valor de vectorBarajado[i]
            numeroSegundo=imagenes[arrayBarajado.get(i)].getPuntaje();
            //if(primero.getDrawable().getConstantState().equals(imgb.getDrawable().getConstantState())){
            if(numeroPrimero==numeroSegundo){//si coincide el valor los dejamos destapados
                //reiniciamos
                primero=null;
                bloqueo=false;
                //aumentamos los aciertos y la puntuación
                aciertos++;
                puntuacion++;
                textoPuntuacion.setText("Puntuación: " + puntuacion);
                //al llegar a8 aciertos se ha ganado el juego
                if(aciertos==2){
                    Toast toast = Toast.makeText(getApplicationContext(), "Has ganado!!", Toast.LENGTH_LONG);
                    toast.show();
                }

            }else{//si NO coincide el valor los volvemos a tapar al cabo de un segundo
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //les ponemos la imagen de fondo
                        primero.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        primero.setImageResource(R.drawable.ingemath1);
                        imgb.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        imgb.setImageResource(R.drawable.ingemath1);
                        //los volvemos a habilitar
                        primero.setEnabled(true);
                        imgb.setEnabled(true);
                        //reiniciamos la variables auxiliaares
                        primero = null;
                        bloqueo = false;
                        //restamos uno a la puntuación
                        if (puntuacion > 0) {
                            puntuacion--;
                            textoPuntuacion.setText("Puntuación: " + puntuacion);
                        }
                    }
                }, 2000);//al cabo de un segundo
            }


        }

    }

    public void iniciar(){
        arrayBarajado = barajar(imagenes.length);
        cargarBotones();

        //MOSTRAMOS LA IMAGEN
        for(int i=0; i<botonera.length; i++) {
            botonera[i].setScaleType(ImageView.ScaleType.CENTER_CROP);
            botonera[i].setImageResource(imagenes[arrayBarajado.get(i)].getImagen());
        }

        //Y EN UN SEGUNDO LA OCULTAMOS
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < botonera.length; i++) {
                    botonera[i].setScaleType(ImageView.ScaleType.CENTER_CROP);
                    botonera[i].setImageResource(fondo);
                }
            }
        }, 2000);

        //AÑADIMOS LOS EVENTOS A LOS BOTONES DEL JUEGO
        for(int i=0; i < 6; i++){
            final int j=i;
            botonera[i].setEnabled(true);
            botonera[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!bloqueo)
                        comprobar(j, botonera[j]);
                }
            });
        }
        aciertos=0;
        puntuacion=0;
        textoPuntuacion.setText("Puntuación: " + puntuacion);
    }
}

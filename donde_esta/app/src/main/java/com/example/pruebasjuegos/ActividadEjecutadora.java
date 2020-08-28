package com.example.pruebasjuegos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.pruebasjuegos.baseDatos.OperacionesBD;
import com.example.pruebasjuegos.motor.ClasesAuxiliares;

public class ActividadEjecutadora extends AppCompatActivity {

    int nivelMaximo = 1;
    int putuacionMaxima = 0;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_ejecutadora);

        TextView tv = findViewById(R.id.tPuntuacion);



        IniciaMusicaIntro();

        //Base de datos
        OperacionesBD.enviarContexto(this);
        //OperacionesBD.reiniciarBD();
        if (OperacionesBD.existenciaBD("N47")) {
            Log.e("BASEDATOS", "La BD existe. Se cargan los datos críticos.");
            cargarBD();
        } else {
            Log.e("BASEDATOS", "La BD no existe. Se crea.");
            OperacionesBD.inicializarBaseDatos();
        }
        actualizarSituacionPantalla();
    }

    private void cargarBD(){
        //Consulta superado
        this.nivelMaximo = OperacionesBD.consulBD("SELECT nvlAlcanzado FROM n47");
        Log.e("BASEDATOS",""+nivelMaximo);
        //consulta puntuacion
        this.putuacionMaxima = OperacionesBD.consulBD("SELECT maxPuntuacion FROM n47");
        Log.e("BASEDATOS",""+putuacionMaxima);
    }

    private void actualizarSituacionPantalla(){
        switch (nivelMaximo) {
            default:
            case 1:
                findViewById(R.id.B2).setEnabled(false);
                findViewById(R.id.B3).setEnabled(false);
                break;
            case 2:
                findViewById(R.id.B3).setEnabled(false);
                findViewById(R.id.B2).setEnabled(true);
                break;
            case 3:
                findViewById(R.id.B2).setEnabled(true);
                findViewById(R.id.B3).setEnabled(true);
                break;
        }

        if(putuacionMaxima==0)
            findViewById(R.id.tPuntuacion).setVisibility(View.INVISIBLE);
        else{
            TextView tv = findViewById(R.id.tPuntuacion);
            tv.setVisibility(View.VISIBLE);
            tv.setText("HIT SCORE: "+putuacionMaxima);
        }
    }

    //Se lanza una actividad secundaria para que luego sea más sencillo resetearlo todo
    public void ejecutarJuego(View view) {
        Intent i = new Intent(this, MainActivity.class);
        switch (String.valueOf(view.getTag())) {
            case "B1":
                i.putExtra("dificultad", "1");
                break;
            case "B2":
                i.putExtra("dificultad", "2");
                break;
            case "B3":
                i.putExtra("dificultad", "3");
                break;
            default:
                return;
        }
        startActivityForResult(i,0);
    }

    public void reiniciarBD(View view){
        OperacionesBD.reiniciarBD();
        OperacionesBD.inicializarBaseDatos();
        cargarBD();
        actualizarSituacionPantalla();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (resultCode==0){
            cargarBD();
            actualizarSituacionPantalla();
        }
    }

    public void IniciaMusicaIntro(){
        this.mediaPlayer = MediaPlayer.create(this, R.raw.countermove);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.start();
            }
        });
        mediaPlayer.start();
    }

    public void onResume() {
        super.onResume();
        mediaPlayer.start();
    }

    public void onPause() {
        super.onPause();
        mediaPlayer.pause();
    }


}

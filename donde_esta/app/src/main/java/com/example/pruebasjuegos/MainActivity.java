package com.example.pruebasjuegos;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.View;

import com.example.pruebasjuegos.logica.Logica;
import com.example.pruebasjuegos.motor.ClasesAuxiliares;
import com.example.pruebasjuegos.motor.Juego;
import com.example.pruebasjuegos.motor.TileMap;
import com.example.pruebasjuegos.motor.interfaces.sprites.Chara;

public class MainActivity extends AppCompatActivity {

    //Establece el tamaño. Su valor estable obscila entre 0.01/0.2
    final private static double proporcion = 0.09;
    static int dificultad = 0;
    Juego juego;

    public static MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IniciaMusicaIntro();

        //Parametros pantalla - Añadir Botón de salida
        hideSystemUI();

        //Nivel de juego
        dificultad = Integer.parseInt(getIntent().getExtras().getString("dificultad"));
        Log.e("NIVEL",""+dificultad);
        Display display = this.getWindowManager().getDefaultDisplay();

        Object [] objects = ClasesAuxiliares.mapLogicaNivel(this,dificultad,display,proporcion);
        juego = new Juego(this,this,(TileMap)objects[0],(Logica)objects[1],(int[])objects[2],(Chara)objects[3]);
        setContentView(juego);
    }

    public void actualizarUI(){
        hideSystemUI();
    }

    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
        decorView.setOnSystemUiVisibilityChangeListener
                (new View.OnSystemUiVisibilityChangeListener() {
                    @Override
                    public void onSystemUiVisibilityChange(int visibility) {
                        if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                public void run() {
                                    hideSystemUI();
                                }
                            },2000);
                        }
                    }
                });
    }

    public static int obtenerDificultadActual(){
        return dificultad;
    }

    public void IniciaMusicaIntro(){
        this.mediaPlayer = MediaPlayer.create(this, R.raw.big_car_theft);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.start();
            }
        });
        mediaPlayer.start();
    }


    public void onPause() {
        super.onPause();
        juego.fin();
    }

}

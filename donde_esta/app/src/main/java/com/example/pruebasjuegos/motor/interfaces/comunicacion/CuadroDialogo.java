package com.example.pruebasjuegos.motor.interfaces.comunicacion;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.pruebasjuegos.motor.interfaces.InterfaceBasica;

public class CuadroDialogo extends InterfaceBasica {
    private int tamañoTexto;
    private int[] tamañoPantalla;
    private int segundos = 0;
    private final int tiempoMuestra = 2;
    private boolean tiempoDibujo = false;

    public CuadroDialogo(int[] coordenadas) {
        super(coordenadas);
    }

    public CuadroDialogo(int[] coordenadas, int[] tamañoPantalla, int tamañoTexto) {
        super(coordenadas);
        this.tamañoTexto = tamañoTexto;
        this.tamañoPantalla = tamañoPantalla;
    }

    public void actulizarCuadro(int segundos){
        if(tiempoDibujo && this.segundos - segundos >= tiempoMuestra){
            terminarDibujar();
        }
    }

    public void empezarDibujar(int segundos){
        tiempoDibujo = true;
        this.segundos = segundos;
    }

    public void terminarDibujar(){
        tiempoDibujo = false;
        segundos = 0;
    }

    public void mostrarCuadro(Canvas canvas, String texto){
        if (tiempoDibujo){
            Paint p = new Paint();
            p.setColor(Color.argb(150,0,0,0));
            canvas.drawRoundRect(coordenadas[0] - tamañoPantalla[2]*0.5f, coordenadas[1]-tamañoPantalla[3]*0.5f,tamañoPantalla[0]*0.99f, tamañoPantalla[1]*0.9f,
                    20f,20f,p);

            p.setStyle(Paint.Style.FILL);
            p.setTextSize(tamañoTexto*0.60f);
            p.setColor(Color.WHITE);
            canvas.drawText(texto, coordenadas[0], coordenadas[1],p);
        }
    }
}

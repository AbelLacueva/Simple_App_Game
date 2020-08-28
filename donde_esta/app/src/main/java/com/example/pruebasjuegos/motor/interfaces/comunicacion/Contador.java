package com.example.pruebasjuegos.motor.interfaces.comunicacion;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.pruebasjuegos.logica.Logica;
import com.example.pruebasjuegos.motor.interfaces.InterfaceBasica;

public class Contador extends InterfaceBasica {

    private Logica logica;
    private int tamañoTexto;

    public Contador(int[] coordenadas) {
        super(coordenadas);
    }

    public Contador(int[] coordenadas, int tamañoTexto, Logica logica) {
        super(coordenadas);
        this.logica = logica;
        this.tamañoTexto = tamañoTexto;
    }

    public void dibujarCronometro(Canvas canvas){
        Paint p = new Paint();
        int tiempo = logica.getSegundos();
        int minutos = tiempo/60;
        int segundos = tiempo%60;
        String contador = String.format("%02d:%02d",minutos,segundos);
        p.setStyle(Paint.Style.FILL);
        p.setColor(Color.WHITE);
        p.setTextSize(tamañoTexto);
        float  textSize = p.measureText(contador);
        float  textHeight= p.getTextSize();
        float factorX = canvas.getWidth() - (textSize*1.25f);
        float factorY = textHeight;
        canvas.drawText(contador, factorX, factorY,p);
    }
}

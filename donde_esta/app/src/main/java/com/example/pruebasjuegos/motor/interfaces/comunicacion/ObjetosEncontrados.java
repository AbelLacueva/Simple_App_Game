package com.example.pruebasjuegos.motor.interfaces.comunicacion;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.pruebasjuegos.motor.interfaces.InterfaceBasica;

import java.util.ArrayList;

public class ObjetosEncontrados extends InterfaceBasica {

    Bitmap [] bitmaps;
    ArrayList <Integer> encontrados = new ArrayList<>();
    private final int objetosPorLinea = 4;

    public ObjetosEncontrados(int[] coordenadas, Bitmap [] bitmaps) {
        super(coordenadas);
        this.bitmaps = bitmaps;
    }

    public void actulizarEncontrados(ArrayList <Integer> encontrados){
        this.encontrados = encontrados;
    }

    public void dibujarEncontrados(Canvas canvas){
        int cantidadX = 0;
        int cantidadY = 0;
        for(int x:encontrados){
            canvas.drawBitmap(bitmaps[x],0+coordenadas[0]*cantidadX,coordenadas[1]*cantidadY,null);
            ++cantidadX;
            if (cantidadX%objetosPorLinea == 0){
                ++cantidadY;
                cantidadX = 0;
            }

        }
    }
}

package com.example.pruebasjuegos.motor.interfaces;

import android.graphics.Bitmap;

public class InterfaceBasica {

    protected int [] coordenadas;
    protected Bitmap imagen;

    public InterfaceBasica(int[] coordenadas) {
        this.coordenadas = coordenadas;
    }

    public InterfaceBasica(int[] coordenadas, Bitmap imagen) {
        this.coordenadas = coordenadas;
        this.imagen = imagen;
    }


}

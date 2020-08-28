package com.example.pruebasjuegos.motor.interfaces.sprites;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.pruebasjuegos.motor.ClasesAuxiliares;

public class Chara {
    protected Context context;
    protected int [] datosPantalla;
    protected int [] centroMatriz;
    protected int [] orientacionChara;
    protected int contadorMovimiento = 0;
    protected String setCharas;
    protected int ancho;
    protected int alto;
    protected boolean segundaFila = false;
    private int direccionNeutra;

    public Chara(Context context, int [] datospantalla, int[] centroMatriz,
                 int[] orientacionChara, String setCharas,
                 int ancho, int alto) {
        this.context = context;
        this.datosPantalla = datospantalla;
        this.centroMatriz = centroMatriz;
        this.orientacionChara = orientacionChara;
        this.contadorMovimiento = orientacionChara[0] -1;
        this.setCharas = setCharas;
        this.ancho = ancho;
        this.alto = alto;
        this.direccionNeutra = orientacionChara[0];
    }

    public Chara(Context context, int [] datospantalla, int[] centroMatriz,
                 int[] orientacionChara, String setCharas,
                 int ancho, int alto, boolean segundaFila) {
        this.context = context;
        this.datosPantalla = datospantalla;
        this.centroMatriz = centroMatriz;
        this.orientacionChara = orientacionChara;
        this.contadorMovimiento = orientacionChara[0] -1;
        this.setCharas = setCharas;
        this.ancho = ancho;
        this.alto = alto;
        this.direccionNeutra = orientacionChara[0];
        this.segundaFila = segundaFila;
    }

    public void dibujarChara(Canvas c) {
        Paint p = new Paint();
        Bitmap chara = ClasesAuxiliares.obtenerBitmapSinFormato(context,setCharas);
        int unidadSpriteX = chara.getWidth()/ancho;
        int unidadSpriteY = chara.getHeight()/alto;
        int posicionX = unidadSpriteX * orientacionChara[0];
        int posicionY = unidadSpriteY * orientacionChara[1];
        Rect rectSprite = new Rect(posicionX,posicionY,unidadSpriteX+posicionX,unidadSpriteY +posicionY);
        Rect restMapa = new Rect (centroMatriz[0],centroMatriz[1]-datosPantalla[3],centroMatriz[0]+datosPantalla[2],centroMatriz[1]+datosPantalla[3]);
        c.drawBitmap(chara,rectSprite,restMapa,p);
    }

    //Válido unicamente para la primera fila de personajes
    public void empezarMovimiento(int y){
        if (segundaFila)
            orientacionChara [1] = y + 4;
        else
            orientacionChara [1] = y;
    }

    public void moverChara(){
        orientacionChara [0] = contadorMovimiento;
        contadorMovimiento ++;
    }

    public void terminaMovimiento(){
        //Se centra el personaje
        orientacionChara [0] = direccionNeutra;
        //Se reinicia el ciclo
        contadorMovimiento = orientacionChara[0] -1;
    }

    public int[] getOrientacionChara() {
        return orientacionChara;
    }

    public void setOrientacionChara(int[] orientacionChara) {
        this.orientacionChara = orientacionChara;
    }

    public int[] getCentroMatriz() {
        return centroMatriz;
    }

    public void setCentroMatriz(int[] centroMatriz) {
        this.centroMatriz = centroMatriz;
    }

    public int getContadorMovimiento() {
        return contadorMovimiento;
    }

    public void setContadorMovimiento(int contadorMovimiento) {
        this.contadorMovimiento = contadorMovimiento;
    }
}

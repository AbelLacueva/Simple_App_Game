package com.example.pruebasjuegos.motor;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;

public class TileMap {
    private NavegacionPorMatriz matriz;
    private int [] datosPantalla;
    private Bitmap bitmap;

    public TileMap(Context context, int [] datosPantalla,NavegacionPorMatriz matriz, String assetTileMap) {
        this.matriz = matriz;
        this.datosPantalla = datosPantalla;

        try {
            AssetManager mngr = context.getAssets();
            InputStream is = mngr.open(assetTileMap);
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            int ancho = datosPantalla[2]*this.obtenerX();
            int alto = datosPantalla[3]*this.obtenerY();
            this.bitmap = bitmap.createScaledBitmap(bitmap,ancho,alto,true);
        } catch (Exception e) {
            e.printStackTrace();
            this.bitmap = null;
        }
    }

    public int obtenerX(){
        return matriz.getMatriz()[0].length;
    }

    public int obtenerY(){
        return matriz.getMatriz().length;
    }

    public int obtenerAncho(){
        return this.obtenerX() * this.datosPantalla[2];
    }

    public int obtenerAlto(){
        return this.obtenerY() * this.datosPantalla[3];
    }

    public int obtenerValor(int y, int x){
        return matriz.getMatriz()[y][x];
    }

    public NavegacionPorMatriz getMatriz() {
        return matriz;
    }

    public void setMatriz(NavegacionPorMatriz matriz) {
        this.matriz = matriz;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int[] getDatosPantalla() {
        return datosPantalla;
    }

    public void setDatosPantalla(int[] datosPantalla) {
        this.datosPantalla = datosPantalla;
    }
}

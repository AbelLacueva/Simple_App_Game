package com.example.pruebasjuegos.motor.interfaces.sprites;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.pruebasjuegos.motor.ClasesAuxiliares;

public class CharaBallon extends Chara {

    public CharaBallon(Context context, int[] datospantalla,
                       int[] centroMatriz, int[] orientacionChara, String setCharas, int ancho, int alto) {
        super(context, datospantalla, centroMatriz, orientacionChara, setCharas, ancho, alto);
        contadorMovimiento = 0;
    }

    @Override
    public void dibujarChara(Canvas c) {
        Paint p = new Paint();
        Bitmap chara = ClasesAuxiliares.obtenerBitmapSinFormato(context,setCharas);
        int unidadSpriteX = chara.getWidth()/ancho;
        int unidadSpriteY = chara.getHeight()/alto;
        int posicionX = unidadSpriteX * orientacionChara[0];
        int posicionY = unidadSpriteY * orientacionChara[1];
        Rect rectSprite = new Rect(posicionX,posicionY,unidadSpriteX+posicionX,unidadSpriteY +posicionY);
        Rect restMapa = new Rect (centroMatriz[0],centroMatriz[1]-datosPantalla[3]/7,centroMatriz[0]+datosPantalla[2],centroMatriz[1]+datosPantalla[3]);
        c.drawBitmap(chara,rectSprite,restMapa,p);
    }

    @Override
    public void empezarMovimiento(int y) {
        super.empezarMovimiento(y);
    }

    @Override
    public void terminaMovimiento() {
        contadorMovimiento = 0;
    }
}

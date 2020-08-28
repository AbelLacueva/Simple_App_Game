package com.example.pruebasjuegos.motor.interfaces.inicioFinJuego;

import android.graphics.Bitmap;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import com.example.pruebasjuegos.MainActivity;
import com.example.pruebasjuegos.baseDatos.OperacionesBD;
import com.example.pruebasjuegos.motor.ClasesAuxiliares;
import com.example.pruebasjuegos.motor.interfaces.InterfaceBasica;

public class FinPartida extends InterfaceBasica {

    int [] datoPantalla;
    int objetosEncontrados;
    int tiempo;
    int bonusNivel = 0;
    int puntuacionTotal = 0;
    boolean nivelSuperado = false;

    Bitmap imagenBoton;

    public FinPartida(int[] coordenadas) {
        super(coordenadas);
    }

    public FinPartida(int[] datosPantalla, Bitmap imagen, Bitmap imagenBoton, int objetosEncontrados, int tiempo, Boolean nivelSuperado) {
        super(datosPantalla,imagen);
        if (nivelSuperado) {
            bonusNivel = 10*MainActivity.obtenerDificultadActual();
        }
        this.datoPantalla = datosPantalla;
        int x =(int) (datosPantalla[0] * 0.5f);
        int y = (x*imagen.getHeight())/imagen.getWidth();
        this.imagen = imagen.createScaledBitmap(imagen,x,y,true);
        this.imagenBoton = imagenBoton;
        this.objetosEncontrados = objetosEncontrados*10;
        this.tiempo = tiempo * 5;
        this.nivelSuperado = nivelSuperado;
        this.puntuacionTotal = (this.tiempo + this.objetosEncontrados + bonusNivel);
        ClasesAuxiliares.reiniciarPintarAlpha();
        actualizarBD();
    }


    private void actualizarBD(){
        String sql = "UPDATE n47 SET ";
        Boolean controlCambios = false;

        if (puntuacionTotal > OperacionesBD.consulBD("SELECT maxPuntuacion FROM n47")){
            sql += " maxPuntuacion = " + puntuacionTotal + " ";
            controlCambios = true;
        }

        if(bonusNivel > 0
                && MainActivity.obtenerDificultadActual() < 3 &&
                MainActivity.obtenerDificultadActual() >= OperacionesBD.consulBD("SELECT nvlAlcanzado FROM n47")){
            if (controlCambios)
                sql += " , ";
            sql += " nvlAlcanzado = " + (MainActivity.obtenerDificultadActual() + 1);
            controlCambios = true;
        }

        if (controlCambios){
            sql += " WHERE id = 0";
            OperacionesBD.atualizarFilaBD(sql);
        }
    }

    public void dibujarFinPartida(Canvas canvas){
        Paint p = ClasesAuxiliares.pintarAlpha();

        canvas.drawBitmap(imagen,(canvas.getWidth()/2)-(imagen.getWidth()/2),datoPantalla[3]*0.5f, p);

        if (p.getAlpha() >= 255) {
            String puntuacion = "Puntuación Total: " + puntuacionTotal;

            p.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            p.setTextSize(coordenadas[3]*0.5f);

            //Centrado
            float  textSize = p.measureText(puntuacion)/2;
            float  textHeight= p.getTextSize();
            float factorX = canvas.getWidth()/2;
            float factorY = canvas.getHeight()*3/4;

            if (nivelSuperado)
                p.setColor(Color.rgb(42,127,172));
            else
                p.setColor(Color.rgb(181,25,62));


            canvas.drawRoundRect(factorX*0.9f - textSize, factorY-textHeight*1.2f, 1.1f*factorX + textSize, factorY+textHeight*0.5f,
                    20f,20f,p);

            p.setColor(Color.rgb(31,42,49));
            p.setTextAlign(Paint.Align.CENTER);
            canvas.drawText(puntuacion,factorX,factorY,p);

            canvas.drawBitmap(imagenBoton,canvas.getWidth()-datoPantalla[2]*1.1f,canvas.getHeight()-datoPantalla[3]*2.2f,null);
        }
    }
}

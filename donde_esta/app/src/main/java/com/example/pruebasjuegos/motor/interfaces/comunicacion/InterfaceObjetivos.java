package com.example.pruebasjuegos.motor.interfaces.comunicacion;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;

import com.example.pruebasjuegos.motor.ClasesAuxiliares;
import com.example.pruebasjuegos.motor.interfaces.InterfaceBasica;


public class InterfaceObjetivos extends InterfaceBasica {

    private int[] tamañoPantalla;
    private int tamañoTexto;
    private String complemento = "";
    private String mensajeActual="";


    private int segundos = 0;
    private int tiempoMuestra = 10;
    private boolean tiempoDibujo = false;
    private  boolean encontrado = false;
    private boolean forzado = false;

    public InterfaceObjetivos(int[] coordenadas) {
        super(coordenadas);
    }

    public InterfaceObjetivos(int[] coordenadas, int[] tamañoPantalla, int tamañoTexto) {
        super(coordenadas);
        this.tamañoTexto = tamañoTexto;
        this.tamañoPantalla = tamañoPantalla;
        ClasesAuxiliares.actualizarControlDialogo(true);
        this.mensajeActual = ClasesAuxiliares.obtenerDialogo();
    }

    public void actulizarCuadro(int segundos, boolean encontrado, boolean forzado, String complemento){
        if(tiempoDibujo && this.segundos - segundos >= tiempoMuestra){
            terminarDibujar();
        }
        if(this.encontrado != encontrado){
            terminarDibujar();
            ClasesAuxiliares.actualizarControlDialogo();
            this.mensajeActual = ClasesAuxiliares.obtenerDialogo();
            this.encontrado = encontrado;
            if (this.encontrado){
                empezarDibujar(segundos," Ve a la salida");
            }
            else{
                empezarDibujar(segundos,complemento);
            }
        }
        if (forzado){
            this.forzado = true;
            empezarDibujar(segundos,complemento);
        }
    }

    public void empezarDibujar(int segundos, String complemento){
        tiempoDibujo = true;
        this.segundos = segundos;
        this.complemento = complemento;
    }

    public void terminarDibujar(){
        forzado = false;
        tiempoDibujo = false;
        segundos = 0;
    }

    public void dibujarCuadro(Canvas canvas){
        Paint p = new Paint();
        if(tiempoDibujo){
            //Estilo del texto
            p.setStyle(Paint.Style.FILL);
            p.setTextSize(tamañoTexto*0.7f);

            //Obtención del tamaño del texto
            String mensage = "";
            if (!forzado)
                mensage = String.format(mensajeActual, complemento);
            else
                mensage = String.format("No veo %s por aquí.",complemento);

            float  textSize = p.measureText(mensage);
            float  textHeight= p.getTextSize();
            int factorX =(int)(0.97*canvas.getWidth()-textSize);

            p.setColor(Color.argb(150,0,0,0));
            canvas.drawRoundRect(factorX*0.98f, coordenadas[1] - (textHeight*1.4f), 1.02f*factorX+textSize, coordenadas[1] + (textHeight*0.8f)
                    ,20f,20f,p);

            p.setColor(Color.WHITE);
            canvas.drawText(mensage, factorX, coordenadas[1] ,p);
        }

        p.setTypeface(Typeface.MONOSPACE);
        p.setTextSize(tamañoTexto*0.8f);

        //Centrado
        String mensage = ClasesAuxiliares.quitarArticulo(complemento);
        float  textSize = p.measureText(mensage)/2;
        float  textHeight= p.getTextSize();
        float factorX = (canvas.getWidth() + tamañoPantalla[2])/2;
        float factorY = tamañoPantalla[3];

        p.setColor(Color.argb(230,0,0,0));
        canvas.drawRoundRect(factorX*0.9f - textSize, (0.7f*factorY)-textHeight, 1.1f*factorX + textSize, factorY*1.4f,
                20f,20f,p);

        p.setColor(Color.WHITE);
        p.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(mensage.toUpperCase(),factorX,factorY,p);

    }
}

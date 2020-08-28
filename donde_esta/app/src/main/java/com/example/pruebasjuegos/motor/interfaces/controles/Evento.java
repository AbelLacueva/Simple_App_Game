package com.example.pruebasjuegos.motor.interfaces.controles;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import com.example.pruebasjuegos.logica.Logica;
import com.example.pruebasjuegos.motor.interfaces.InterfaceBasica;
import com.example.pruebasjuegos.motor.interfaces.comunicacion.InterfaceObjetivos;
import com.example.pruebasjuegos.motor.interfaces.sprites.Chara;
import com.example.pruebasjuegos.motor.ClasesAuxiliares;
import com.example.pruebasjuegos.motor.NavegacionPorMatriz;

public class Evento extends InterfaceBasica {

    private NavegacionPorMatriz matriz;
    private Chara globo;
    int ultimaOrientacion = 0;
    private int [] desplazamiento;
    private int [] coordenadasEvento;
    private boolean encontrado = false;

    private final int velocidadDesplazamiento = 16;
    private int contadorVelocidad = velocidadDesplazamiento;
    private final int divisorFrames = 2;

    private InterfaceObjetivos interfaceObjetivos;

    private Logica logica;

    public Evento(int[] coordenadas,NavegacionPorMatriz matriz) {
        super(coordenadas);
        this.matriz = matriz;
    }

    public Evento(int[] coordenadas, Bitmap imagen, NavegacionPorMatriz matriz, int[]datosPantalla, Chara globo, Logica logica) {
        super(coordenadas, imagen);
        this.matriz = matriz;
        ultimaOrientacion = matriz.getOrientacion();
        this.globo = globo;
        this.desplazamiento = new  int[]{datosPantalla[2],datosPantalla[3]};
        this.logica = logica;
        this.interfaceObjetivos = new InterfaceObjetivos(new int[]{datosPantalla[2]*4,datosPantalla[1]-datosPantalla[3]},
                datosPantalla,datosPantalla[3]*1/2);
        interfaceObjetivos.empezarDibujar(logica.getSegundos(),logica.obtenerStringObjetoBuscado());
    }

    //Se han actualizado los if e else por refactorización desde pintar evento
    public void actualizarEvento(){
        interfaceObjetivos.actulizarCuadro(logica.getSegundos(), encontrado, false, logica.obtenerStringObjetoBuscado());
        if (logica.getElementoBuscado() == 0 && !encontrado){
            logica.setFinJuego(true);
        }
        else if(encontrado){
            encontrado = !matriz.comprobarCasillaActual(3);
        }
    }

    public void pintarEvento(Canvas canvas){
        interfaceObjetivos.dibujarCuadro(canvas);
        if (!encontrado && matriz.cotejadorEvento()){
            if (ultimaOrientacion == matriz.getOrientacion()){
                switch (ultimaOrientacion){
                    case 1:
                        coordenadasEvento = new int[]{coordenadas[0]-desplazamiento[1],coordenadas[1]};
                        break;
                    case 2:
                        coordenadasEvento = new int[]{coordenadas[0]+desplazamiento[1],coordenadas[1]};
                        break;
                    case 3:
                        coordenadasEvento = new int[]{coordenadas[0],coordenadas[1]-desplazamiento[0]};
                        break;
                    case 4:
                        coordenadasEvento = new int[]{coordenadas[0],coordenadas[1]+desplazamiento[0]};
                        break;
                }
                canvas.drawBitmap(imagen,coordenadasEvento[0],coordenadasEvento[1], ClasesAuxiliares.pintarAlpha());
                if (contadorVelocidad != 0){
                    if (contadorVelocidad%divisorFrames==0)
                        globo.moverChara();
                    contadorVelocidad--;
                    globo.dibujarChara(canvas);
                }
            }else{
                contadorVelocidad = velocidadDesplazamiento;
                globo.terminaMovimiento();
                ClasesAuxiliares.reiniciarPintarAlpha();
            }
            ultimaOrientacion = matriz.getOrientacion();
        }
        else{
            if(contadorVelocidad != velocidadDesplazamiento){
                contadorVelocidad = velocidadDesplazamiento;
                globo.terminaMovimiento();
                ClasesAuxiliares.pintarAlpha();
            }
        }
    }

    public void ejecutarEvento(int x, int y){
        if (matriz.cotejadorEvento() && !encontrado &&
                x > coordenadasEvento[0] && x < coordenadasEvento[0] + desplazamiento[0] &&
                y > coordenadasEvento[1] && y < coordenadasEvento[1] + desplazamiento[1]){
            if(logica.comprobarElemento(-matriz.obtenerValorEvento())){
                matriz.actualizarValorEvento(0);
                encontrado = true;
            }else
                interfaceObjetivos.actulizarCuadro(logica.getSegundos(), encontrado,true,logica.obtenerStringObjetoBuscado());
        }

    }

}

package com.example.pruebasjuegos.motor.interfaces.controles;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

import com.example.pruebasjuegos.motor.interfaces.InterfaceBasica;
import com.example.pruebasjuegos.motor.interfaces.sprites.Chara;
import com.example.pruebasjuegos.motor.NavegacionPorMatriz;


public class Cruceta extends InterfaceBasica {

    private Chara personaje;

    private int [] posicionReal;
    private int [] objetivoX = new int[3];
    private int [] objetivoY = new int[3];
    private int controlPulsado = 0;
    private NavegacionPorMatriz matriz;

    private int velocidadDesplazamiento = 9;
    private int contadorVelocidad = velocidadDesplazamiento;
    private int divisorFrames = 3;

    public Cruceta(int [] posicionReal,int[] coordenadas, Bitmap imagen, Chara personaje, int [] desplazamiento, NavegacionPorMatriz matriz) {
        super(coordenadas, imagen);
        this.posicionReal = posicionReal;
        this.personaje = personaje;
        objetivoX [2] = desplazamiento [2];
        objetivoY [2] = desplazamiento [3];
        this.matriz = matriz;
    }

    public void pintarCruceta(Canvas canvas) {
        Matrix matrix = new Matrix();
        Bitmap control = imagen;
        //Derecha
        canvas.drawBitmap(control,coordenadas[0],coordenadas[2],null);
        //Abajo
        matrix.postRotate(90);
        control =  Bitmap.createBitmap(control, 0, 0, control.getWidth(), control.getHeight(), matrix, true);
        canvas.drawBitmap(control,coordenadas[4],coordenadas[6],null);
        //Izquierda
        control =  Bitmap.createBitmap(control, 0, 0, control.getWidth(), control.getHeight(), matrix, true);
        canvas.drawBitmap(control,coordenadas[8],coordenadas[10],null);
        //Arriba
        control =  Bitmap.createBitmap(control, 0, 0, control.getWidth(), control.getHeight(), matrix, true);
        canvas.drawBitmap(control,coordenadas[12],coordenadas[14],null);
    }

    public void realizarMovimiento(int x, int y){
        if (objetivoX[0] == 0 && objetivoY[0] == 0 && controlPulsado == 0){
            if(x > coordenadas[0] && x < coordenadas[1]
                    && y > coordenadas[2] && y < coordenadas[3]) {
                objetivoX[0] = -objetivoX[2];
                objetivoX[1] = objetivoX[0] + posicionReal[0];
                personaje.empezarMovimiento(2);
                controlPulsado = 2;
            }else if( x > coordenadas[4] && x < coordenadas[5]
                    && y > coordenadas[6] && y < coordenadas[7]) {
                objetivoY[0] = objetivoY[2];
                objetivoY[1] = posicionReal[1] - objetivoY[0];
                personaje.empezarMovimiento(0);
                controlPulsado = 4;
            }else if (x > coordenadas[8] && x < coordenadas[9]
                    && y > coordenadas[10] && y < coordenadas[11]) {
                objetivoX[0] = objetivoX[2];
                objetivoX[1] = objetivoX[0] + posicionReal[0];
                personaje.empezarMovimiento(1);
                controlPulsado = 1;
            }else if( x > coordenadas[12] && x < coordenadas[13]
                    && y > coordenadas[14] && y < coordenadas[15]) {
                objetivoY[0] = -objetivoY[2];
                objetivoY[1] = posicionReal[1] - objetivoY[0];
                personaje.empezarMovimiento(3);
                controlPulsado = 3;
            }
        }
        matriz.setOrientacion(controlPulsado);
    }

    public boolean botonDiferentePulsado(int x, int y) {
        if(controlPulsado != 2 &&
                x > coordenadas[0] && x < coordenadas[1]
                && y > coordenadas[2] && y < coordenadas[3]) {
            return true;
        }else if(controlPulsado!= 4 &&
                x > coordenadas[4] && x < coordenadas[5]
                && y > coordenadas[6] && y < coordenadas[7]) {
            return true;
        }else if (controlPulsado != 1 &&
                x > coordenadas[8] && x < coordenadas[9]
                && y > coordenadas[10] && y < coordenadas[11]) {
            return true;
        }else if(controlPulsado != 3 &&
                x > coordenadas[12] && x < coordenadas[13]
                && y > coordenadas[14] && y < coordenadas[15]) {
            return true;
        }
        return false;
    }

    public boolean botonPulsado(int x, int y) {
        if( x > coordenadas[0] && x < coordenadas[1]
                && y > coordenadas[2] && y < coordenadas[3]) {
            return true;
        }else if( x > coordenadas[4] && x < coordenadas[5]
                && y > coordenadas[6] && y < coordenadas[7]) {
            return true;
        }else if (x > coordenadas[8] && x < coordenadas[9]
                && y > coordenadas[10] && y < coordenadas[11]) {
            return true;
        }else if(x > coordenadas[12] && x < coordenadas[13]
                && y > coordenadas[14] && y < coordenadas[15]) {
            return true;
        }
        return false;
    }

    public void movimiento() {
        if(objetivoX[0] > 0 && matriz.navegacion(1)){
            posicionReal[0] += objetivoX[2]/velocidadDesplazamiento;
            objetivoX[0] -= objetivoX[2]/velocidadDesplazamiento;
            //Modificación para el sprite, simulando movimiento. Deben ser multiplos de 3 * x, dado
            //tiene 4 posiciones
            if(contadorVelocidad!=0 && contadorVelocidad%divisorFrames==0){
                personaje.moverChara();
            }
            --contadorVelocidad;
            //Condiciones de fin de ciclo
            //Si x ha completado su ciclo y se ha levantado el dedo del botón, se actualiza la
            //matriz
            if (objetivoX[0]<=0 && controlPulsado == 0){
                matriz.actualizarPosicionX(-1);
                //Regulación de posiciones
                posicionReal[0] = objetivoX[1];
                contadorVelocidad = velocidadDesplazamiento;
                objetivoX[0] = 0;
                //Visualizacion chara
                personaje.terminaMovimiento();
            }
            //Si el botón continua pulsado, adicionalmente se retroalimenta la x
            else if (objetivoX[0]<=0 && controlPulsado == 1){
                matriz.actualizarPosicionX(-1);
                //Regulación de posiciones
                posicionReal[0] = objetivoX[1];
                contadorVelocidad = velocidadDesplazamiento;
                //Condicion adicional de momiviento
                objetivoX[0] = objetivoX[2];
                objetivoX[1] = posicionReal[0] +  objetivoX[0];
                //Visualizacion Chara
                personaje.terminaMovimiento();

            }
            return;
        }
        if(objetivoX[0] < 0 && matriz.navegacion(2)){
            posicionReal[0] -= objetivoX[2]/velocidadDesplazamiento;
            objetivoX[0] += objetivoX[2]/velocidadDesplazamiento;
            if(contadorVelocidad!=0 && contadorVelocidad%divisorFrames==0){
                personaje.moverChara();
            }
            --contadorVelocidad;
            if (objetivoX[0]>=0 && controlPulsado == 0){
                matriz.actualizarPosicionX(1);
                posicionReal[0] = objetivoX[1];
                contadorVelocidad = velocidadDesplazamiento;
                objetivoX[0] = 0;
                personaje.terminaMovimiento();
            }
            else if(objetivoX[0]>=0 && controlPulsado == 2){
                matriz.actualizarPosicionX(1);
                posicionReal[0] = objetivoX[1];
                contadorVelocidad = velocidadDesplazamiento;
                objetivoX[0] = -objetivoX[2];
                objetivoX[1] = posicionReal[0] + objetivoX[0];
                personaje.terminaMovimiento();
            }
            return;
        }

        //Variables y
        if(objetivoY[0] < 0 && matriz.navegacion(3)){
            posicionReal[1] += objetivoY[2]/velocidadDesplazamiento;
            objetivoY[0] += objetivoY[2]/velocidadDesplazamiento;
            if(contadorVelocidad!=0 && contadorVelocidad%divisorFrames==0){
                personaje.moverChara();
            }
            --contadorVelocidad;
            if (objetivoY[0]>=0 && controlPulsado == 0){
                posicionReal[1] = objetivoY[1];
                contadorVelocidad = velocidadDesplazamiento;
                matriz.actualizarPosicionY(-1);
                objetivoY[0] = 0;
                personaje.terminaMovimiento();
            }
            else if(objetivoY[0]>=0 && controlPulsado == 3){
                posicionReal[1] = objetivoY[1];
                contadorVelocidad = velocidadDesplazamiento;
                matriz.actualizarPosicionY(-1);
                objetivoY[0] = -objetivoY[2];
                objetivoY[1] = posicionReal[1] - objetivoY[0];
                personaje.terminaMovimiento();
            }
            return;
        }

        if(objetivoY[0] > 0 && matriz.navegacion(4)){
            posicionReal[1] -= objetivoY[2]/velocidadDesplazamiento;
            objetivoY[0] -= objetivoY[2]/velocidadDesplazamiento;
            if(contadorVelocidad!=0 && contadorVelocidad%divisorFrames==0){
                personaje.moverChara();
            }
            --contadorVelocidad;
            if (objetivoY[0]<=0 && controlPulsado == 0){
                posicionReal[1] = objetivoY[1];
                contadorVelocidad = velocidadDesplazamiento;
                matriz.actualizarPosicionY(1);
                objetivoY[0] = 0;
                personaje.terminaMovimiento();
            }
            else if(objetivoY[0]<=0 && controlPulsado == 4){
                posicionReal[1] = objetivoY[1];
                contadorVelocidad = velocidadDesplazamiento;
                matriz.actualizarPosicionY(1);
                objetivoY[0] = objetivoY[2];
                objetivoY[1] = posicionReal[1] - objetivoY[0];
                personaje.terminaMovimiento();
            }
            return;
        }
        if (controlPulsado == 0){
            objetivoX[0]=0;
            objetivoY[0]=0;
        }
    }

    //Aparentemente soluciona desde la clase chara: Reiniciar la posición original
    public void actualizarTiempo(int divisorFrames){
        this.divisorFrames = divisorFrames;
        velocidadDesplazamiento = 3 * divisorFrames;
    }

    //Get y Set

    public void setControlPulsado(int controlPulsado) {
        this.controlPulsado = controlPulsado;
    }

    public int[] getPosicionReal() {
        return posicionReal;
    }

}

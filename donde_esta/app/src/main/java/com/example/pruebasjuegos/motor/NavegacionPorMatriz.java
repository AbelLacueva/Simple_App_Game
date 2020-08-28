package com.example.pruebasjuegos.motor;

import android.util.Log;

public class NavegacionPorMatriz {

    private int [][] matriz;
    private int [] posicionActualMatriz;
    private int orientacion = 0;

    public NavegacionPorMatriz(int[][] matriz, int[] posicionActualMatriz) {
        this.matriz = matriz;
        this.posicionActualMatriz = posicionActualMatriz;
    }

    public NavegacionPorMatriz(int[][] matriz, int[] posicionActualMatriz, int orientacion) {
        this.matriz = matriz;
        this.posicionActualMatriz = posicionActualMatriz;
        this.orientacion = orientacion;
    }

    public boolean navegacion(int valor){
        switch (valor){
            case 1:
                if(matriz[posicionActualMatriz[0]][posicionActualMatriz[1]-1] > 0)
                    return true;
                break;
            case 2:
                if (matriz[posicionActualMatriz[0]][posicionActualMatriz[1]+1] > 0)
                    return true;
                break;
            case 3:
                if (matriz[posicionActualMatriz[0]-1][posicionActualMatriz[1]] > 0)
                    return true;
                break;
            case 4:
                if (matriz[posicionActualMatriz[0]+1][posicionActualMatriz[1]] > 0)
                    return true;
                break;
        }
        return false;
    }

    public boolean cotejadorEvento(){
        switch (orientacion){
            case 1:
                if(matriz[posicionActualMatriz[0]][posicionActualMatriz[1]-1] <= -1)
                    return true;
                break;
            case 2:
                if (matriz[posicionActualMatriz[0]][posicionActualMatriz[1]+1]  <= -1)
                    return true;
                break;
            case 3:
                if (matriz[posicionActualMatriz[0]-1][posicionActualMatriz[1]]  <= -1)
                    return true;
                break;
            case 4:
                if (matriz[posicionActualMatriz[0]+1][posicionActualMatriz[1]]  <= -1)
                    return true;
                break;
            case 0:
                Log.e("MATRIZ","No se le ha asignado una dirección al personaje");
                return false;
        }
        return false;
    }

    public int obtenerValorEvento(){
        switch (orientacion){
            case 1:
                return matriz[posicionActualMatriz[0]][posicionActualMatriz[1]-1];
            case 2:
                return matriz[posicionActualMatriz[0]][posicionActualMatriz[1]+1];
            case 3:
                return matriz[posicionActualMatriz[0]-1][posicionActualMatriz[1]];
            case 4:
                return matriz[posicionActualMatriz[0]+1][posicionActualMatriz[1]];
            case 0:
                return 0;
        }
        return 0;
    }

    public void actualizarValorEvento(int valor){
        switch (orientacion){
            case 1:
                matriz[posicionActualMatriz[0]][posicionActualMatriz[1]-1] = valor;
                break;
            case 2:
                matriz[posicionActualMatriz[0]][posicionActualMatriz[1]+1] = valor;
                break;
            case 3:
                matriz[posicionActualMatriz[0]-1][posicionActualMatriz[1]] = valor;
                break;
            case 4:
                matriz[posicionActualMatriz[0]+1][posicionActualMatriz[1]] = valor;
                break;
            case 0:
                Log.e("ActualizarEvento", "No hay un evento que actualizar");
        }
    }

    public boolean comprobarCasillaActual(int valor){
        if ( matriz[posicionActualMatriz[0]][posicionActualMatriz[1]] == 3)
            return true;
        else
            return false;
    }

    public void imprimirMatriz(){
        for(int y = 0; y < matriz.length ; y++){
            for(int x = 0; x < matriz[y].length; x++){
                System.out.print(matriz[y][x] + " ");
            }
            System.out.println();
        }
    }

    public void actualizarPosicionX(int x){
        posicionActualMatriz[1] += x;
    }

    public void actualizarPosicionY(int y){
        posicionActualMatriz[0] += y;
    }

    public int[][] getMatriz() {
        return matriz;
    }

    public int getOrientacion() {
        return orientacion;
    }

    public void setOrientacion(int orientacion) {
        if (orientacion!=0)
            this.orientacion = orientacion;
    }
}

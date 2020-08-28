package com.example.pruebasjuegos.logica;

import android.util.Log;

import com.example.pruebasjuegos.baseDatos.OperacionesBD;
import com.example.pruebasjuegos.motor.ClasesAuxiliares;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class Logica {

    //Variable en la asignación de mapas
    //Variable en el nivel de dificultad
    //Implementar base de datos con puntuaciones
    //Implimentar lista de objetos e iconos de los mismos
    //Asgnar lista de tilesMap, que deben ser seleccionados aleatoriamente

    //Crear varios HasMap en vase al nivel de dificultad y/o la temática

    public static HashMap <Integer,String> objetosProfesor = new HashMap<Integer, String>(){{
        put(0,"Nada");
        put(1,"las gafas");
        put(2,"los examenes");
        put(3,"el maletin");
        put(4,"la llave");
        put(5,"el palo de cricket");
        put(6,"el birrete");
        put(7,"dinero pa' pipas");
        put(8," las vitaminas");
    }};
    String [] rutaProfesor = new String[]{"Iconos/lupa.png", "Iconos/gafas.png","Iconos/puntajes.png","Iconos/maletin.png", "Iconos/llave.png","Iconos/grillo.png",
            "Iconos/birrete.png","Iconos/ganancias.png","Iconos/vitaminas.png"};
    public static HashMap <Integer,String> objetosCientifico = new HashMap<Integer, String>(){{
        put(0,"Nada");
        put(1,"las gafas");
        put(2,"el matraz");
        put(3,"el atomo de carbono");
        put(4,"la entropía");
        put(5,"la Teoría del todo");
        put(6,"el maletin");
        put(7,"el Co-Ni-Zn(OH)");
        put(8,"los antidepresivos");
    }};
    String [] rutaCientifico = new String[]{"Iconos/lupa.png", "Iconos/gafasRedondas.png","Iconos/agitador.png","Iconos/atomo.png", "Iconos/quimico.png","Iconos/investigar.png",
            "Iconos/maletin.png","Iconos/ganancias.png","Iconos/vitaminas.png"};


    public static HashMap <Integer,String> objetos;
    private static String [] rutas;
    int elementoBuscado = 0;
    int minimo;
    int maximo;

    int segundos;
    int tiempo_actual;
    int tiempo_previo;

    int tiempo_aumento;
    int iteracion_tiempo = 3;

    ArrayList <Integer> aEncontrar;
    ArrayList <Integer> encontrados;

    private boolean finJuego = false;
    private boolean victoria;

    public Logica(int restantes,int maximo,int minimo, int segundos) {
        this.minimo = minimo;
        this.maximo = maximo;
        this.segundos = segundos;
        this.tiempo_aumento = segundos/2;
        asignarObjetos();
        this.encontrados = new ArrayList<>();
        this.aEncontrar = generarNumeros(restantes,maximo,minimo);
        elementoBuscado = aEncontrar.get(generarAleatorios(aEncontrar.size()-1,0));
    }

    public Logica(int restantes,int maximo,int minimo, int segundos, int azar) {
        this.minimo = minimo;
        this.maximo = maximo;
        this.segundos = segundos;
        this.tiempo_aumento = segundos/2;
        asignarObjetos(azar);
        this.encontrados = new ArrayList<>();
        this.aEncontrar = generarNumeros(restantes,maximo,minimo);
        elementoBuscado = aEncontrar.get(generarAleatorios(aEncontrar.size()-1,0));
    }


    public void asignarObjetos(){
        int aleatorio = generarAleatorios(2,1);
        switch (aleatorio){
            case 1:
                this.objetos = objetosProfesor;
                this.rutas = rutaProfesor;
                break;
            case 2:
                this.objetos = objetosCientifico;
                this.rutas = rutaCientifico;
                break;
        }

    }

    public void asignarObjetos(int azar){
        switch (azar){
            case 1:
                this.objetos = objetosProfesor;
                this.rutas = rutaProfesor;
                break;
            case 2:
                this.objetos = objetosCientifico;
                this.rutas = rutaCientifico;
                break;
        }
    }

    public int [][] reasignarMatriz(int [][] matriz){
        int contador = 0;
        int [][] nuevaMatriz = new int[matriz.length][matriz[0].length];
        for(int y = 0; y < nuevaMatriz.length; y++){
            for (int x = 0; x < nuevaMatriz[y].length ; x++){
                nuevaMatriz[y][x] = matriz[y][x];
            }
        }
        for(int y = 0; y < nuevaMatriz.length; y++){
            for (int x = 0; x < nuevaMatriz[y].length ; x++){
                if (nuevaMatriz[y][x] == -10){
                    nuevaMatriz[y][x] = -(aEncontrar.get(contador));
                    contador++;
                }
            }
        }
        for(int y = 0; y < nuevaMatriz.length; y++){
            for (int x = 0; x < nuevaMatriz[y].length ; x++){
                System.out.print(nuevaMatriz[y][x]+ " ");
                if (nuevaMatriz[y][x] < 0)
                    System.out.print(objetos.get(-1*(nuevaMatriz[y][x])) +" ");
            }
            System.out.println();
        }
        return nuevaMatriz;
    }

    private  ArrayList<Integer> generarNumeros(int tamaño, int maximo,int minimo){
        HashSet<Integer> numBruto = new HashSet<>();
        while (numBruto.size()<tamaño){
            numBruto.add(generarAleatorios(maximo,minimo));
        }
        ArrayList<Integer> listNum = new ArrayList<>(numBruto);
        /*para ordenar los numero
        Collections.sort(listNum);*/
        return listNum;
    }

    public static int generarAleatorios(int max,int min) {
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public boolean comprobarElemento(int elemento){
        if (elemento == elementoBuscado){
            encontrados.add(elementoBuscado);
            obtenerNuevoElemento();
            return true;
        }else
            return false;
    }

    private void obtenerNuevoElemento(){
        for (int x = 0; x < aEncontrar.size() ; x++){
            if (aEncontrar.get(x)==elementoBuscado)
                aEncontrar.remove(x);
        }
        if (aEncontrar.size()!=0)
            elementoBuscado = aEncontrar.get(generarAleatorios(aEncontrar.size()-1,0));
        else{
            elementoBuscado = 0;
            //Listado de Elemtos encontrados
            listarElementosEncontrado();
        }

    }

    public String obtenerStringObjetoBuscado(){
        return objetos.get(getElementoBuscado());
    }

    public void listarElementosEncontrado(){
        for (int i:encontrados){
            System.out.println(objetos.get(i));
        }
    }

    //Implementar las franjas de velocidad del Sprite y contiene la lógica de fin de juego
    public int actualizarLogicaTemporal() {
        if (segundos == 0 || finJuego){
            if (segundos == 0 & finJuego ==false){
                logicaFinJuego(false);
                finJuego=true;
            }else
                logicaFinJuego(true);
        }else{
            tiempo_actual = (int)System.currentTimeMillis()/1000;
            if(tiempo_actual!=tiempo_previo){
                tiempo_previo = tiempo_actual;
                segundos -= 1;
                if (segundos == tiempo_aumento && iteracion_tiempo > 1){
                    //aumentar tiempo
                    tiempo_aumento = segundos /2;
                    iteracion_tiempo -= 1;
                }
                Log.e("Tiempo", ""+segundos);
            }
        }
        return iteracion_tiempo;
    }

    private void logicaFinJuego(boolean victoria){
        if(victoria)
            this.victoria = true;
        else
            this.victoria = false;
    }

    public boolean pulsadoFinJuego(){
        //Dejarse de gilipolleces y añadir botón para salir
        if (finJuego && ClasesAuxiliares.pintarAlpha().getAlpha()>=255)
            return true;
        return false;
    }

    public int getElementoBuscado() {
        return elementoBuscado;
    }

    public int getSegundos() {
        return segundos;
    }

    public ArrayList<Integer> getEncontrados() {
        return encontrados;
    }

    public static String[] getRutas() {
        return rutas;
    }

    public boolean isFinJuego() {
        return finJuego;
    }

    public void setFinJuego(boolean finJuego) {
        this.finJuego = finJuego;
    }

    public boolean isVictoria() {
        return victoria;
    }

    public void setVictoria(boolean victoria) {
        this.victoria = victoria;
    }
}
